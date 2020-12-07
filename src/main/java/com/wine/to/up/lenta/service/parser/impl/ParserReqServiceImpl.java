package com.wine.to.up.lenta.service.parser.impl;

import com.wine.to.up.commonlib.annotations.InjectEventLogger;
import com.wine.to.up.commonlib.logging.EventLogger;
import com.wine.to.up.lenta.service.components.LentaServiceMetricsCollector;
import com.wine.to.up.lenta.service.parser.ParserReqService;
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

import static com.wine.to.up.lenta.service.logging.LentaParserServiceNotableEvents.*;

@Slf4j
@AllArgsConstructor
@Getter
@Setter
@Scope(value="prototype")
public class ParserReqServiceImpl implements ParserReqService {

    private static final String PARSED_WINES_COUNT = "parsed_wines_count";

    private String baseUrl;

    private final String apiUrl;

    private final String apiBody;

    private HttpResponse<?> response;

    private final LentaServiceMetricsCollector metricsCollector;

    private final AtomicInteger parsedWines = new AtomicInteger();

    @InjectEventLogger
    private EventLogger eventLogger;

    private static final String BRAND_NAME = "Бренд";
    private static final String COUNTRY_NAME = "Страна производителя";
    private static final String CAPACITY_NAME = "Литраж";
    private static final String STRENGTH_NAME = "Крепость (%)";
    private static final String COLOR_NAME = "Цвет";
    private static final String SUGAR_NAME = "Содержание сахара";
    private static final String GRAPE_SORT_NAME = "Сорт винограда";
    private static final String AROMA_NAME = "Аромат";
    private static final String GACTRANOMY = "Гастрономия";
    private static final String TASTE = "Вкус";
    private static final String MANUFACTURER = "Вид упаковки";
    private static final String IMAGEURL = "imageUrl";

    public ParserReqServiceImpl(String baseUrl, String apiUrl, String apiBody, LentaServiceMetricsCollector metricsCollector) {
        this.baseUrl = baseUrl;
        this.apiUrl = apiUrl;
        this.apiBody = apiBody;
        this.response = null;
        this.metricsCollector = Objects.requireNonNull(metricsCollector, "Can't get metricsCollector");
        Metrics.gauge(PARSED_WINES_COUNT, parsedWines);
    }

    public ParserReqServiceImpl(String baseUrl, String apiUrl, String apiBody, HttpResponse httpResponse) {
        this.baseUrl = baseUrl;
        this.apiUrl = apiUrl;
        this.apiBody = apiBody;
        this.response = httpResponse;
        this.metricsCollector = null;
    }

    public ParserRspImpl getJson() {
        eventLogger.info(I_START_PARSING);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        HttpClient client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).connectTimeout(Duration.ofSeconds(30)).executor(executor).build();

        ParserRspImpl wineList = new ParserRspImpl();

        JSONArray jsonArr = new JSONArray(apiBody);
        for (int a = 0; a < jsonArr.length(); a++) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonArr.getJSONObject(a).toString()))
                    .build();

            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());

            } catch (Exception e) {
                eventLogger.error(E_BAD_REQUEST, response.statusCode(), e);
                parsedWines.set(wineList.getJsonList().size());
                return null;
            } finally {
                JSONObject jsonObject = new JSONObject(response.body().toString());
                JSONArray array = jsonObject.getJSONArray("skus");

                for (int i = 0; i < array.length(); i++) {
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
                    if(jsonArr.getJSONObject(a).getString("nodeCode").equals("c529e2e61ea65b2c9f45b32b62d75a0b5")){
                        jsonString.put("wineSparkling", true);
                    } else {
                        jsonString.put("wineSparkling", false);
                    }
                    StringBuilder productHtml = new StringBuilder()
                            .append(baseUrl)
                            .append(array.getJSONObject(i).getString("skuUrl"));
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
                    } finally {
                        wineList.add(getProperties(jsonString, document));
                    }
                }
            }
        }
        eventLogger.info(I_WINES_PARSED, wineList.getJsonList().size());
        parsedWines.set(wineList.getJsonList().size());
        return wineList;
    }

    public JSONObject getProperties(JSONObject jsonString, Document document){
        Elements elem = null;
        try {
            elem = Objects.requireNonNull(document).getElementsByClass("sku-card-tab-params__item");
        } catch (NullPointerException ex){
            eventLogger.error(E_NULL_DOCUMENT, ex);
            return jsonString;
        } finally {
            for (Element element : elem) {
                Document iterdoc = Jsoup.parse(element.toString());
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
        return jsonString;
    }
}
