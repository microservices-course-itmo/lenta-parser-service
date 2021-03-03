package com.wine.to.up.lenta.service.db.constans;

import com.wine.to.up.parser.common.api.schema.ParserApi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** class for standardization of color attributes */
@Getter
@AllArgsConstructor
@Log4j2
public enum Color {

    /** template of red attribute */
    RED(ParserApi.Wine.Color.RED,"Красный"),

    /** template of rose attribute */
    ROSE(ParserApi.Wine.Color.ROSE, "Розовый"),

    /** template of white attribute */
    WHITE(ParserApi.Wine.Color.WHITE, "Белый"),

    /** template of orange attribute */
    ORANGE(ParserApi.Wine.Color.ORANGE, "Оранжевый");


    private final ParserApi.Wine.Color productColor;

    private final String colorWine;

    private static final Map<String, Color> COLOR_MAP = Arrays.stream(
            Color.values()).collect(Collectors.toMap(Color::getColorWine, Function.identity())
    );

    public static ParserApi.Wine.Color resolve(String color) {
        return COLOR_MAP.getOrDefault(color, getDefault()).productColor;
    }

    private static Color getDefault(){
        return Color.RED;
    }
}