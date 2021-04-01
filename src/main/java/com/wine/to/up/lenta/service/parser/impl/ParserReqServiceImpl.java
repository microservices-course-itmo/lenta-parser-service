package com.wine.to.up.lenta.service.parser.impl;

import com.wine.to.up.commonlib.annotations.InjectEventLogger;
import com.wine.to.up.commonlib.logging.EventLogger;
import com.wine.to.up.lenta.service.components.LentaServiceMetricsCollector;
import com.wine.to.up.lenta.service.parser.ParserReqService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Metrics;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static com.wine.to.up.lenta.service.logging.LentaParserServiceNotableEvents.*;

/**
 * This class consists of methods of calling lenta API and parsing reserved data
 * Also collecting metrics for all processed
 */
@Slf4j
@AllArgsConstructor
@Getter
@Setter
@Scope(value = "prototype")
public class ParserReqServiceImpl implements ParserReqService {

    /**
     * Metric that count parsed wines
     */
    private static final String PARSED_WINES_COUNT = "parsed_wines_count";

    /**
     * Metric that count parsing which still in progress
     */
    private static final String PARSING_IN_PROGRESS_GAUGE = "parsing_in_progress";

    /**
     * Metric that count summary time of parsing
     */
    private static final String PARSING_PROCESS_DURATION_SUMMARY = "parsing_process_duration";

    /**
     * Metric that stores the time that has passed since the last prasing
     */
    private static final String TIME_SINCE_LAST_SUCCEEDED_PARSING_GAUGE = "time_since_last_succeeded_parsing";

    /**
     * URL of Lenta site
     */
    private String baseUrl;

    /**
     * URL of Lenta API
     */
    private final String apiUrl;

    /**
     * Body for connection to API
     */
    private final String apiBody;

    /**
     * Collector of metric
     */
    private final LentaServiceMetricsCollector metricsCollector;

    /**
     * Counter of parsed wines
     */
    private final AtomicInteger parsedWines = new AtomicInteger();

    /**
     * Counter of wine that parsing right now
     */
    private final AtomicInteger parsingInProgress = new AtomicInteger(0);

    /**
     * Time when last parsing had ended successfully
     */
    private final AtomicLong lastSucceededParsingTime = new AtomicLong(0);

    /**
     * Event logger
     */
    @InjectEventLogger
    private EventLogger eventLogger;

    /**
     * Wine privacy - brand name
     */
    private static final String BRAND_NAME = "Бренд";

    /**
     * Wine privacy - country_name
     */
    private static final String COUNTRY_NAME = "Страна производителя";

    /**
     * Wine privacy - capacity name
     */
    private static final String CAPACITY_NAME = "Литраж";

    /**
     * Wine privacy - strength name
     */
    private static final String STRENGTH_NAME = "Крепость (%)";

    /**
     * Wine privacy - color name
     */
    private static final String COLOR_NAME = "Цвет";

    /**
     * Wine privacy - sugar name
     */
    private static final String SUGAR_NAME = "Содержание сахара";

    /**
     * Wine privacy - grape sort name
     */
    private static final String GRAPE_SORT_NAME = "Сорт винограда";

    /**
     * Wine privacy - aroma name
     */
    private static final String AROMA_NAME = "Аромат";

    /**
     * Wine privacy - gactranomy
     */
    private static final String GACTRANOMY = "Гастрономия";

    /**
     * Wine privacy - taste
     */
    private static final String TASTE = "Вкус";

    /**
     * Wine privacy - manufacturer
     */
    private static final String MANUFACTURER = "Вид упаковки";

    /**
     * Wine privacy - URL of image
     */
    private static final String IMAGEURL = "imageUrl";

    private static final Integer PAGE_COUNT = 4;

    private static final String CHECK_SPARKLING = "c529e2e61ea65b2c9f45b32b62d75a0b5";

    private static final String CHECK_VERMUTS = "c95dd35572e56e1a1bea8ef16f1640ff0";

    private static final String CHECK_SWEET_WINES = "c5a9adffd31b3fdf7af0a1a1bf01051ea";

    private static final Integer MAX_BATCH_SIZE = 2;

    /**
     * Builder for class with metrics
     *
     * @param baseUrl          This is base URL.
     * @param apiUrl           This is URL of Lenta API.
     * @param apiBody          This is body for connection to Lenta API.
     * @param metricsCollector This is Collector for metrics
     * @return an instance of the ParserReqServiceImpl.
     */
    public ParserReqServiceImpl(String baseUrl, String apiUrl, String apiBody, LentaServiceMetricsCollector metricsCollector) {
        this.baseUrl = baseUrl;
        this.apiUrl = apiUrl;
        this.apiBody = apiBody;
        this.metricsCollector = Objects.requireNonNull(metricsCollector, "Can't get metricsCollector");
        Metrics.gauge(PARSED_WINES_COUNT, parsedWines);
        Metrics.gauge(PARSING_IN_PROGRESS_GAUGE, parsingInProgress);
        Metrics.gauge(
                TIME_SINCE_LAST_SUCCEEDED_PARSING_GAUGE,
                lastSucceededParsingTime,
                val -> val.get() == 0 ? Double.NaN : (System.currentTimeMillis() - val.get()) / 1000.0
        );
    }

