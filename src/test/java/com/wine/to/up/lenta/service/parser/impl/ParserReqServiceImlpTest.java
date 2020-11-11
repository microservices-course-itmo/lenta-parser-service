package com.wine.to.up.lenta.service.parser.impl;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.springframework.validation.annotation.Validated;
import java.io.File;
import java.io.IOException;
import java.net.http.HttpResponse;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;


@Validated
@Slf4j
public class ParserReqServiceImlpTest  extends Mockito{

    @Test
    public void testParser() throws IOException {
        String url = "https://lenta.com";
        String apiUrl = "";
        String apiBody = "";
        HttpResponse<?> response;
        HttpResponse httpResponse = mock(HttpResponse.class);
        ParserReqServiceImpl parserReqServiceImpl = new ParserReqServiceImpl(url, apiBody ,apiBody ,httpResponse);
        JSONObject jsonAns = new JSONObject();
        JSONObject jo = new JSONObject();
        jo.put("code", "458545");
        jo.put("brand", "CALVET SELECTION DES PRINCES");
        jo.put("skuUrl", "file:///D:/Lessons/Java/lenta-parser-service/src/main/resources/testVine.html");
        JSONObject verifiedJson = new JSONObject();
        StringBuilder productHtml = new StringBuilder().append(jo.getString("skuUrl"));
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
        File in = new File("src/main/resources/testVine.html");
        Document iterdoc = Jsoup.parse(in, null);
        assertEquals(verifiedJson.toString(), parserReqServiceImpl.changeToLocal().getProperties(jsonAns, iterdoc).toString() );
    }
}

