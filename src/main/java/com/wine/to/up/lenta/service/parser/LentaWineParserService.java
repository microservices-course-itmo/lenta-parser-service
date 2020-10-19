package com.wine.to.up.lenta.service.parser;

import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class LentaWineParserService {

    /**
     * This method convert JSON objects to Java objects "ProductDTO"
     *
     * @param wineList - List of JSON objects that parse from site
     *
     * @return List of ProductDTO
     */
    public List<ProductDTO> parseWineList(ParserRsp wineList) {

        List<ProductDTO> productDTOList = new ArrayList<>();

        JSONArray productList = wineList.getWineList();

        for (int i = 0; i < productList.length(); i++) {
            productDTOList.add(getProductDTO(productList.getJSONObject(i)));
        }
        return productDTOList;
    }

    private ProductDTO getProductDTO(JSONObject jsonObject) {
        return ProductDTO.builder()
                .brand(jsonObject.getString("wineName"))
                .country(jsonObject.getString("wineCountry"))
                .image(jsonObject.getString("wineImage"))
                .rating(jsonObject.get("wineRating").equals("") ? 0.0f : jsonObject.getInt("wineRating"))
                .discount(jsonObject.get("wineDiscountPercentage").equals("") ? 0 : Integer.parseInt(jsonObject.getString("wineDiscountPercentage")
                        .replace("%", "")))
                .price(Float.parseFloat(jsonObject.getString("winePriceDiscount")
                        .replace(" ₽", "")
                        .replace(" ", ".")))
                .oldPrice(Float.parseFloat(jsonObject.getString("winePriceNormal")
                        .replace(" ₽", "")
                        .replace("1 ", "1")
                        .replace(" ", ".")))
                .build();
    }
}
