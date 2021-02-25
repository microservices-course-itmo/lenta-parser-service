//package com.wine.to.up.lenta.service.helpers;
//
//import com.wine.to.up.lenta.service.db.dto.ProductDTO;
//import com.wine.to.up.parser.common.api.schema.ParserApi;
//import org.json.JSONObject;
//import org.junit.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ExportProductDtoListHelperTest {
//
//    @Test
//    public void exportProductDTOTest(){
//
//        ParserApi.Wine.Builder builder = ParserApi.Wine.newBuilder();
//        ProductDTO wine = getProduct();
//
//        ExportProductDtoListHelper.fillBrand(builder, wine);
//        ExportProductDtoListHelper.fillSparkling(builder, wine);
//        ExportProductDtoListHelper.fillCountry(builder, wine);
//        ExportProductDtoListHelper.fillCapacity(builder, wine);
//        ExportProductDtoListHelper.fillStrength(builder, wine);
//        ExportProductDtoListHelper.fillColor(builder, wine);
//        ExportProductDtoListHelper.fillSugar(builder, wine);
//        ExportProductDtoListHelper.fillOldPrice(builder, wine);
//        ExportProductDtoListHelper.fillImage(builder, wine);
//        ExportProductDtoListHelper.fillManufacturer(builder, wine);
//        ExportProductDtoListHelper.fillLink(builder, wine);
//        ExportProductDtoListHelper.fillGrapeSort(builder, wine);
//        ExportProductDtoListHelper.fillGastronomy(builder, wine);
//        ExportProductDtoListHelper.fillTaste(builder, wine);
//        ExportProductDtoListHelper.fillFlavor(builder, wine);
//        ExportProductDtoListHelper.fillRating(builder, wine);
//        ExportProductDtoListHelper.fillTitle(builder, wine);
//        assertNotNull(builder);
//
//
//    }
//
//    private ProductDTO getProduct(){
//
//        ProductDTO.ProductDTOBuilder testBuilder = ProductDTO.builder();
//        JSONObject testJson = new JSONObject();
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
//
//        return testBuilder.build();
//    }
//
//}