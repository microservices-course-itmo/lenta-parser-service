package com.wine.to.up.lenta.service.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.wine.to.up.lenta.service.components.LentaServiceMetricsCollector;
import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import com.wine.to.up.lenta.service.parser.impl.ParserRspImpl;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CsvControllerUnitTest {

    private CsvController controllerMock;

    @Before
    public void setUp() {
        ParserReqServiceImpl parserReqService = mock(ParserReqServiceImpl.class);
        when(parserReqService.getJson()).thenReturn(getParserRsp());
        LentaServiceMetricsCollector lentaServiceMetricsCollector = mock(LentaServiceMetricsCollector.class);
        controllerMock = new CsvController(parserReqService, lentaServiceMetricsCollector);
    }

    private ParserRspImpl getParserRsp(){
        JSONObject jsonObject = new JSONObject();
        ParserRspImpl parserRsp = new ParserRspImpl();
        parserRsp.add(jsonObject);
        parserRsp.add(jsonObject);
        parserRsp.add(jsonObject);
        return parserRsp;
    }

    @Test
    public void csvTest(){
        ResponseEntity parserRsp = controllerMock.generateCsv();
        Assert.assertEquals(parserRsp.getStatusCode(), HttpStatus.OK);
    }

}