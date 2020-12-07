package com.wine.to.up.lenta.service.helpers;

import com.wine.to.up.commonlib.annotations.InjectEventLogger;
import com.wine.to.up.commonlib.logging.EventLogger;
import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.Arrays;

import static com.wine.to.up.lenta.service.logging.LentaParserServiceNotableEvents.*;

/**
 * Class helper for LentaWineParserServiceImpl.getProductDTO
 * checks json fields on NULL and fills builder`s fields
 */

@Slf4j
public class LentaWineParserServiceImplHelper {

    private static final String WINECAPACITY = "wineCapacity";

    @InjectEventLogger
    private static EventLogger eventLogger;

    private LentaWineParserServiceImplHelper() {
        throw new IllegalStateException("Class helper");
    }

    public static void fillOldPrice(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineOldPrice")) {
            try {
                productBuilder.oldPrice(jsonObject.getFloat("wineOldPrice"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"old price:", ex);
                productBuilder.oldPrice(null);
            }

        }
    }

    public static void fillNewPrice(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineNewPrice")) {
            try {
                productBuilder.newPrice(jsonObject.getFloat("wineNewPrice"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT, "new price", ex);
                productBuilder.newPrice(null);
            }
        }
    }

    public static void fillImageUrl(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("imageUrl")) {
            try {
                productBuilder.image(jsonObject.getString("imageUrl"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"image :", ex);
                productBuilder.image(null);
            }
        }
    }

    public static void fillWineRating(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineRating")) {
            try {
                productBuilder.rating(jsonObject.getFloat("wineRating"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"rating:", ex);
                productBuilder.rating(null);
            }
        }
    }

    public static void fillWineLink(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineLink")) {
            try {
                productBuilder.link(String.valueOf(jsonObject.get("wineLink")));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"link:", ex);
                productBuilder.link(null);
            }
        }
    }

    public static void fillWineBrand(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineBrand")) {
            try {
                productBuilder.brand(jsonObject.getString("wineBrand"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"brand:", ex);
                productBuilder.brand(null);
            }
        }
    }

    public static void fillWineCountry(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineCountry")) {
            try {
                productBuilder.country(jsonObject.getString("wineCountry"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"country:", ex);
                productBuilder.country(null);
            }
        }
    }

    public static void fillWineAroma(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineAroma")) {
            try {
                productBuilder.flavor(jsonObject.getString("wineAroma"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"flavor:", ex);
                productBuilder.flavor(null);
            }
        }
    }

    public static void fillWineSugarContent(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineSugarContent")) {
            try {
                productBuilder.sugar(jsonObject.getString("wineSugarContent"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"sugar:", ex);
                productBuilder.sugar(null);
            }
        }
    }

    public static void fillWineColour(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineColour")) {
            try {
                productBuilder.color(jsonObject.getString("wineColour"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"color:", ex);
                productBuilder.color(null);
            }
        }
    }

    public static void fillWineGastronomy(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineGastronomy")) {
            try {
                productBuilder.gastronomy(jsonObject.getString("wineGastronomy"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"gastronomy:", ex);
                productBuilder.gastronomy(null);
            }
        }
    }

    public static void fillWineStrength(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineStrength")) {
            try {
                productBuilder.strength(jsonObject.getFloat("wineStrength"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"strength:", ex);
                productBuilder.strength(null);
            }
        }
    }

    public static void fillWineSparkling(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineSparkling")){
            try {
                productBuilder.sparkling(true);
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"sparkling:", ex);
                productBuilder.sparkling(null);
            }
        }
    }

    public static void fillWineTaste(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineTaste")) {
            try {
                productBuilder.taste(jsonObject.getString("wineTaste"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"taste:", ex);
                productBuilder.taste(null);
            }
        }
    }

    public static void fillWineTitle(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineTitle")) {
            try {
                productBuilder.wineTitle(jsonObject.getString("wineTitle"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"wine name:", ex);
                productBuilder.wineTitle(null);
            }
        }
    }

    public static void fillWinePackagingType(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("winePackagingType")) {
            try {
                productBuilder.manufacturer(jsonObject.getString("winePackagingType"));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"manufacturer:", ex);
                productBuilder.manufacturer(null);
            }
        }
    }

    public static void fillWineGrapeSort(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineGrapeSort")) {
            try {
                productBuilder.grapeSort(Arrays.asList(jsonObject.getString("wineGrapeSort").split(", ")));
            } catch (Exception ex){
                eventLogger.warn(W_SET_PROPERTY_EVENT,"grape sort:", ex);
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
                eventLogger.warn(W_SET_PROPERTY_EVENT,"capacity", ex);
                productBuilder.capacity(null);
            }
        }
    }
}
