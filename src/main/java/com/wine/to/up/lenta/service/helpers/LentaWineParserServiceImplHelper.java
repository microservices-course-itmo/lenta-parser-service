package com.wine.to.up.lenta.service.helpers;

import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Class helper for LentaWineParserServiceImpl.getProductDTO
 * checks json fields on NULL and fills builder`s fields
 */
public class LentaWineParserServiceImplHelper {

    private static final String WINECAPACITY = "wineCapacity";

    private LentaWineParserServiceImplHelper() {
        throw new IllegalStateException("Class helper");
    }

    public static void fillOldPrice(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineOldPrice")) {
            productBuilder.oldPrice(jsonObject.getFloat("wineOldPrice"));
        }
    }

    public static void fillNewPrice(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineNewPrice")) {
            productBuilder.newPrice(jsonObject.getFloat("wineNewPrice"));
        }
    }

    public static void fillImageUrl(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("imageUrl")) {
            productBuilder.image(jsonObject.getString("imageUrl"));
        }
    }

    public static void fillWineRating(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineRating")) {
            productBuilder.rating(jsonObject.getFloat("wineRating"));
        }
    }

    public static void fillWineLink(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineLink")) {
            productBuilder.link(String.valueOf(jsonObject.get("wineLink")));
        }
    }

    public static void fillWineBrand(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineBrand")) {
            productBuilder.brand(jsonObject.getString("wineBrand"));
        }
    }

    public static void fillWineCountry(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineCountry")) {
            productBuilder.country(jsonObject.getString("wineCountry"));
        }
    }

    public static void fillWineAroma(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineAroma")) {
            productBuilder.flavor(jsonObject.getString("wineAroma"));
        }
    }

    public static void fillWineSugarContent(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineSugarContent")) {
            productBuilder.sugar(jsonObject.getString("wineSugarContent"));
        }
    }

    public static void fillWineColour(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineColour")) {
            productBuilder.color(jsonObject.getString("wineColour"));
        }
    }

    public static void fillWineGastronomy(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineGastronomy")) {
            productBuilder.gastronomy(jsonObject.getString("wineGastronomy"));
        }
    }

    public static void fillWineStrength(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineStrength")) {
            productBuilder.strength(jsonObject.getFloat("wineStrength"));
        }
    }

    public static void fillWineSparkling(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineSparkling")){
            productBuilder.sparkling(true);
        }
    }

    public static void fillWineTaste(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineTaste")) {
            productBuilder.taste(jsonObject.getString("wineTaste"));
        }
    }

    public static void fillWinePackagingType(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("winePackagingType")) {
            productBuilder.manufacturer(jsonObject.getString("winePackagingType"));
        }
    }

    public static void fillWineGrapeSort(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineGrapeSort")) {
            productBuilder.grapeSort(Arrays.asList(jsonObject.getString("wineGrapeSort").split(", ")));
        }
    }

    public static void fillWineCapacity(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has(WINECAPACITY)) {
            if(jsonObject.getString(WINECAPACITY).contains(",")) {
                productBuilder.capacity(Float.parseFloat(jsonObject.getString(WINECAPACITY).replace(",", ".")));
            } else {
                productBuilder.capacity(jsonObject.getFloat(WINECAPACITY));
            }
        }
    }
}
