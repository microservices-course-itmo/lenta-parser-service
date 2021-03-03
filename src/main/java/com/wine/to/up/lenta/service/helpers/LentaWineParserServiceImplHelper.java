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

    /** wine capacity attribute */
    private static final String WINECAPACITY = "wineCapacity";

    /** event logger */
    @InjectEventLogger
    private static EventLogger eventLogger;

    /** builder */
    private LentaWineParserServiceImplHelper() {
        throw new IllegalStateException("Class helper");
    }

    /**
     *Checking for null OldPrice attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillOldPrice(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineOldPrice")) {
            try {
                productBuilder.oldPrice(jsonObject.getFloat("wineOldPrice"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"old price", getURL(jsonObject));
                productBuilder.oldPrice(null);
            }

        }
    }

    /**
     *Checking for null NewPrice attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillNewPrice(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineNewPrice")) {
            try {
                productBuilder.newPrice(jsonObject.getFloat("wineNewPrice"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT, "new price", getURL(jsonObject));
                productBuilder.newPrice(null);
            }
        }
    }

    /**
     *Checking for null OldPrice attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillImageUrl(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("imageUrl")) {
            try {
                productBuilder.image(jsonObject.getString("imageUrl"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"image", getURL(jsonObject));
                productBuilder.image(null);
            }
        }
    }

    /**
     *Checking for null wine rating attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineRating(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineRating")) {
            try {
                productBuilder.rating(jsonObject.getFloat("wineRating"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"rating", getURL(jsonObject));
                productBuilder.rating(null);
            }
        }
    }

    /**
     *Checking for null wine link attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineLink(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineLink")) {
            try {
                productBuilder.link(String.valueOf(jsonObject.get("wineLink")));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"link", null);
                productBuilder.link(null);
            }
        }
    }

    /**
     *Checking for null wine brand attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineBrand(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineBrand")) {
            try {
                productBuilder.brand(jsonObject.getString("wineBrand"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"brand:", getURL(jsonObject));
                productBuilder.brand(null);
            }
        }
    }

    /**
     *Checking for null wine country attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineCountry(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineCountry")) {
            try {
                productBuilder.country(jsonObject.getString("wineCountry"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"country:", getURL(jsonObject));
                productBuilder.country(null);
            }
        }
    }

    /**
     *Checking for null wine Aroma attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineAroma(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineAroma")) {
            try {
                productBuilder.flavor(jsonObject.getString("wineAroma"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"flavor:", getURL(jsonObject));
                productBuilder.flavor(null);
            }
        }
    }

    /**
     *Checking for null wine sugar content attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineSugarContent(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineSugarContent")) {
            try {
                productBuilder.sugar(jsonObject.getString("wineSugarContent"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"sugar:", getURL(jsonObject));
                productBuilder.sugar(null);
            }
        }
    }

    /**
     *Checking for null wine color attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineColour(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineColour")) {
            try {
                productBuilder.color(jsonObject.getString("wineColour"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"color:", getURL(jsonObject));
                productBuilder.color(null);
            }
        }
    }

    /**
     *Checking for null wine gastronomy attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineGastronomy(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineGastronomy")) {
            try {
                productBuilder.gastronomy(jsonObject.getString("wineGastronomy"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"gastronomy:", getURL(jsonObject));
                productBuilder.gastronomy(null);
            }
        }
    }

    /**
     *Checking for null wine strenght attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineStrength(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineStrength")) {
            try {
                productBuilder.strength(jsonObject.getFloat("wineStrength"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"strength:", getURL(jsonObject));
                productBuilder.strength(null);
            }
        }
    }

    /**
     *Checking for null wine sparkling attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineSparkling(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineSparkling")){
            try {
                if (jsonObject.getBoolean("wineSparkling") == true) {
                    productBuilder.sparkling(true);
                } else {
                    productBuilder.sparkling(null);
                }

            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"sparkling:", getURL(jsonObject));
                productBuilder.sparkling(null);
            }
        }
    }

    /**
     *Checking for null wine taste attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineTaste(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineTaste")) {
            try {
                productBuilder.taste(jsonObject.getString("wineTaste"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"taste:", getURL(jsonObject));
                productBuilder.taste(null);
            }
        }
    }

    /**
     *Checking for null wine title attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineTitle(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineTitle")) {
            try {
                productBuilder.wineTitle(jsonObject.getString("wineTitle"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"wine name:", getURL(jsonObject));
                productBuilder.wineTitle(null);
            }
        }
    }

    /**
     *Checking for null wine packaging type attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWinePackagingType(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("winePackagingType")) {
            try {
                productBuilder.manufacturer(jsonObject.getString("winePackagingType"));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"manufacturer:", getURL(jsonObject));
                productBuilder.manufacturer(null);
            }
        }
    }

    /**
     *Checking for null wine grape sort attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineGrapeSort(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has("wineGrapeSort")) {
            try {
                productBuilder.grapeSort(Arrays.asList(jsonObject.getString("wineGrapeSort").split(", ")));
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"grape sort:", getURL(jsonObject));
                productBuilder.grapeSort(null);
            }
        }
    }

    /**
     *Checking for null wine capacity attribute and Log error
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static void fillWineCapacity(JSONObject jsonObject, ProductDTO.ProductDTOBuilder productBuilder) {
        if (jsonObject.has(WINECAPACITY)) {
            try {
                if (jsonObject.getString(WINECAPACITY).contains(",")) {
                    productBuilder.capacity(Float.parseFloat(jsonObject.getString(WINECAPACITY).replace(",", ".")));
                } else {
                    productBuilder.capacity(jsonObject.getFloat(WINECAPACITY));
                }
            } catch (Exception ex){
                eventLogger.warn(W_WINE_ATTRIBUTE_ABSENT,"capacity", getURL(jsonObject));
                productBuilder.capacity(null);
            }
        }
    }

    /**
     *Checking for null wine URL attribute
     * @param jsonObject parsed wine
     * @param  productBuilder DTO builder*/
    public static String getURL(JSONObject jsonObject){
        if (jsonObject.has("wineLink")) {
            return jsonObject.getString("wineLink");
        } else {
            return null;
        }
    }
}
