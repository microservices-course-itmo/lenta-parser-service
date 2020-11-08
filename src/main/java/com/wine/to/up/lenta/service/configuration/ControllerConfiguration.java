package com.wine.to.up.lenta.service.configuration;

import com.wine.to.up.lenta.service.controller.LentaStoreController;
import com.wine.to.up.lenta.service.controller.SendKafkaController;
import com.wine.to.up.lenta.service.cron.ExportProductDtoList;
import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import org.springframework.context.annotation.Bean;

public class ControllerConfiguration {

    @Bean
    public LentaStoreController lentaStoreController(ParserReqServiceImpl parserReqServiceImpl) {
        return new LentaStoreController(parserReqServiceImpl);
    }

    @Bean
    public SendKafkaController sendKafkaController(ExportProductDtoList exportProductDtoList){
        return new SendKafkaController(exportProductDtoList);
    }
}
