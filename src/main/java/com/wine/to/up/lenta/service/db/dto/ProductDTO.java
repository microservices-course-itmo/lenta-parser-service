package com.wine.to.up.lenta.service.db.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Column;

import java.util.List;

/**
 * This class describes the attributes of the wine. This class instances sent to kafka
 */
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "wines")
public class ProductDTO {

    /**
     * Usually price of wine
     */
    @Column(name = "old_price")
    private Float oldPrice;

    /**
     * Price of wine with discount
     */
    @Column(name = "new_price")
    private Float newPrice;

    /**
     * Link of wine page on Lenta website
     */
    @Column(name = "link")
    private String link;

    /**
     * Image of wine from Lenta website
     */
    @Column(name = "image")
    private String image;

    /**
     * Manufacturer of wine
     */
    @Column(name = "manufacturer")
    private String manufacturer;

    /**
     * Brand of wine
     */
    @Column(name = "brand")
    private String brand;

    /**
     * Country of wine
     */
    @Column(name = "country")
    private String country;

    /**
     * Capacity of wine
     */
    @Column(name = "capacity")
    private Float capacity;

    /**
     * Strength of wine
     */
    @Column(name = "strength")
    private Float strength;
  
    /**
     * Color of wine
     */
    @Column(name = "color")
    private String color;

    /**
     * Type of wine (dry, medium)
     */
    @Column(name = "sugar")
    private String sugar;

    /**
     * Grape sort of wine
     */
    @Column(name = "grape_sort")
    private List<String> grapeSort;
  
    /**
     * Gastronomy of wine
     */
    @Column(name = "gastronomy")
    private String gastronomy;

    /**
     * Taste of wine
     */
    @Column(name = "taste")
    private String taste;

    /**
     * Flavor of wine
     */
    @Column(name = "flavor")
    private String flavor;

    /**
     * Rating of wine on Lenta website
     */
    @Column(name = "rating")
    private Float rating;

    /**
     * Sparkling of wine
     */
    @Column(name = "sparkling")
    private Boolean sparkling;

    /**
     * Title of wine
     */
    @Column(name = "wine_title")
    private String wineTitle;
}
