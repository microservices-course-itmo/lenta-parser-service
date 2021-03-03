package com.wine.to.up.lenta.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring class of starting
 */
@SpringBootApplication
@ComponentScan("com.wine.to.up")
@EnableSwagger2
public class ServiceApplication {

    /**
     * Starting of spring application
     */
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
