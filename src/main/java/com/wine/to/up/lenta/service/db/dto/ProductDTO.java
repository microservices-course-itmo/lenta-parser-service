package com.wine.to.up.lenta.service.db.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ProductDTO {

    private String name;
    private String brand;
    private String country;
    private String color;
    private String sugar;
    private float price;
    private float oldPrice;
    private float capacity;
    private float strength;
    private String image;
    private int discount;
    private String manufacturer;
    private String region;
    private String link;
    private String grapeSort;
    private int year;
    private String description;
    private String gastronomy;
    private String taste;
    private String flavor;
    private float rating;
}
