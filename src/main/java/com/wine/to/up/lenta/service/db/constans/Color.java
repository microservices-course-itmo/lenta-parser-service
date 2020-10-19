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
public enum Color {

    RED(UpdateProducts.Product.Color.RED,"Красное"),

    ROSE(UpdateProducts.Product.Color.ROSE, "Розовое"),

    WHITE(UpdateProducts.Product.Color.WHITE, "Белое"),

    UNRECOGNIZED(UpdateProducts.Product.Color.UNRECOGNIZED, "Unrecognized");

    private final UpdateProducts.Product.Color productColor;

    private final String color;

    private static final Map<String, Color> COLOR_MAP = Arrays.stream(
            Color.values()).collect(Collectors.toMap(Color::getColor, Function.identity())
    );

    public static UpdateProducts.Product.Color resolve(String color) {
        return COLOR_MAP.getOrDefault(color, Color.UNRECOGNIZED).productColor;
    }
}