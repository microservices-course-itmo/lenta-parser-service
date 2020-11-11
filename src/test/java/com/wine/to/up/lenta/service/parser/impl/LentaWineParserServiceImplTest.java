package com.wine.to.up.lenta.service.parser.impl;

import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.Test;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;


@Slf4j
@NoArgsConstructor
public class LentaWineParserServiceImplTest {
    @Test
    public void parserTest() {
        ProductDTO.ProductDTOBuilder productBuilder = ProductDTO.builder();
        float a = (float) 1578.99;
        productBuilder.oldPrice(a);
        a = (float) 699.0;
        productBuilder.newPrice(a);
        productBuilder.link("https://lenta.com/product/vino-les-coudriers-kot-dyu-ron-lekudr-aos-gn-kr-suh-franciya-075l-233824/");
        productBuilder.image("https://lenta.gcdn.co/globalassets/1/-/23/38/24/109234.png?preset=thumbnail");
        productBuilder.manufacturer("Стеклянная бутылка");
        productBuilder.brand("LES COUDRIERS");
        productBuilder.country("Франция");
        a = (float) 0.75;
        productBuilder.capacity(a);
        a = (float) 13.0;
        productBuilder.strength(a);
        productBuilder.color("Красный");
        productBuilder.sugar("Сухое");String str = "Гренаш,сира,сенсо,мурведр";
        productBuilder.grapeSort(Arrays.asList("Гренаш,сира,сенсо,мурведр".split(", ")));
        productBuilder.gastronomy("Отличное дополнение к блюдам из мяса, дичи, сырам.");
        productBuilder.taste("Приятный, хорошо сбалансированный вкус.");
        productBuilder.flavor("Приятный аромат с нотками красных фруктов.");
        a = (float) 5.0;
        productBuilder.rating(a);
        //verifyedDto.setSparkling();
        JSONObject testJson = new JSONObject();
        testJson.put("code", "458545");
        a = (float) 5.0;
        testJson.put("wineRating",a);
        testJson.put("wineLink","https://lenta.com/product/vino-les-coudriers-kot-dyu-ron-lekudr-aos-gn-kr-suh-franciya-075l-233824/");
        testJson.put("winePackagingType","Стеклянная бутылка");
        a = (float) 1578.99;
        testJson.put("wineOldPrice",a);
        a = (float) 13.0;
        testJson.put("wineStrength",a);
        testJson.put("wineGastronomy","Отличное дополнение к блюдам из мяса, дичи, сырам.");
        testJson.put("wineTaste","Приятный, хорошо сбалансированный вкус.");
        testJson.put("wineCountry","Франция");
        testJson.put("wineAroma","Приятный аромат с нотками красных фруктов.");
        testJson.put("wineSugarContent","Сухое");
        testJson.put("wineGrapeSort","Гренаш,сира,сенсо,мурведр");
        testJson.put("imageUrl","https://lenta.gcdn.co/globalassets/1/-/23/38/24/109234.png?preset=thumbnail");
        testJson.put("wineBrand","LES COUDRIERS");
        testJson.put("wineColour","Красный");
        a = (float) 699.0;
        testJson.put("wineNewPrice",a);
        testJson.put("wineCapacity","0.75");
        ParserRspImpl parserRspImlp = new ParserRspImpl();
        parserRspImlp.add(testJson);
        LentaWineParserServiceImpl lentaWineParserService = new LentaWineParserServiceImpl();
        lentaWineParserService.parseWineList(parserRspImlp);
        ProductDTO verified = productBuilder.build();
        assertEquals(lentaWineParserService.parseWineList(parserRspImlp).get(0).toString(), verified.toString());
    }
}
