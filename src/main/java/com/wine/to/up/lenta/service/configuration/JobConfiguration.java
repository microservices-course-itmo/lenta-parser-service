package com.wine.to.up.lenta.service.configuration;

import com.wine.to.up.commonlib.messaging.KafkaMessageSender;
import com.wine.to.up.lenta.service.components.LentaServiceMetricsCollector;
import com.wine.to.up.lenta.service.cron.ExportProductDtoList;
import com.wine.to.up.lenta.service.parser.impl.LentaWineParserServiceImpl;
import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import com.wine.to.up.parser.common.api.schema.ParserApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class JobConfiguration {

    @Bean
    public ExportProductDtoList exportProductDtoList(ParserReqServiceImpl requestsService, LentaWineParserServiceImpl parserService,
                                                     KafkaMessageSender<ParserApi.WineParsedEvent> kafkaSendMessageService, LentaServiceMetricsCollector metricsCollector){
        return new ExportProductDtoList(requestsService, parserService, kafkaSendMessageService, metricsCollector);
    }
}
