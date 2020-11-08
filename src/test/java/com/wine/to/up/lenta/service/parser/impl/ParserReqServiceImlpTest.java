package com.wine.to.up.lenta.service.parser.impl;

import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.validation.annotation.Validated;

import static org.junit.Assert.assertEquals;


@Validated
@Slf4j
public class ParserReqServiceImlpTest {

    @Test
    public void testParser() {
        String url = "https://lenta.com";
        String apiUrl = "";
        String apiBody = "";
        ParserReqServiceImpl parserReqServiceImpl = new ParserReqServiceImpl(url, apiUrl, apiBody);
        System.setProperty("webdriver.chrome.driver", "/Lessons/Java/lenta-parser-service/src/main/resources/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors");
        WebDriver driver = new ChromeDriver(options);
        JSONObject jo = new JSONObject();
        jo.put("code", "458545");
        jo.put("brand", "CALVET SELECTION DES PRINCES");
        jo.put("skuUrl", "file:///D:/Lessons/Java/lenta-parser-service/src/main/resources/testVine.html");
        JSONObject jsonAns = new JSONObject();
        JSONArray jsonArr = new JSONArray();
        jsonArr.put(jo);
        JSONObject verifiedJson = new JSONObject();
        verifiedJson.put("winePackagingType","Стеклянная бутылка");
        verifiedJson.put("wineTaste","Хорошо сбалансированный, деликатный вкус.");
        verifiedJson.put("wineCountry","Франция");
        verifiedJson.put("wineAroma","Ароматный фруктовый букет с ванильными нотками.");
        verifiedJson.put("wineStrength", 13);
        verifiedJson.put("wineSugarContent","Сухое");
        verifiedJson.put("wineGrapeSort","«Мерло» 80%, «каберне совиньон» 20%");
        verifiedJson.put("wineBrand","CALVET SELECTION DES PRINCES");
        verifiedJson.put("wineGastronomy","Жаркое из дичи, легкие салаты и различные виды сыров.");
        verifiedJson.put("wineColour","Красный");
        verifiedJson.put("wineCapacity","0.75");
        assertEquals(verifiedJson.toString(), parserReqServiceImpl.changeToLocal().getProperties(driver, jsonAns, jsonArr,0).toString() );
        //return new ResponseEntity<Object>(parserReqServiceImpl.changeToLocal().getProperties(driver, jsonAns, jsonArr,0).toString(), HttpStatus.OK);
    }
}
