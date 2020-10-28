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

    private static final String API_BODY = "{\"nodeCode\":\"ca36224e8c82be1181aad8835310555bf\",\"filters\":[],\"tag\":\"\",\"typeSearch\":1,\"sortingType\":\"ByPriority\",\"offset\":0,\"limit\":\"20\",\"updateFilters\":true}";

    private final String baseUrl;

    private final String userAgent;

    private final String apiUrl;

    @SneakyThrows
    public ParserRspImpl getJsonList() {

        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");

        HttpClient client = HttpClient.newBuilder().build();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
        WebDriver driver = new ChromeDriver(options);

        ParserRspImpl wineList = new ParserRspImpl();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(API_BODY))
                .build();

        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObject = new JSONObject(response.body().toString());
        List<String> list = new ArrayList<String>();
        JSONArray array = jsonObject.getJSONArray("skus");
        for(int i = 0 ; i < array.length() ; i++){
            driver.get(baseUrl + array.getJSONObject(i).getString("skuUrl"));
            String htmlContent = driver.getPageSource();
            Document document = Jsoup.parse(htmlContent);
            if (document.select("label:containsOwn(Выдержка)").size() > 1){
                Float wineProof = Float.parseFloat(document.select("div.sku-card-tab-params__group > div:nth-child(1) > dd").text());
                String wineGastronomy = document.select("div.sku-card-tab-params__group > div:nth-child(2) > dd").text();
                String winePackagingType = document.select("div.sku-card-tab-params__group > div:nth-child(3) > dd").text();
                String wineSugarContent = document.select("div.sku-card-tab-params__group > div:nth-child(4) > dd").text();
                String wineColour = document.select("div.sku-card-tab-params__group > div:nth-child(5) > dd").text();
                String wineExtract = document.select("div.sku-card-tab-params__group > div:nth-child(6) > dd").text();
                String wineExtractContainer = document.select("div.sku-card-tab-params__group > div:nth-child(7) > dd").text();
                String wineExtractMethod = document.select("div.sku-card-tab-params__group > div:nth-child(8) > dd").text();
                String wineAroma = document.select("div.sku-card-tab-params__group > div:nth-child(9) > dd").text();
                String wineTaste = document.select("div.sku-card-tab-params__group > div:nth-child(10) > dd").text();
                String wineBrand = document.select("div.sku-card-tab-params__group > div:nth-child(11) > a").text();
                String wineGrapeSort = document.select("div.sku-card-tab-params__group > div:nth-child(12) > dd").text();
                String wineServingTmp = document.select("div.sku-card-tab-params__group > div:nth-child(13) > dd").text();
                Integer wineServingTmpFrom = Integer.parseInt(wineServingTmp.split("–")[0]);
                Integer wineServingTmpTo = Integer.parseInt(wineServingTmp.split("–")[1]);
                String wineVendor = document.select("div.sku-card-tab-params__group > div:nth-child(14) > dd").text();
                String wineCountry = document.select("div.sku-card-tab-params__group > div:nth-child(15) > dd").text();
                Float wineVolume = Float.parseFloat((document.select("div.sku-card-tab-params__group > div:nth-child(16) > dd").text()).split(" ")[0]);
                String wineRecommendDishes = document.select("div.sku-card-tab-params__group > div:nth-child(17) > dd").text();
                String wineBrandHistory = document.select("div.sku-card-tab-params__group > div:nth-child(18) > dd").text();
                String wineClassification = document.select("div.sku-card-tab-params__group > div:nth-child(21) > dd").text();
                String wineStylistics = document.select("div.sku-card-tab-params__group > div:nth-child(27) > dd").text();
                String wineStyle = document.select("div.sku-card-tab-params__group > div:nth-child(28) > dd").text();
                Float wineOldPrice = array.getJSONObject(i).getJSONObject("regularPrice").getFloat("value");
                Float wineNewPrice = array.getJSONObject(i).getJSONObject("cardPrice").getFloat("value");
                String imageUrl = array.getJSONObject(i).getString("imageUrl");
                Float rating = array.getJSONObject(i).getFloat("averageRating");
//                Converting to json object
                JSONObject jsonString = new JSONObject()
                        .put("wineBrand", wineBrand)
                        .put("wineAroma", wineAroma)
                        .put("wineColour", wineColour)
                        .put("wineExtract", wineExtract)
                        .put("wineCountry", wineCountry)
                        .put("wineVolume", wineVolume)
                        .put("wineGastronomy", wineGastronomy)
                        .put("wineGrapeSort", wineGrapeSort)
                        .put("wineVendor",wineVendor)
                        .put("wineRecommendDishes", wineRecommendDishes)
                        .put("wineBrandHistory", wineBrandHistory)
                        .put("wineClassification", wineClassification)
                        .put("wineServingTmpFrom", wineServingTmpFrom)
                        .put("wineServingTmpTo", wineServingTmpTo)
                        .put("wineStylistics", wineStylistics)
                        .put("wineStyle", wineStyle)
                        .put("wineExtractContainer", wineExtractContainer)
                        .put("wineExtractMethod", wineExtractMethod)
                        .put("wineTaste", wineTaste)
                        .put("wineSugarContent", wineSugarContent)
                        .put("wineProof", wineProof)
                        .put("winePackagingType", winePackagingType)
                        .put("wineValue", wineOldPrice)
                        .put("imageUrl", imageUrl)
                        .put("wineOldPrice", imageUrl)
                        .put("wineNewPrice", wineNewPrice)
                        .put("wineRating", rating);
//          Adding json object
                wineList.add(jsonString);
            }
        }
        driver.close();
        driver.quit();

        return wineList;
    }
}
