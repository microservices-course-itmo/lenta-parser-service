//package com.wine.to.up.lenta.service.helpers;
//
//import com.wine.to.up.lenta.service.db.dto.ProductDTO;
//import lombok.NoArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONObject;
//import org.junit.Test;
//
//import java.util.Arrays;
//
//import static org.junit.Assert.assertEquals;
//
//@NoArgsConstructor
//public class LentaWineParserServiceImplHelperTest {
//    @Test
//    public void parserServiceImplHelper() {
//        ProductDTO.ProductDTOBuilder productBuilder = ProductDTO.builder();
//        ProductDTO.ProductDTOBuilder testBuilder = ProductDTO.builder();
//        JSONObject testJson = new JSONObject();
//
//        productBuilder.oldPrice(1578.99f)
//                .newPrice(699.0f)
//                .image("https://lenta.gcdn.co/globalassets/1/-/23/38/24/109234.png?preset=thumbnail")
//                .manufacturer("Стеклянная бутылка")
//                .brand("LES COUDRIERS")
//                .country("Франция")
//                .capacity(0.75f)
//                .strength(13.0f)
//                .grapeSort(Arrays.asList("Гренаш,сира,сенсо,мурведр".split(", ")))
//                .gastronomy("Отличное дополнение к блюдам из мяса, дичи, сырам.")
//                .taste("Приятный, хорошо сбалансированный вкус.")
//                .flavor("Приятный аромат с нотками красных фруктов.")
//                .rating(5.0f)
//                .build();
//
//        testJson.put("code", "458545")
//                .put("wineRating",5.0f)
//                .put("winePackagingType","Стеклянная бутылка")
//                .put("wineOldPrice",1578.99f)
//                .put("wineStrength", 13.0f)
//                .put("wineGastronomy","Отличное дополнение к блюдам из мяса, дичи, сырам.")
//                .put("wineTaste","Приятный, хорошо сбалансированный вкус.")
//                .put("wineCountry","Франция")
//                .put("wineAroma","Приятный аромат с нотками красных фруктов.")
//                .put("wineGrapeSort","Гренаш,сира,сенсо,мурведр")
//                .put("imageUrl","https://lenta.gcdn.co/globalassets/1/-/23/38/24/109234.png?preset=thumbnail")
//                .put("wineBrand","LES COUDRIERS")
//                .put("wineNewPrice",699.0f)
//                .put("wineCapacity","0.75");
//
//        LentaWineParserServiceImplHelper.fillOldPrice(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillNewPrice(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillImageUrl(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWineRating(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWineLink(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWineBrand(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWineCountry(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWineAroma(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWineSugarContent(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWineColour(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWineGastronomy(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWineStrength(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWineSparkling(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWineTaste(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWinePackagingType(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWineGrapeSort(testJson, testBuilder);
//        LentaWineParserServiceImplHelper.fillWineCapacity(testJson, testBuilder);
//        testBuilder.build();
//        assertEquals(testBuilder.toString(), productBuilder.toString());
//    }
//}