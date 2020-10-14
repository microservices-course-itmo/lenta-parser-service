package com.wine.to.up.lenta.service.db.constans;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Currency {

    UNKNOWN(0, "UNKNOWN"),

    RUB(1, "₽");

    private final int id;

    private final String code;

    private static final Map<String, Currency> R = Arrays.stream(
            Currency.values()).collect(Collectors.toMap(Currency::getCode, Function.identity())
    );

    public static Currency resolve(String code) {
        return R.getOrDefault(code, Currency.UNKNOWN);
    }
}
