package com.wine.to.up.lenta.service.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@AllArgsConstructor
@Getter
@Setter
public class ParserReqService {

    private final String baseUrl;

    private final String userAgent;

    @SneakyThrows
    public ParserRsp getJsonList() {

        Document page = Jsoup.connect(baseUrl).userAgent(userAgent).get();
        String wineSelector = "div.catalog-grid-container.catalog-page__grid > div > div > a";
        Elements wineElements = page.select(wineSelector);
        ParserRsp wineList = new ParserRsp();

        Pattern wineNamePatternEN = Pattern.compile("\\b[A-Z]+\\b");
        Pattern wineNamePatternRU = Pattern.compile("\\b[А-Я][А-Я0-9]+\\b");
        Pattern wineTypePattern = Pattern.compile("(роз|кра|бел|стол|кр\\.).*?,");

        String wineTitleSelector = "div.sku-card-small__head > div.sku-card-small__title";
        String winePriceNormalSelector = "div.sku-prices-block.sku-prices-block--small.sku-card-small__prices > div.sku-prices-block__item.sku-prices-block__item--regular > div.sku-price.sku-price--regular.sku-price--small.sku-prices-block__price";
        String winePriceDiscountSelector = "div.sku-prices-block.sku-prices-block--small.sku-card-small__prices > div.sku-prices-block__item.sku-prices-block__item--primary > div.sku-price.sku-price--primary.sku-price--small.sku-prices-block__price";
        String wineDiscountPercentageSelector = "div.sku-card-small__image-container > div.sku-card-small__labels > div";
        String wineImageSelector = "div.sku-card-small__image-container > div.square.sku-card-small__image > div > img";
        String wineStarsSelector = "div.sku-card-small__rating-info > div.rating.sku-card-small__commenter-rating-overview-stars";

        for (Element e : wineElements) {
            String wineTitle = e.select(wineTitleSelector).text();
            String winePriceNormal = e.select(winePriceNormalSelector).text();
            String winePriceDiscount = e.select(winePriceDiscountSelector).text();
            String wineDiscountPercentage = e.select(wineDiscountPercentageSelector).text();
            String wineImage = e.select(wineImageSelector).attr("src");
            String wineCountry;
            String wineType = "";
            String wineVolume;
            StringBuilder wineName = new StringBuilder();

            wineImage = wineImage.replace("?preset=thumbnail\"", "");
            String wineStars = e.select(wineStarsSelector).toString();
            List<String> allStars = new ArrayList<>();
            List<String> fullName = new ArrayList<>();
            String[] wineSplitArray = wineTitle.split("\\,");

            if (wineSplitArray.length > 1) {
                wineCountry = wineSplitArray[wineSplitArray.length - 2];
                wineVolume = wineSplitArray[wineSplitArray.length - 1];
            } else {
                wineCountry = wineSplitArray[wineSplitArray.length - 1];
                wineVolume = wineSplitArray[0];
            }

            Matcher wineStarMatch = Pattern.compile(".*?rating__star--active")
                    .matcher(wineStars);
            while (wineStarMatch.find()) {
                allStars.add(wineStarMatch.group());
            }

            Matcher wineNameMatchEN = wineNamePatternEN
                    .matcher(wineTitle);
            while (wineNameMatchEN.find()) {
                fullName.add(wineNameMatchEN.group());
            }

            Matcher wineTypeMatch = wineTypePattern
                    .matcher(wineTitle);
            while (wineTypeMatch.find()) {
                wineType = wineTypeMatch.group().replace(",", "");
            }

            if (fullName.toString().length() == 2) {
                Matcher wineNameMatchRU = wineNamePatternRU
                        .matcher(wineTitle);
                while (wineNameMatchRU.find()) {
                    fullName.add(wineNameMatchRU.group());
                }
            }

            for (String s : fullName) {
                wineName.append(s).append(" ");
            }

            wineName = new StringBuilder(wineName.toString().replace("L", "").replace("DO", ""));
            wineImage = wineImage.replace("?preset=thumbnail", "");

            JSONObject jsonString = new JSONObject()
                    .put("wineTitle", wineTitle)
                    .put("wineImage", wineImage)
                    .put("winePriceNormal", winePriceNormal)
                    .put("winePriceDiscount", winePriceDiscount)
                    .put("wineDiscountPercentage", wineDiscountPercentage)
                    .put("wineRating", allStars.size())
                    .put("wineCountry", wineCountry)
                    .put("wineVolume", wineVolume)
                    .put("wineName", wineName.toString())
                    .put("wineType", wineType);

            wineList.add(jsonString);
        }
        return wineList;
    }
}
