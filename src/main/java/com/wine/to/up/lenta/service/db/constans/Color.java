package com.wine.to.up.lenta.service.db.constans;

import com.wine.to.up.parser.common.api.schema.ParserApi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Enum for standardization of color attributes
 */
@Getter
@AllArgsConstructor
@Log4j2
public enum Color {

    /**
     * Template of red element
     */
    RED(ParserApi.Wine.Color.RED,"Красный"),

    /**
     * Template of rose element
     */
    ROSE(ParserApi.Wine.Color.ROSE, "Розовый"),

    /**
     * Template of white element
     */
    WHITE(ParserApi.Wine.Color.WHITE, "Белый"),

    /**
     * Template of orange element
     */
    ORANGE(ParserApi.Wine.Color.ORANGE, "Оранжевый");

    /**
     * Attribute of this enum
     */
    private final ParserApi.Wine.Color productColor;

    /**
     * Attribute of this enum
     */
    private final String colorWine;

    /**
     * Method that matches element of enum and title of color
     */
    private static final Map<String, Color> COLOR_MAP = Arrays.stream(
            Color.values()).collect(Collectors.toMap(Color::getColorWine, Function.identity())
    );

    /**
     * Method that return value of this enum
     *
     * @param color - value that need return
     *
     * @return color from signature or default color
     */
    public static ParserApi.Wine.Color resolve(String color) {
        return COLOR_MAP.getOrDefault(color, getDefault()).productColor;
    }

    /**
     * Method that put default value of this enum
     *
     * @return red element of this enum
     */
    private static Color getDefault(){
        return Color.RED;
    }
}