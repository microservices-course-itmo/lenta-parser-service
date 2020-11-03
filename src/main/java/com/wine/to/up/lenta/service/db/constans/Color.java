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
public enum Color {

    RED(ParserApi.Wine.Color.RED,"Красный"),

    ROSE(ParserApi.Wine.Color.ROSE, "Розовый"),

    WHITE(ParserApi.Wine.Color.WHITE, "Белый");

    private final ParserApi.Wine.Color productColor;

    private final String color;

    private static final Map<String, Color> COLOR_MAP = Arrays.stream(
            Color.values()).collect(Collectors.toMap(Color::getColor, Function.identity())
    );

    public static ParserApi.Wine.Color resolve(String color) {
        return COLOR_MAP.getOrDefault(color, Color.RED).productColor;
    }
}