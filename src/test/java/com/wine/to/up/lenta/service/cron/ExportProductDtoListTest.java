//package com.wine.to.up.lenta.service.cron;
//
//import com.wine.to.up.commonlib.messaging.KafkaMessageSender;
//import com.wine.to.up.lenta.service.components.LentaServiceMetricsCollector;
//import com.wine.to.up.lenta.service.controller.LentaStoreController;
//import com.wine.to.up.lenta.service.controller.SendKafkaController;
//import com.wine.to.up.lenta.service.db.dto.ProductDTO;
//import com.wine.to.up.lenta.service.parser.impl.LentaWineParserServiceImpl;
//import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
//import com.wine.to.up.lenta.service.parser.impl.ParserRspImpl;
//import com.wine.to.up.parser.common.api.schema.ParserApi;
//import org.json.JSONObject;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class ExportProductDtoListTest {
//
//    ExportProductDtoList exportProductDtoList;
//    ParserReqServiceImpl parserReqService;
//    LentaWineParserServiceImpl parserService;
//    LentaServiceMetricsCollector lentaServiceMetricsCollector;
//    KafkaMessageSender<ParserApi.WineParsedEvent> kafkaSendMessageService;
//
//    @Before
//    public void setUp() {
//        parserReqService = mock(ParserReqServiceImpl.class);
//        parserService = mock(LentaWineParserServiceImpl.class);
//        when(parserReqService.getJson()).thenReturn(getParserRsp());
//        kafkaSendMessageService = mock(KafkaMessageSender.class);
//        lentaServiceMetricsCollector = mock(LentaServiceMetricsCollector.class);
//
//        exportProductDtoList = new ExportProductDtoList(parserReqService, parserService, kafkaSendMessageService, lentaServiceMetricsCollector);
//    }
//
//    private ParserRspImpl getParserRsp(){
//        JSONObject jsonObject = new JSONObject();
//        ParserRspImpl parserRsp = new ParserRspImpl();
//        parserRsp.add(jsonObject);
//        parserRsp.add(jsonObject);
//        parserRsp.add(jsonObject);
//        return parserRsp;
//    }
//
//    @Test
//    public void jobTest(){
//        ProductDTO.ProductDTOBuilder productBuilder = ProductDTO.builder();
//        assertNotNull(new ExportProductDtoList(parserReqService, parserService, kafkaSendMessageService, lentaServiceMetricsCollector));
//        assertNotNull(exportProductDtoList.getProtobufProduct(productBuilder.build()));
//    }
//
//}