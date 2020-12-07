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

    @InjectEventLogger
    private static EventLogger eventLogger;


    private ExportProductDtoListHelper() {
        throw new IllegalStateException("Class helper");
    }

    public static void fillBrand(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getBrand() != null) {
            builder.setBrand(wine.getBrand());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillSparkling(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getSparkling() != null ){
            builder.setSparkling(true);
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillCountry(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getCountry() != null) {
            builder.setCountry(wine.getCountry());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillCapacity(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getCapacity() != null) {
            builder.setCapacity(wine.getCapacity());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillStrength(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getStrength() != null) {
            builder.setStrength(wine.getStrength());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillColor(ParserApi.Wine.Builder builder, ProductDTO wine) {
        ParserApi.Wine.Color color = convertColor(wine.getColor());
        if (color != null) {
            builder.setColor(color);
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillSugar(ParserApi.Wine.Builder builder, ProductDTO wine) {
        ParserApi.Wine.Sugar sugar = convertSugar(wine.getSugar());
        if (sugar != null) {
            builder.setSugar(sugar);
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillOldPrice(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getOldPrice() != null) {
            builder.setOldPrice(wine.getOldPrice());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillImage(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getImage() != null) {
            builder.setImage(wine.getImage());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillManufacturer(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getManufacturer() != null) {
            builder.setManufacturer(wine.getManufacturer());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillLink(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getLink() != null) {
            builder.setLink(wine.getLink());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillGrapeSort(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getGrapeSort() != null) {
            builder.addAllGrapeSort(wine.getGrapeSort());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillGastronomy(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getGastronomy() != null) {
            builder.setGastronomy(wine.getGastronomy());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillTaste(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getTaste() != null) {
            builder.setTaste(wine.getTaste());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillFlavor(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getFlavor() != null) {
            builder.setFlavor(wine.getFlavor());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillRating(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getRating() != null) {
            builder.setRating(wine.getRating());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    public static void fillTitle(ParserApi.Wine.Builder builder, ProductDTO wine) {
        if (wine.getWineTitle() != null) {
            builder.setName(wine.getWineTitle());
        } else {
            eventLogger.warn(W_SOME_WARN_EVENT, "Get null wine property");
        }
    }

    private static ParserApi.Wine.Color convertColor(String value) {
        return Color.resolve(value);
    }

    private static ParserApi.Wine.Sugar convertSugar(String value) {
        return Sugar.resolve(value);
    }
}
