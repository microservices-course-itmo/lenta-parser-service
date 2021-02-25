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
public class ProductDTO {

    private Float oldPrice;
    private Float newPrice;
    private String link;
    private String image;
    private String manufacturer;
    private String brand;
    private String country;
    private Float capacity;
    private Float strength;
    private String color;
    private String sugar;
    private List<String> grapeSort;
    private String gastronomy;
    private String taste;
    private String flavor;
    private Float rating;
    private Boolean sparkling;
    private String wineTitle;
}
