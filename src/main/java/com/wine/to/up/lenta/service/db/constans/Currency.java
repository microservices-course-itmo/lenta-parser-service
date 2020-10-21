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

    UNKNOWN("UNKNOWN"),

    RUB("₽");

    private final String code;

    private static final Map<String, Currency> CURRENCY_MAP = Arrays.stream(
            Currency.values()).collect(Collectors.toMap(Currency::getCode, Function.identity())
    );

    public static Currency resolve(String code) {
        return CURRENCY_MAP.getOrDefault(code, Currency.UNKNOWN);
    }
}
