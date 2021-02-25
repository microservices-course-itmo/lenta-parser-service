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

    DRY(ParserApi.Wine.Sugar.DRY,"Сухое"),

    MEDIUM_DRY(ParserApi.Wine.Sugar.MEDIUM_DRY, "Полусухое"),

    MEDIUM(ParserApi.Wine.Sugar.MEDIUM, "Полусладкое"),

    SWEET(ParserApi.Wine.Sugar.SWEET, "Сладкое"),

    LIQUER(ParserApi.Wine.Sugar.SWEET, "Ликерное"),

    BRUT(ParserApi.Wine.Sugar.DRY, "Брют"),

    EXTRA_BRUT(ParserApi.Wine.Sugar.DRY, "Экстра брют");

    private final ParserApi.Wine.Sugar productSugar;

    private final String sugarWine;

    private static final Map<String, Sugar> SUGAR_MAP = Arrays.stream(
            Sugar.values()).collect(Collectors.toMap(Sugar::getSugarWine, Function.identity())
    );

    public static ParserApi.Wine.Sugar resolve(String sugar) {
        return SUGAR_MAP.getOrDefault(sugar, getDefault()).productSugar;
    }

    private static Sugar getDefault(){
        return Sugar.DRY;
    }
}