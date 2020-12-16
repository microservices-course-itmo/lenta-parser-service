package com.wine.to.up.lenta.service.controller;

import com.wine.to.up.lenta.service.components.LentaServiceMetricsCollector;
import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import com.wine.to.up.lenta.service.parser.impl.ParserRspImpl;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LentaStoreControllerUnitTest {

    private LentaStoreController lentaStoreControllerMock;

    @Before
    public void setUp() {
        ParserReqServiceImpl parserReqService = mock(ParserReqServiceImpl.class);
        when(parserReqService.getJson()).thenReturn(getParserRsp());
        LentaServiceMetricsCollector lentaServiceMetricsCollector = mock(LentaServiceMetricsCollector.class);
        doNothing().when(lentaServiceMetricsCollector).parseSiteJson(isA(Long.class));
        lentaStoreControllerMock = new LentaStoreController(parserReqService, lentaServiceMetricsCollector);
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
    public void jsonTest(){
        ResponseEntity parserRsp = lentaStoreControllerMock.getParserResult();
        Assert.assertEquals(parserRsp.getStatusCode(), HttpStatus.OK);
    }
}
