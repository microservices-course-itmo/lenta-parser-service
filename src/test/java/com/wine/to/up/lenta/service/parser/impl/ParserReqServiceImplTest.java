package com.wine.to.up.lenta.service.parser.impl;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserReqServiceImplTest {

    public ParserReqServiceImpl parserReqService = new ParserReqServiceImpl("https://lenta.com", "https://lenta.com/api/v1/skus/list", "{\"nodeCode\":\"ca36224e8c82be1181aad8835310555bf\",\"filters\":[],\"tag\":\"\",\"typeSearch\":1,\"sortingType\":\"ByPriority\",\"offset\":0,\"limit\":\"20\",\"updateFilters\":true}");

    @Test
    public void parserRspTest(){
        parserReqService.getJsonList();
    }

}