package com.wine.to.up.lenta.service.controller;

import com.wine.to.up.lenta.service.components.LentaServiceMetricsCollector;
import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import com.wine.to.up.lenta.service.parser.impl.ParserRspImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * This Lenta controller class that start parsing
 */
@RestController
@RequestMapping("/lenta")
@Validated
@Slf4j
@AllArgsConstructor
public class LentaStoreController {

    private final ParserReqServiceImpl parserReqServiceImpl;
    private final LentaServiceMetricsCollector metricsCollector;

    /**
     * This controller starting parsing and return parsed wines
     *
     * @return List of parsed wines
     */
    @GetMapping(name = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getParserResult() {
        long startTime = System.nanoTime();
        Integer batchSize = new Date().getHours() * 60 + new Date().getMinutes();
        ParserRspImpl parserRsp = parserReqServiceImpl.getJson(batchSize);
        metricsCollector.parseSiteJson(System.nanoTime() - startTime);
        if (parserRsp == null) {
            return new ResponseEntity<>("Parser return nothing, check internet connection or check lenta api request", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(parserRsp.getWines().toList(), HttpStatus.OK);
        }
    }
}
