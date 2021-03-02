package com.wine.to.up.lenta.service.configuration;

import com.wine.to.up.lenta.service.components.LentaServiceMetricsCollector;
import com.wine.to.up.lenta.service.parser.impl.LentaWineParserServiceImpl;
import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration for connecting to lenta
 */
@Configuration
@PropertySource("classpath:lenta-site.properties")
public class ParserConfiguration {

    /** URL of lenta*/
    @Value("${site.base.url}")
    private String url;

    /** URL of lenta API */
    @Value("${site.rest.url}")
    private String apiUrl;

    /** Body for API connection */
    @Value("${site.rest.body}")
    private String apiBody;

    /** Bean for ParserReqService call */
    @Bean
    public ParserReqServiceImpl parserReqService(LentaServiceMetricsCollector metricsCollector){
        return new ParserReqServiceImpl(url, apiUrl, apiBody, metricsCollector);
    }

    /** Bean for LentaWineParserServiceImpl call */
    @Bean
    public LentaWineParserServiceImpl lentaWineParserService(){
        return new LentaWineParserServiceImpl();
    }
}
