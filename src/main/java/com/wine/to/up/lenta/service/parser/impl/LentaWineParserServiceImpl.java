package com.wine.to.up.lenta.service.parser.impl;

import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import com.wine.to.up.lenta.service.parser.LentaWineParserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class LentaWineParserServiceImpl implements LentaWineParserService {

    public List<ProductDTO> parseWineList(ParserRspImpl wineList) {

        List<ProductDTO> productDTOList = new ArrayList<>();

        JSONArray productList = wineList.getWineList();
        for (int i = 0; i < productList.length(); i++) {
            productDTOList.add(getProductDTO(productList.getJSONObject(i)));
        }
        return productDTOList;
    }

    private ProductDTO getProductDTO(JSONObject jsonObject) {
        ProductDTO.ProductDTOBuilder productBuilder = ProductDTO.builder();

        productBuilder.oldPrice(jsonObject.getFloat("wineOldPrice"));
        productBuilder.newPrice(jsonObject.getFloat("wineNewPrice"));
        productBuilder.image(jsonObject.getString("imageUrl"));
        productBuilder.rating(jsonObject.getFloat("wineRating"));
        productBuilder.link(jsonObject.getString("wineLink"));

        if (jsonObject.has("wineBrand")) {
            productBuilder.brand(jsonObject.getString("wineBrand"));
        }
        if (jsonObject.has("wineCountry")) {
            productBuilder.country(jsonObject.getString("wineCountry"));
        }
        if (jsonObject.has("wineAroma")) {
            productBuilder.flavor(jsonObject.getString("wineAroma"));
        }
        if (jsonObject.has("wineSugarContent")) {
            productBuilder.sugar(jsonObject.getString("wineSugarContent"));
        }
        if (jsonObject.has("wineColour")) {
            productBuilder.color(jsonObject.getString("wineColour"));
        }
        if (jsonObject.has("wineGastronomy")) {
            productBuilder.gastronomy(jsonObject.getString("wineGastronomy"));
        }
        if (jsonObject.has("wineStrength")) {
            productBuilder.strength(jsonObject.getFloat("wineStrength"));
        }
        if (jsonObject.has("wineTaste")) {
            productBuilder.taste(jsonObject.getString("wineTaste"));
        }
        if (jsonObject.has("winePackagingType")) {
            productBuilder.manufacturer(jsonObject.getString("winePackagingType"));
        }
        if (jsonObject.has("wineGrapeSort")) {
            productBuilder.grapeSort(Arrays.asList(jsonObject.getString("wineGrapeSort").split(", ")));
        }
        productBuilder.capacity(jsonObject.getFloat("wineCapacity"));

        return productBuilder.build();
    }
}
