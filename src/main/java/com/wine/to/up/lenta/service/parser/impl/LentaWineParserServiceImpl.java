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

        JSONArray productList = wineList.getWines();
        for (int i = 0; i < productList.length(); i++) {
            productDTOList.add(getProductDTO(productList.getJSONObject(i)));
        }
        return productDTOList;
    }

    private ProductDTO getProductDTO(JSONObject jsonObject) {
        ProductDTO.ProductDTOBuilder productBuilder = ProductDTO.builder();

        if (jsonObject.has("wineOldPrice")) {
            productBuilder.oldPrice(jsonObject.getFloat("wineOldPrice"));
        }
        if (jsonObject.has("wineNewPrice")) {
            productBuilder.newPrice(jsonObject.getFloat("wineNewPrice"));
        }
        if (jsonObject.has("imageUrl")) {
            productBuilder.image(jsonObject.getString("imageUrl"));
        }
        if (jsonObject.has("wineRating")) {
            productBuilder.rating(jsonObject.getFloat("wineRating"));
        }
        if (jsonObject.has("wineLink")) {
            productBuilder.link(String.valueOf(jsonObject.get("wineLink")));
        }
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
        if (jsonObject.has("wineSparkling")){
            productBuilder.sparkling(true);
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
        if (jsonObject.has("wineCapacity")) {
            if(jsonObject.getString("wineCapacity").contains(",")) {
                productBuilder.capacity(Float.parseFloat(jsonObject.getString("wineCapacity").replace(",", ".")));
            } else {
                productBuilder.capacity(jsonObject.getFloat("wineCapacity"));
            }
        }

        return productBuilder.build();
    }
}
