package com.wine.to.up.lenta.service.parser.impl;

import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import com.wine.to.up.lenta.service.helpers.LentaWineParserServiceImplHelper;
import com.wine.to.up.lenta.service.parser.LentaWineParserService;
import com.wine.to.up.lenta.service.services.DataBaseService;
import com.wine.to.up.lenta.service.services.impl.DataBaseServiceImpl;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * This class consists of methods of creating and filling DTO
 */
@Slf4j
@NoArgsConstructor
public class LentaWineParserServiceImpl implements LentaWineParserService {

    @Autowired
    private DataBaseServiceImpl dataBaseService;

    /**
     * This class consists of methods of creating DTO
     *
     * @param wineList - array of parsed wines with properties
     *
     * @return List of ProductDTO - list of class instances with one wine
     */
    public List<ProductDTO> parseWineList(ParserRspImpl wineList) {

        List<ProductDTO> productDTOList = new ArrayList<>();

        JSONArray productList = wineList.getWines();
        for (int i = 0; i < productList.length(); i++) {
            productDTOList.add(getProductDTO(productList.getJSONObject(i)));
        }
        return productDTOList;
    }

    /**
     * This class consists of methods of transform parsed properties in DTO format
     *
     * @param jsonObject - json with one parsed wine
     *
     * @return ProductDTO - class instance with one wine
     */
    private ProductDTO getProductDTO(JSONObject jsonObject) {
        ProductDTO.ProductDTOBuilder productBuilder = ProductDTO.builder();

        LentaWineParserServiceImplHelper.fillOldPrice(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillNewPrice(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillImageUrl(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineRating(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineLink(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineBrand(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineCountry(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineAroma(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineSugarContent(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineColour(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineGastronomy(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineStrength(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineSparkling(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineTaste(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWinePackagingType(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineGrapeSort(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineCapacity(jsonObject, productBuilder);
        LentaWineParserServiceImplHelper.fillWineTitle(jsonObject, productBuilder);

        return productBuilder.build();
    }
}
