package com.wine.to.up.lenta.service.parser.impl;

import com.wine.to.up.lenta.service.parser.ParserReqService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Getter
@Setter
public class ParserReqServiceImpl implements ParserReqService {

    private final String baseUrl;

    private final String apiUrl;

    private final String apiBody;

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

    @SneakyThrows
    public ParserRspImpl getJsonList() {

        System.setProperty("webdriver.chrome.driver", "/Users/kosch71/Downloads/lenta-parser-service/src/main/resources/chromedriver");

        HttpClient client = HttpClient.newBuilder().build();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
        WebDriver driver = new ChromeDriver(options);

        ParserRspImpl wineList = new ParserRspImpl();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(apiBody))
                .build();

        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObject = new JSONObject(response.body().toString());

        JSONArray array = jsonObject.getJSONArray("skus");
        for (int i = 0; i < array.length(); i++) {


            Double wineOldPrice = array.getJSONObject(i).getJSONObject("regularPrice").getDouble("value");
            Double wineNewPrice = array.getJSONObject(i).getJSONObject("cardPrice").getDouble("value");
            String imageUrl = array.getJSONObject(i).getString("imageUrl");
            Double rating = array.getJSONObject(i).getDouble("averageRating");

            JSONObject jsonString = new JSONObject()
                    .put("imageUrl", imageUrl)
                    .put("wineOldPrice", wineOldPrice)
                    .put("wineNewPrice", wineNewPrice)
                    .put("wineRating", rating)
                    .put("wineLink", baseUrl + array.getJSONObject(i).getString("skuUrl"));

            wineList.add(getProperties(driver, jsonString, array, i));
        }

        driver.close();
        driver.quit();

        return wineList;
    }

    private JSONObject getProperties(WebDriver driver, JSONObject jsonString, JSONArray array, int i){
        driver.get(baseUrl + array.getJSONObject(i).getString("skuUrl"));
        String htmlContent = driver.getPageSource();
        Document document = Jsoup.parse(htmlContent);
        Elements elem = document.getElementsByClass("sku-card-tab-params__item");

        for (int j = 0; j < elem.size(); j++) {
            Document iterdoc = Jsoup.parse(elem.get(j).toString());
            String title = iterdoc.getElementsByClass("sku-card-tab-params__label-block").text();
            Elements value = iterdoc.getElementsByClass("sku-card-tab-params__value");
            if (value.size() == 0) {
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
            }
        }
        return jsonString;
    }
}
