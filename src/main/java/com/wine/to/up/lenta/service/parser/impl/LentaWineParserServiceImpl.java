package com.wine.to.up.lenta.service.parser.impl;

import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import com.wine.to.up.lenta.service.parser.LentaWineParserService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
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
        return ProductDTO.builder()
                .brand(jsonObject.getString("wineBrand"))
                .country(jsonObject.getString("wineCountry"))
                .flavor(jsonObject.getString("wineAroma"))
                .sugar(jsonObject.getString("wineSugarContent"))
                .color(jsonObject.getString("wineColour"))
                .gastronomy(jsonObject.getString("wineGastronomy"))
                .strength(jsonObject.getFloat("wineGastronomy"))
                .taste(jsonObject.getString("wineTaste"))
                .manufacturer(jsonObject.getString("winePackagingType"))
                .image(jsonObject.getString("imageUrl"))
                .oldPrice(jsonObject.getFloat("wineOldPrice"))
                .newPrice(jsonObject.getFloat("wineNewPrice"))
                .rating(jsonObject.getFloat("wineRating"))
                .build();
    }
}
