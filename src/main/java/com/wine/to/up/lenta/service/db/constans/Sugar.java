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

    DRY(0, UpdateProducts.Product.Sugar.DRY,"сухое"),

    MEDIUM_DRY(1, UpdateProducts.Product.Sugar.MEDIUM_DRY, "полусухое"),

    MEDIUM(2, UpdateProducts.Product.Sugar.MEDIUM, "полусладкое"),

    SWEET(3, UpdateProducts.Product.Sugar.SWEET, "сладкое");

    private final int id;

    private final UpdateProducts.Product.Sugar productSugar;

    private final String sugar;

    private static final Map<String, Sugar> R = Arrays.stream(
            Sugar.values()).collect(Collectors.toMap(Sugar::getSugar, Function.identity())
    );

    public static UpdateProducts.Product.Sugar resolve(String sugar) {
        return R.getOrDefault(sugar, Sugar.DRY).productSugar;
    }
}