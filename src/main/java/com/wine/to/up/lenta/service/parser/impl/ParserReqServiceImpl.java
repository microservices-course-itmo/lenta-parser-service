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
import java.util.Date;
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

    /** Metric that count parsed wines */
    private static final String PARSED_WINES_COUNT = "parsed_wines_count";

    /** Metric that count parsing which still in progress */
    private static final String PARSING_IN_PROGRESS_GAUGE = "parsing_in_progress";

    /** Metric that count summary time of parsing*/
    private static final String PARSING_PROCESS_DURATION_SUMMARY = "parsing_process_duration";

    /** Metric that stores the time that has passed since the last prasing*/
    private static final String TIME_SINCE_LAST_SUCCEEDED_PARSING_GAUGE = "time_since_last_succeeded_parsing";

    /** URL of Lenta site*/
    private String baseUrl;

    /** URL of Lenta API*/
    private final String apiUrl;

    /** Body for connection to API */
    private final String apiBody;

    /** Collector of metric */
    private final LentaServiceMetricsCollector metricsCollector;

    /** Counter of parsed wines  */
    private final AtomicInteger parsedWines = new AtomicInteger();

    /** Counter of wine that parsing right now */
    private final AtomicInteger parsingInProgress = new AtomicInteger(0);

    /** Time when last parsing had ended successfully - */
    private final AtomicLong lastSucceededParsingTime = new AtomicLong(0);

    /** Event logger */
    @InjectEventLogger
    private EventLogger eventLogger;

    /** Wine privacy - brand name  */
    private static final String BRAND_NAME = "Бренд";

    /** Wine privacy - country_name*/
    private static final String COUNTRY_NAME = "Страна производителя";

    /** Wine privacy - capacity name*/
    private static final String CAPACITY_NAME = "Литраж";

    /** Wine privacy - strength name*/
    private static final String STRENGTH_NAME = "Крепость (%)";

    /** Wine privacy - color name*/
    private static final String COLOR_NAME = "Цвет";

    /** Wine privacy - sugar name*/
    private static final String SUGAR_NAME = "Содержание сахара";

    /** Wine privacy - grape sort name*/
    private static final String GRAPE_SORT_NAME = "Сорт винограда";

    /** Wine privacy - aroma name*/
    private static final String AROMA_NAME = "Аромат";

    /** Wine privacy - gactranomy*/
    private static final String GACTRANOMY = "Гастрономия";

    /** Wine privacy - taste*/
    private static final String TASTE = "Вкус";

    /** Wine privacy - manufacturer*/
    private static final String MANUFACTURER = "Вид упаковки";

    /** Wine privacy - URL of image*/
    private static final String IMAGEURL = "imageUrl";

    /**
     * Builder for class with metrics
     *
     * @param baseUrl This is base URL.
     * @param apiUrl This is URL of Lenta API.
     * @param apiBody This is body for connection to Lenta API.
     * @param metricsCollector This is Collector for metrics
     *
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
     * @param apiUrl This is URL of Lenta API.
     * @param apiBody This is body for connection to Lenta API.
     *
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
     *
     * @return wineList This is array of json with parsed properties(One Json for one single wine) .
     */
    @Timed(PARSING_PROCESS_DURATION_SUMMARY)
    public ParserRspImpl getJson(Integer batchSize) {
        eventLogger.info(I_START_PARSING);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).connectTimeout(Duration.ofSeconds(30)).executor(executor).build();

        ParserRspImpl wineList = new ParserRspImpl();

        JSONArray jsonArr = new JSONArray(apiBody);
        for (int a = 0; a < jsonArr.length(); a++) {
            long startFetchingTime = new Date().getTime();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonArr.getJSONObject(a).toString()))
                    .build();

            HttpResponse<?> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (Exception e) {
                eventLogger.error(E_BAD_REQUEST, response.statusCode(), e);
                parsedWines.set(wineList.getJsonList().size());
                eventLogger.error(W_PAGE_PARSING_FAILED, e);
                metricsCollector.countParsingComplete("FAILED");
                return null;
            } finally {
                metricsCollector.fetchingPageDuration(new Date().getTime() - startFetchingTime);
                JSONObject jsonObject = new JSONObject(response.body().toString());
                JSONArray array = jsonObject.getJSONArray("skus");
                long startParsingingTime = new Date().getTime();
                for (int i = 0; i < array.length() ; i++) {
                    if (batchSize != null && (i < batchSize || i >= batchSize + 60)){
                        continue;
                    }
                    parsingInProgress.incrementAndGet();
                    metricsCollector.incParsingStarted();
                    Double wineOldPrice = array.getJSONObject(i).getJSONObject("regularPrice").getDouble("value");
                    Double wineNewPrice = array.getJSONObject(i).getJSONObject("cardPrice").getDouble("value");
                    String wineTitle = array.getJSONObject(i).getString("title");
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
                            .put("wineTitle", wineTitle);
                    if (jsonArr.getJSONObject(a).getString("nodeCode").equals("c529e2e61ea65b2c9f45b32b62d75a0b5")) {
                        jsonString.put("wineSparkling", true);
                    } else {
                        jsonString.put("wineSparkling", false);
                    }
                    StringBuilder productHtml = new StringBuilder()
                            .append(baseUrl)
                            .append(array.getJSONObject(i).getString("skuUrl"));

                    Document document = getItemHtml(String.valueOf(productHtml));

                    long startParsingTime = new Date().getTime();
                    wineList.add(getProperties(jsonString, document));
                    metricsCollector.parsingDetailsDuration(new Date().getTime() - startParsingTime);

                    eventLogger.info(I_WINE_DETAILS_PARSED, jsonString);
                    parsingInProgress.decrementAndGet();
                    metricsCollector.countParsingComplete("SUCCESS");

                    if (wineList.getJsonList().size() % 20 == 0){
                        eventLogger.info(I_WINES_PAGE_PARSED, wineList.getWines());
                        metricsCollector.parsingPageDuration(new Date().getTime() - startParsingingTime);
                    }
                }
            }
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
     * @param document This is html page of single wine.
     *
     * @return jsonString.
     */
    public JSONObject getProperties(JSONObject jsonString, Document document) {

        Elements elem = null;
        try {
            elem = Objects.requireNonNull(document).getElementsByClass("sku-card-tab-params__item");
        } catch (NullPointerException ex) {
            eventLogger.error(E_NULL_DOCUMENT, ex);
            return jsonString;
        } finally {
            for (Element element : elem) {
                Document iterdoc = null;

                try {
                    iterdoc = Jsoup.parse(element.toString());
                } catch (Exception ex) {
                    eventLogger.warn(W_FIELD_PARSING_FAILED, element.toString(), jsonString.getString("wineLink"));
                } finally {

                    String title = iterdoc.getElementsByClass("sku-card-tab-params__label-block").text();
                    Elements value = iterdoc.getElementsByClass("sku-card-tab-params__value");
                    if (value.isEmpty()) {
                        value = iterdoc.getElementsByClass("link sku-card-tab-params__link");
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
            }
        }
        return jsonString;
    }

    /**
     * Method of geting html
     *
     * @param productHtml This is URL.
     *
     * @return document This is html received from URL.
     */
    public Document getItemHtml(String productHtml) {
        long startFetchingTime = new Date().getTime();
        Document document = null;
        try {
            document = Jsoup.connect(String.valueOf(productHtml))
                    .header("Accept-Encoding", "gzip, deflate")
                    .userAgent("Opera")
                    .maxBodySize(0)
                    .timeout(15000)
                    .get();
        } catch (IOException e) {
            eventLogger.error(E_CHARACTERISTICS_ERROR, e);
        }
        metricsCollector.fetchingDetailsDuration(new Date().getTime() - startFetchingTime);
        return document;
    }
}
