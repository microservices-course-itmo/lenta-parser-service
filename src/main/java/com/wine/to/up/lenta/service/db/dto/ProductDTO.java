package com.wine.to.up.lenta.service.db.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "wines")
public class ProductDTO {

    @Column(name = "old_price")
    private Float oldPrice;

    @Column(name = "new_price")
    private Float newPrice;

    @Column(name = "link")
    private String link;

    @Column(name = "image")
    private String image;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "brand")
    private String brand;

    @Column(name = "country")
    private String country;

    @Column(name = "capacity")
    private Float capacity;

    @Column(name = "strength")
    private Float strength;

    @Column(name = "color")
    private String color;

    @Column(name = "sugar")
    private String sugar;

    @Column(name = "grape_sort")
    private List<String> grapeSort;

    @Column(name = "gastronomy")
    private String gastronomy;

    @Column(name = "taste")
    private String taste;

    @Column(name = "flavor")
    private String flavor;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "sparkling")
    private Boolean sparkling;

    @Column(name = "wine_title")
    private String wineTitle;
}
