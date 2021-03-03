package com.wine.to.up.lenta.service.db.constans;

import com.wine.to.up.parser.common.api.schema.ParserApi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Log4j2
public enum Sugar {

    /**
     * Template of dry element
     */
    DRY(ParserApi.Wine.Sugar.DRY,"Сухое"),

    /**
     * Template of medium-dry element
     */
    MEDIUM_DRY(ParserApi.Wine.Sugar.MEDIUM_DRY, "Полусухое"),

    /**
     * Template of medium element
     */
    MEDIUM(ParserApi.Wine.Sugar.MEDIUM, "Полусладкое"),

    /**
     * Template of sweet element
     */
    SWEET(ParserApi.Wine.Sugar.SWEET, "Сладкое"),

    /**
     * Template of liquer element
     */
    LIQUER(ParserApi.Wine.Sugar.SWEET, "Ликерное"),

    /**
     * Template of brut element
     */
    BRUT(ParserApi.Wine.Sugar.DRY, "Брют"),

    /**
     * Template of extra-brut element
     */
    EXTRA_BRUT(ParserApi.Wine.Sugar.DRY, "Экстра брют");

    /**
     * Attribute of this enum
     */
    private final ParserApi.Wine.Sugar productSugar;

    /**
     * Attribute of this enum
     */
    private final String sugarWine;

    /**
     * Method that matches element of enum and title of sugar
     */
    private static final Map<String, Sugar> SUGAR_MAP = Arrays.stream(
            Sugar.values()).collect(Collectors.toMap(Sugar::getSugarWine, Function.identity())
    );

    /**
     * Method that return value of this enum
     *
     * @param sugar - value that need return
     *
     * @return color from signature or default color
     */
    public static ParserApi.Wine.Sugar resolve(String sugar) {
        return SUGAR_MAP.getOrDefault(sugar, getDefault()).productSugar;
    }

    /**
     * Method that put default value of this enum
     *
     * @return dry element of this enum
     */
    private static Sugar getDefault(){
        return Sugar.DRY;
    }
}