package com.wine.to.up.lenta.service.configuration;

import com.wine.to.up.lenta.service.parser.impl.LentaWineParserServiceImpl;
import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
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

    @Value("${site.rest.url}")
    private String apiUrl;

    @Bean
    public ParserReqServiceImpl parserReqService(){
        return new ParserReqServiceImpl(url, userAgent, apiUrl);
    }

    @Bean
    public LentaWineParserServiceImpl lentaWineParserService(){
        return new LentaWineParserServiceImpl();
    }

}
