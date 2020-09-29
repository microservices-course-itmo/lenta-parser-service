package com.wine.to.up.demo.service;

import com.wine.to.up.demo.service.parser.Parser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("com.wine.to.up")
@EnableSwagger2
public class ServiceApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ServiceApplication.class, args);

        Parser parser = new Parser();
        parser.parseTitle();
        parser.parseText();
    }

}
