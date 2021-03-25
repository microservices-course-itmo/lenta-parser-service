package com.wine.to.up.lenta.service.helpers;

import com.wine.to.up.commonlib.annotations.InjectEventLogger;
import com.wine.to.up.commonlib.logging.EventLogger;
import com.wine.to.up.lenta.service.db.constans.Color;
import com.wine.to.up.lenta.service.db.constans.Sugar;
import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import com.wine.to.up.parser.common.api.schema.ParserApi;

import static com.wine.to.up.lenta.service.logging.LentaParserServiceNotableEvents.*;

/**
 * Class helper for ExportProductDtoList.getProtobufProduct
 * checks wine fields on NULL and fills builder`s fields
 */
public class ExportProductDtoListHelper {

    /**
     * Event logger
     */
    @InjectEventLogger
    private static EventLogger eventLogger;


    private ExportProductDtoListHelper() {
        throw new IllegalStateException("Class helper");
    }

    /**
     * Checking for null brand attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillBrand(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getBrand() != null) {
            builder.setBrand(wine.getBrand());
        }
    }

    /**
     * Checking for null sparkling attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillSparkling(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getSparkling() != null ){
            builder.setSparkling(true);
        }
    }

    /**
     * Checking for null country attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillCountry(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getCountry() != null) {
            builder.setCountry(wine.getCountry());
        }
    }

    /**
     * Checking for null capacity attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillCapacity(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getCapacity() != null) {
            builder.setCapacity(wine.getCapacity());
        }
    }

    /**
     * Checking for null strength attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillStrength(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getStrength() != null) {
            builder.setStrength(wine.getStrength());
        }
    }

    private static void fillInStock(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getInStock() != null) {
            builder.setInStock(wine.getInStock());
        }
    }

    private static void fillCity(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getCity() != null) {
            builder.setCity(wine.getCity());
        }
    } 
    

    /**
     * Checking for null color attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillColor(ParserApi.Wine.Builder builder, ProductDTO wine) {
        ParserApi.Wine.Color color = convertColor(wine.getColor());
        if (color != null) {
            builder.setColor(color);
        }
    }

    /**
     * Checking for null sugar attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillSugar(ParserApi.Wine.Builder builder, ProductDTO wine) {
        ParserApi.Wine.Sugar sugar = convertSugar(wine.getSugar());
        if (sugar != null) {
            builder.setSugar(sugar);
        }
    }

    /**
     * Checking for null oldprice attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillOldPrice(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getOldPrice() != null) {
            builder.setOldPrice(wine.getOldPrice());
        }
    }

    /**
     * Checking for null new price attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillNewPrice(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getNewPrice() != null) {
            builder.setNewPrice(wine.getNewPrice());
        }
    }

    /**
     * Checking for null image attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillImage(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getImage() != null) {
            builder.setImage(wine.getImage());
        }
    }

    /**
     * Checking for null manufacturer attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillManufacturer(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getManufacturer() != null) {
            builder.setManufacturer(wine.getManufacturer());
        }
    }

    /**
     * Checking for null link attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillLink(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getLink() != null) {
            builder.setLink(wine.getLink());
        }
    }

    /**
     * Checking for null grapesort attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillGrapeSort(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getGrapeSort() != null) {
            builder.addAllGrapeSort(wine.getGrapeSort());
        }
    }

    /**
     * Checking for null gastronomy attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillGastronomy(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getGastronomy() != null) {
            builder.setGastronomy(wine.getGastronomy());
        }
    }

    /**
     * Checking for null taste attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillTaste(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getTaste() != null) {
            builder.setTaste(wine.getTaste());
        }
    }

    /**
     * Checking for null flavor attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillFlavor(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getFlavor() != null) {
            builder.setFlavor(wine.getFlavor());
        }
    }

    /**
     * Checking for null rating attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillRating(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getRating() != null) {
            builder.setRating(wine.getRating());
        }
    }

    /**
     * Checking for null title attribute
     *
     * @param builder builder of Wine
     * @param  wine Wine with attributes
     */
    private static void fillTitle(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getWineTitle() != null) {
            builder.setName(wine.getWineTitle());
        }
    }

    /**
     * Method of  attribute color convetring
     *
     * @param  value parsed value of color
     * @return standardized value of color
     */

    private static ParserApi.Wine.Color convertColor(String value) {
        return Color.resolve(value);
    }
    /**
     * Method of attribute  sugar convetring
     *
     * @param value parsed value of sugar
     * @return standardized value of color
     */
    private static ParserApi.Wine.Sugar convertSugar(String value) {
        return Sugar.resolve(value);
    }
    
    public static void fillBuilder(ParserApi.Wine.Builder builder, ProductDTO wine){
        fillBrand(builder, wine);
        fillSparkling(builder, wine);
        fillCountry(builder, wine);
        fillCapacity(builder, wine);
        fillStrength(builder, wine);
        fillColor(builder, wine);
        fillSugar(builder, wine);
        fillOldPrice(builder, wine);
        fillImage(builder, wine);
        fillManufacturer(builder, wine);
        fillLink(builder, wine);
        fillGrapeSort(builder, wine);
        fillGastronomy(builder, wine);
        fillTaste(builder, wine);
        fillFlavor(builder, wine);
        fillRating(builder, wine);
        fillTitle(builder, wine);
        fillNewPrice(builder, wine);
        fillCity(builder, wine);
        fillInStock(builder, wine);
    }
}
