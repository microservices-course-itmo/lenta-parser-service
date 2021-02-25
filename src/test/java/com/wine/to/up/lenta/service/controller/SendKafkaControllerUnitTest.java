//package com.wine.to.up.lenta.service.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.wine.to.up.lenta.service.components.LentaServiceMetricsCollector;
//import com.wine.to.up.lenta.service.cron.ExportProductDtoList;
//import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
//import com.wine.to.up.lenta.service.parser.impl.ParserRspImpl;
//import org.json.JSONObject;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//@RunWith(MockitoJUnitRunner.class)
//public class SendKafkaControllerUnitTest {
//
//    private SendKafkaController controllerMock;
//
//    @Before
//    public void setUp() {
//        ExportProductDtoList exportProductDtoList = mock(ExportProductDtoList.class);
//        doNothing().when(exportProductDtoList).runCronTask();
//        controllerMock = new SendKafkaController(exportProductDtoList);
//        Assert.assertNotNull(exportProductDtoList);
//    }
//
//    @Test
//    public void csvTest(){
//        ResponseEntity parserRsp = controllerMock.sendKafkaMessage();
//        Assert.assertEquals(parserRsp.getStatusCode(), HttpStatus.OK);
//    }
//
//
//
//}