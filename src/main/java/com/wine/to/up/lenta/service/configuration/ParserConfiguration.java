package com.wine.to.up.lenta.service.configuration;

import com.wine.to.up.lenta.service.parser.impl.LentaWineParserServiceImpl;
import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.http.HttpResponse;

@Configuration
@PropertySource("classpath:lenta-site.properties")
public class ParserConfiguration {

    @Value("${site.base.url}")
    private String url;

    @Value("${site.rest.url}")
    private String apiUrl;

    @Value("${site.rest.body}")
    private String apiBody;

    private HttpResponse<?> response;

    @Bean
    public ParserReqServiceImpl parserReqService(){
        return new ParserReqServiceImpl(url, apiUrl, apiBody, response);
    }

    @Bean
    public LentaWineParserServiceImpl lentaWineParserService(){
        return new LentaWineParserServiceImpl();
    }

}
