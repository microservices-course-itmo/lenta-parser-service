package com.wine.to.up.lenta.service.configuration;

import com.wine.to.up.lenta.service.parser.LentaWineParserService;
import com.wine.to.up.lenta.service.parser.ParserReqService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:lenta-site.properties")
public class ParserConfiguration {

    @Value("${site.base.url}")
    private String url;

    @Value("${site.user.agent}")
    private String userAgent;

    @Bean
    public ParserReqService parserReqService(){
        return new ParserReqService(url, userAgent);
    }

    @Bean
    public LentaWineParserService lentaWineParserService(){
        return new LentaWineParserService();
    }

}
