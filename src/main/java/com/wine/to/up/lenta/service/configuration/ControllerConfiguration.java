package com.wine.to.up.lenta.service.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ControllerConfiguration {

    /**
     * Set Timeout for HTTP requests
     * @return
     */
    @Bean
    public ClientHttpRequestFactory getClientHttpRequestFactory() {
        int timeout = 1200000; // here is the timeout property set for rest template
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(timeout);
        return clientHttpRequestFactory;
    }

    /**
     * RestTemplate to call REST endpoints
     * @param clientHttpRequestFactory
     * @return
     */
    @Bean
    public RestTemplate getRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new RestTemplate(clientHttpRequestFactory);
    }
}
