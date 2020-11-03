package com.wine.to.up.lenta.service.db.constans;

import com.wine.to.up.parser.common.api.schema.ParserApi;
import com.wine.to.up.parser.common.api.schema.UpdateProducts;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Sugar {

    DRY(ParserApi.Wine.Sugar.DRY,"Сухое"),

    MEDIUM_DRY(ParserApi.Wine.Sugar.MEDIUM_DRY, "Полусухое"),

    MEDIUM(ParserApi.Wine.Sugar.MEDIUM, "Полусладкое"),

    SWEET(ParserApi.Wine.Sugar.SWEET, "Сладкое");

    private final ParserApi.Wine.Sugar productSugar;

    private final String sugar;

    private static final Map<String, Sugar> SUGAR_MAP = Arrays.stream(
            Sugar.values()).collect(Collectors.toMap(Sugar::getSugar, Function.identity())
    );

    public static ParserApi.Wine.Sugar resolve(String sugar) {
        return SUGAR_MAP.getOrDefault(sugar, Sugar.DRY).productSugar;
    }
}