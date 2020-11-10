package com.wine.to.up.lenta.service.parser.impl;

import com.wine.to.up.lenta.service.parser.ParserReqService;
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

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@AllArgsConstructor
@Getter
@Setter
public class ParserReqServiceImpl implements ParserReqService {

    private String baseUrl;

    private final String apiUrl;

    private final String apiBody;

    private HttpResponse<?> response;

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


    public ParserReqServiceImpl changeToLocal(){
        this.baseUrl = "";
        return this;
    }
    public ParserReqServiceImpl changeToOnline(){
        this.baseUrl = "https://lenta.com";
        return this;
    }

    public ParserRspImpl getJson() {

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
                log.error("Bad request: `%s` \n; Catch exception: `%s`", response.statusCode(), e);
                return null;
            } finally {
                JSONObject jsonObject = new JSONObject(response.body().toString());
                JSONArray array = jsonObject.getJSONArray("skus");

                for (int i = 0; i < array.length(); i++) {
                    Double wineOldPrice = array.getJSONObject(i).getJSONObject("regularPrice").getDouble("value");
                    Double wineNewPrice = array.getJSONObject(i).getJSONObject("cardPrice").getDouble("value");
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
                            .put("wineLink", baseUrl + array.getJSONObject(i).getString("skuUrl"));
                    if(jsonArr.getJSONObject(a).getString("nodeCode").equals("c529e2e61ea65b2c9f45b32b62d75a0b5")){
                        jsonString.put("wineSparkling", true);
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
                        log.error("Can't parse wine characteristics", e);
                    } finally {
                        wineList.add(getProperties(jsonString, document));
                    }
                }
            }
        }
        return wineList;
    }

    public JSONObject getProperties(JSONObject jsonString, Document document){

            Elements elem = Objects.requireNonNull(document).getElementsByClass("sku-card-tab-params__item");

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
        return jsonString;
    }
}
