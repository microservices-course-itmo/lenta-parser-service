package com.wine.to.up.demo.service.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table (name = "wines")
public class Wine {

    @Id
    @GeneratedValue
    private Long id;

    @Column (name = "name")
    private String name;

    @Column (name = "type")
    private String type;

    @Column (name = "country")
    private String country;

    @Column (name = "rating")
    private int rating;

    @Column (name = "valume")
    private float volume;

    @Column (name = "normal_price")
    private BigDecimal normalPrice;

    @Column (name = "discount_price")
    private BigDecimal discountPrice;

    @Column (name = "discount_percentage")
    private int discountPercentage;

    @Column (name = "image")
    private String image;
}
