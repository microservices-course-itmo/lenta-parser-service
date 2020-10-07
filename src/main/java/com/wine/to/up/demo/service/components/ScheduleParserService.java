package com.wine.to.up.demo.service.components;

import com.wine.to.up.demo.service.services.impl.LentaWineParserImpl;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduleParserService {

    @SneakyThrows
    @Scheduled(cron = "${cron.expression}")
    public void startParse(){
        LentaWineParserImpl.parseTitle();
    }
}
