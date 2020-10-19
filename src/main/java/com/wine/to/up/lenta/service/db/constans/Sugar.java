package com.wine.to.up.lenta.service.db.constans;

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

    DRY(UpdateProducts.Product.Sugar.DRY,"сухое"),

    MEDIUM_DRY(UpdateProducts.Product.Sugar.MEDIUM_DRY, "полусухое"),

    MEDIUM(UpdateProducts.Product.Sugar.MEDIUM, "полусладкое"),

    SWEET(UpdateProducts.Product.Sugar.SWEET, "сладкое");

    private final UpdateProducts.Product.Sugar productSugar;

    private final String sugar;

    private static final Map<String, Sugar> SUGAR_MAP = Arrays.stream(
            Sugar.values()).collect(Collectors.toMap(Sugar::getSugar, Function.identity())
    );

    public static UpdateProducts.Product.Sugar resolve(String sugar) {
        return SUGAR_MAP.getOrDefault(sugar, Sugar.DRY).productSugar;
    }
}