    /**
     * Builder for class without metrics
     *
     * @param baseUrl This is base URL.
     * @param apiUrl  This is URL of Lenta API.
     * @param apiBody This is body for connection to Lenta API.
     * @return an instance of the ParserReqServiceImpl.
     */
    public ParserReqServiceImpl(String baseUrl, String apiUrl, String apiBody) {
        this.baseUrl = baseUrl;
        this.apiUrl = apiUrl;
        this.apiBody = apiBody;
        this.metricsCollector = null;
    }

    /**
     * Method of geting data from lenta API
     *
     * @param batchSize This is Size of a batch.
     * @return wineList This is array of json with parsed properties(One Json for one single wine) .
     */
    @Timed(PARSING_PROCESS_DURATION_SUMMARY)
    public ParserRspImpl getJson(Integer batchSize) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).connectTimeout(Duration.ofSeconds(30)).executor(executor).build();

        ParserRspImpl wineList = new ParserRspImpl();

        JSONArray jsonArr = new JSONArray(apiBody);
        for (int a = 0; a < jsonArr.length(); a++) {

            int tempBatchSize = batchSize;
            long startFetchingTime = System.nanoTime();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonArr.getJSONObject(a).toString()))
                    .build();

            HttpResponse<?> response;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                eventLogger.error(E_BAD_REQUEST, e);
                parsedWines.set(wineList.getJsonList().size());
                eventLogger.info(W_PAGE_PARSING_FAILED);
                return null;
            }
            if (response == null) {
                eventLogger.info(W_PAGE_PARSING_FAILED);
            }
            JSONObject jsonObject = new JSONObject(response.body().toString());
            JSONArray array = jsonObject.getJSONArray("skus");

            String nodeCode = jsonArr.getJSONObject(a).getString("nodeCode");
            String cityCode = jsonArr.getJSONObject(a).getString("storeId");

            metricsCollector.incParsingStarted(getCityName(cityCode));
            eventLogger.info(I_START_PARSING);
            parsingInProgress.incrementAndGet();

            String cityName = getCityName(cityCode);
            metricsCollector.timeWinePageFetchingDuration(System.nanoTime() - startFetchingTime, cityName);

            tempBatchSize = getTempBatchSize(tempBatchSize, array, nodeCode);

            for (int i = 0; i < array.length(); i++) {
                long startParsingTime = System.nanoTime();
                long startPageParsingTime = startParsingTime;
                if (i < tempBatchSize || i >= tempBatchSize + MAX_BATCH_SIZE) {
                    continue;
                }

                Double wineOldPrice = array.getJSONObject(i).getJSONObject("regularPrice").getDouble("value");
                Double wineNewPrice = array.getJSONObject(i).getJSONObject("cardPrice").getDouble("value");
                String wineTitle = array.getJSONObject(i).getString("title");
                Integer wineCount = array.getJSONObject(i).getInt("stock");
                String imageUrl = null;

                if (array.getJSONObject(i).has(IMAGEURL)) {
                    imageUrl = String.valueOf(array.getJSONObject(i).get(IMAGEURL));
                }

                Double rating = array.getJSONObject(i).getDouble("averageRating");

                JSONObject jsonString = new JSONObject()
                        .put(IMAGEURL, imageUrl)
                        .put("wineOldPrice", wineOldPrice)
                        .put("wineNewPrice", wineNewPrice)
                        .put("wineRating", rating)
                        .put("wineLink", baseUrl + array.getJSONObject(i).getString("skuUrl"))
                        .put("wineTitle", wineTitle)
                        .put("count", wineCount)
                        .put("cityName", cityName);

                if (jsonArr.getJSONObject(a).getString("nodeCode").equals(CHECK_SPARKLING)) {
                    jsonString.put("wineSparkling", true);
                } else {
                    jsonString.put("wineSparkling", false);
                }
                StringBuilder productHtml = new StringBuilder()
                        .append(baseUrl)
                        .append(array.getJSONObject(i).getString("skuUrl"));

                Document document = null;
                try {
                    document = getItemHtml(String.valueOf(productHtml), cityName);
                } catch (IOException e) {
                    eventLogger.warn(W_SOME_WARN_EVENT, e);
                }

                JSONObject wine = getProperties(jsonString, document);

                wineList.add(wine);

                metricsCollector.parsingDetailsDuration(System.nanoTime() - startParsingTime, cityName);

                eventLogger.info(I_WINE_DETAILS_PARSED, jsonString);


                if (wineList.getJsonList().size() % PAGE_COUNT == 0) {
                    eventLogger.info(I_WINES_PAGE_PARSED);
                    metricsCollector.parsingPageDuration(System.nanoTime() - startPageParsingTime, cityName);
                }
            }
            metricsCollector.countParsingComplete("SUCCESS", cityName);
            parsingInProgress.decrementAndGet();
        }
        eventLogger.info(I_WINES_PARSED, wineList.getJsonList().size());
        lastSucceededParsingTime.set(System.currentTimeMillis());
        parsedWines.set(wineList.getJsonList().size());
        return wineList;
    }

    /**
     * Method of parsing additional properties
     *
     * @param jsonString This is container for properties.
     * @param document   This is html page of single wine.
     * @return jsonString.
     */
    public JSONObject getProperties(JSONObject jsonString, Document document) {

        Elements elem;
        try {
            elem = Objects.requireNonNull(document).getElementsByClass("sku-card-tab-params__item");
        } catch (NullPointerException ex) {
            eventLogger.error(E_NULL_DOCUMENT, ex);
            eventLogger.info(W_WINE_DETAILS_PARSING_FAILED);
            return jsonString;
        }
        for (Element element : elem) {
            Document iterDoc;

            try {
                iterDoc = Jsoup.parse(element.toString());
            } catch (Exception ex) {
                eventLogger.warn(W_SOME_WARN_EVENT, element.toString(), jsonString.getString("wineLink"));
                return jsonString;
            }

            String title = iterDoc.getElementsByClass("sku-card-tab-params__label-block").text();
            Elements value = iterDoc.getElementsByClass("sku-card-tab-params__value");
            if (value.isEmpty()) {
                value = iterDoc.getElementsByClass("link sku-card-tab-params__link");
            }

            switch (title) {
                case BRAND_NAME:
                    jsonString.put("wineBrand", value.text());
                    break;
                case COUNTRY_NAME:
                    jsonString.put("wineCountry", value.text());
                    break;
                case GACTRANOMY:
                    jsonString.put("wineGastronomy", value.text());
                    break;
                case CAPACITY_NAME:
                    jsonString.put("wineCapacity", value.text().replace(" L", ""));
                    break;
                case STRENGTH_NAME:
                    jsonString.put("wineStrength", Float.parseFloat(value.text()));
                    break;
                case COLOR_NAME:
                    jsonString.put("wineColour", value.text());
                    break;
                case SUGAR_NAME:
                    jsonString.put("wineSugarContent", value.text());
                    break;
                case GRAPE_SORT_NAME:
                    jsonString.put("wineGrapeSort", value.text());
                    break;
                case AROMA_NAME:
                    jsonString.put("wineAroma", value.text());
                    break;
                case TASTE:
                    jsonString.put("wineTaste", value.text());
                    break;
                case MANUFACTURER:
                    jsonString.put("winePackagingType", value.text());
                    break;
                default:
                    break;
            }
        }
        return jsonString;
    }

    /**
     * Method of geting html
     *
     * @param productHtml This is URL.
     * @return document This is html received from URL.
     */

    public Document getItemHtml(String productHtml, String cityName) throws IOException {
        long startFetchingTime = System.nanoTime();
        Document document = Jsoup.connect(String.valueOf(productHtml))
                .header("Accept-Encoding", "gzip, deflate")
                .userAgent("Opera")
                .maxBodySize(0)
                .timeout(15000)
                .get();
        metricsCollector.fetchingDetailsDuration(System.nanoTime() - startFetchingTime, cityName);
        return document;
    }

    private int getTempBatchSize(int tempBatchSize, JSONArray array, String nodeCode) {
        switch (nodeCode){
            case CHECK_SPARKLING:
                if (tempBatchSize > array.length()) {
                    tempBatchSize = tempBatchSize / 4;
                }
                break;
            case CHECK_SWEET_WINES:
                if (tempBatchSize > array.length()) {
                    tempBatchSize = tempBatchSize & array.length();
                }
                break;
            case CHECK_VERMUTS:
                if (tempBatchSize > array.length()) {
                    tempBatchSize = tempBatchSize % array.length();
                }
                break;
        }
        return tempBatchSize;
    }

    private String getCityName(String cityCode) {
        String cityName;
        switch (cityCode){
            case "0183":
                cityName = "Москва";
                break;
            case "0009":
                cityName = "Санкт-Петербург";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + cityCode);
        }
        return cityName;

    }
}
