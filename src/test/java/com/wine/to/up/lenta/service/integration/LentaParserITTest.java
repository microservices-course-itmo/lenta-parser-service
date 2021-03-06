//package com.wine.to.up.lenta.service.integration;
//
//import com.wine.to.up.lenta.service.integration.AbstractTest;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.junit.Assert.*;
//
//public class LentaParserITTest extends AbstractTest {
//    private static final String BRAND_NAME = "Бренд";
//    private static final String COUNTRY_NAME = "Страна производителя";
//    private static final String CAPACITY_NAME = "Литраж";
//    private static final String STRENGTH_NAME = "Крепость (%)";
//    private static final String COLOR_NAME = "Цвет";
//    private static final String SUGAR_NAME = "Содержание сахара";
//    private static final String GRAPE_SORT_NAME = "Сорт винограда";
//    private static final String AROMA_NAME = "Аромат";
//    private static final String GACTRANOMY = "Гастрономия";
//    private static final String TASTE = "Вкус";
//    private static final String MANUFACTURER = "Вид упаковки";
//    private static final String IMAGEURL = "imageUrl";
//
//    @Override
//    @Before
//    public void setUp() {
//        super.setUp();
//    }
//    //GET API test
//    @Test
//    @Ignore
//    public void testAnswer() throws Exception {
//        String uri = "/lenta";
//        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
//                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//        Document httpcontent = Jsoup.parse(mvcResult.getResponse().getContentAsString());
//        JSONArray jsonarray = new JSONArray(mvcResult.getResponse().getContentAsString());
//        JSONObject jsonObject = new JSONObject();
//        for(int i = 0; i < 5; i++)
//            jsonObject = jsonarray.getJSONObject(i);
//            assertFalse(jsonObject.getString("wineLink").isEmpty());
//            assertFalse(jsonObject.getString("winePackagingType").isEmpty());
//            assertFalse(jsonObject.getFloat("wineOldPrice") < 0);
//            assertFalse(jsonObject.getFloat("wineStrength")< 0);
//            assertFalse(jsonObject.getString("wineGastronomy").isEmpty());
//            assertFalse(jsonObject.getString("wineTaste").isEmpty());
//            assertFalse(jsonObject.getString("wineCountry").isEmpty());
//            assertFalse(jsonObject.getString("wineAroma").isEmpty());
//            assertFalse(jsonObject.getString("wineSugarContent").isEmpty());
//            assertFalse(jsonObject.getString("wineGrapeSort").isEmpty());
//            assertFalse(jsonObject.getString("imageUrl").isEmpty());
//            assertFalse(jsonObject.getString("wineBrand").isEmpty());
//            assertFalse(jsonObject.getString("wineColour").isEmpty());
//            assertFalse(jsonObject.getFloat("wineNewPrice")< 0);
//            assertFalse(jsonObject.getString("wineCapacity").isEmpty());
//    }
//}
//
