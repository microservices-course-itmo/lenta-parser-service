package com.wine.to.up.lenta.service.db.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * This class describes the attributes of the wine. This class instances sent to kafka
 */
@Getter
@Setter
@Builder
@ToString
public class ProductDTO {

    /**
     * Usually price of wine
     */
    private Float oldPrice;

    /**
     * Price of wine with discount
     */
    private Float newPrice;

    /**
     * Link of wine page on Lenta website
     */
    private String link;

    /**
     * Image of wine from Lenta website
     */
    private String image;

    /**
     * Manufacturer of wine
     */
    private String manufacturer;

    /**
     * Brand of wine
     */
    private String brand;

    /**
     * Country of wine
     */
    private String country;

    /**
     * Capacity of wine
     */
    private Float capacity;

    /**
     * Strength of wine
     */
    private Float strength;

    /**
     * Color of wine
     */
    private String color;

    /**
     * Type of wine (dry, semi-sweet)
     */
    private String sugar;

    /**
     * Grape sort of wine
     */
    private List<String> grapeSort;

    /**
     * Gastronomy of wine
     */
    private String gastronomy;

    /**
     * Taste of wine
     */
    private String taste;

    /**
     * Flavor of wine
     */
    private String flavor;

    /**
     * Rating of wine on Lenta website
     */
    private Float rating;

    /**
     * Sparkling of wine
     */
    private Boolean sparkling;

    /**
     * Title of wine
     */
    private String wineTitle;
}
