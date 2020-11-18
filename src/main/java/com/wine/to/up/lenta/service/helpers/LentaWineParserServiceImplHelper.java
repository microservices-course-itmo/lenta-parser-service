package com.wine.to.up.lenta.service.helpers;

import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Class helper for LentaWineParserServiceImpl.getProductDTO
 * checks json fields on NULL and fills builder`s fields
 */

@Slf4j
public class LentaWineParserServiceImplHelper {

    private static final String WINECAPACITY = "wineCapacity";

    private LentaWineParserServiceImplHelper() {
        throw new IllegalStateException("Class helper");
    }

    public static void fillOldPrice(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineOldPrice")) {
            try {
                productBuilder.oldPrice(jsonObject.getFloat("wineOldPrice"));
            } catch (Exception ex){
                log.error("Can't set old price:", ex);
                productBuilder.oldPrice(null);
            }

        }
    }

    public static void fillNewPrice(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineNewPrice")) {
            try {
                productBuilder.newPrice(jsonObject.getFloat("wineNewPrice"));
            } catch (Exception ex){
                log.error("Can't set new price:", ex);
                productBuilder.newPrice(null);
            }
        }
    }

    public static void fillImageUrl(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("imageUrl")) {
            try {
                productBuilder.image(jsonObject.getString("imageUrl"));
            } catch (Exception ex){
                log.error("Can't set image :", ex);
                productBuilder.image(null);
            }
        }
    }

    public static void fillWineRating(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineRating")) {
            try {
                productBuilder.rating(jsonObject.getFloat("wineRating"));
            } catch (Exception ex){
                log.error("Can't set rating:", ex);
                productBuilder.rating(null);
            }
        }
    }

    public static void fillWineLink(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineLink")) {
            try {
                productBuilder.link(String.valueOf(jsonObject.get("wineLink")));
            } catch (Exception ex){
                log.error("Can't set link:", ex);
                productBuilder.link(null);
            }
        }
    }

    public static void fillWineBrand(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineBrand")) {
            try {
                productBuilder.brand(jsonObject.getString("wineBrand"));
            } catch (Exception ex){
                log.error("Can't set brand:", ex);
                productBuilder.brand(null);
            }
        }
    }

    public static void fillWineCountry(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineCountry")) {
            try {
                productBuilder.country(jsonObject.getString("wineCountry"));
            } catch (Exception ex){
                log.error("Can't set country:", ex);
                productBuilder.country(null);
            }
        }
    }

    public static void fillWineAroma(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineAroma")) {
            try {
                productBuilder.flavor(jsonObject.getString("wineAroma"));
            } catch (Exception ex){
                log.error("Can't set flavor:", ex);
                productBuilder.flavor(null);
            }
        }
    }

    public static void fillWineSugarContent(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineSugarContent")) {
            try {
                productBuilder.sugar(jsonObject.getString("wineSugarContent"));
            } catch (Exception ex){
                log.error("Can't set sugar:", ex);
                productBuilder.sugar(null);
            }
        }
    }

    public static void fillWineColour(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineColour")) {
            try {
                productBuilder.color(jsonObject.getString("wineColour"));
            } catch (Exception ex){
                log.error("Can't set color:", ex);
                productBuilder.color(null);
            }
        }
    }

    public static void fillWineGastronomy(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineGastronomy")) {
            try {
                productBuilder.gastronomy(jsonObject.getString("wineGastronomy"));
            } catch (Exception ex){
                log.error("Can't set gastronomy:", ex);
                productBuilder.gastronomy(null);
            }
        }
    }

    public static void fillWineStrength(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineStrength")) {
            try {
                productBuilder.strength(jsonObject.getFloat("wineStrength"));
            } catch (Exception ex){
                log.error("Can't set strength:", ex);
                productBuilder.strength(null);
            }
        }
    }

    public static void fillWineSparkling(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineSparkling")){
            try {
                productBuilder.sparkling(true);
            } catch (Exception ex){
                log.error("Can't set sparkling:", ex);
                productBuilder.sparkling(null);
            }
        }
    }

    public static void fillWineTaste(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineTaste")) {
            try {
                productBuilder.taste(jsonObject.getString("wineTaste"));
            } catch (Exception ex){
                log.error("Can't set taste:", ex);
                productBuilder.taste(null);
            }
        }
    }

    public static void fillWinePackagingType(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("winePackagingType")) {
            try {
                productBuilder.manufacturer(jsonObject.getString("winePackagingType"));
            } catch (Exception ex){
                log.error("Can't set manufacturer:", ex);
                productBuilder.manufacturer(null);
            }
        }
    }

    public static void fillWineGrapeSort(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineGrapeSort")) {
            try {
                productBuilder.grapeSort(Arrays.asList(jsonObject.getString("wineGrapeSort").split(", ")));
            } catch (Exception ex){
                log.error("Can't set grape sort:", ex);
                productBuilder.grapeSort(null);
            }
        }
    }

    public static void fillWineCapacity(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has(WINECAPACITY)) {
            try {
                if (jsonObject.getString(WINECAPACITY).contains(",")) {
                    productBuilder.capacity(Float.parseFloat(jsonObject.getString(WINECAPACITY).replace(",", ".")));
                } else {
                    productBuilder.capacity(jsonObject.getFloat(WINECAPACITY));
                }
            } catch (Exception ex){
                log.error("Can't set capacity", ex);
                productBuilder.capacity(null);
            }
        }
    }
}
