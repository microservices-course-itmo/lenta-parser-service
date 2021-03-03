package com.wine.to.up.lenta.service.controller;

import com.wine.to.up.lenta.service.components.LentaServiceMetricsCollector;
import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.CDL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Csv controller
 */
@RestController
@RequestMapping("/csv")
@Validated
@Slf4j
@AllArgsConstructor
public class CsvController {

    /**
     * ParserReqServiseImpl
     */
    private final ParserReqServiceImpl parserReqServiceImpl;

    /**
     * MetricsCollector
     */
    private final LentaServiceMetricsCollector metricsCollector;

    /**
     * Controller that send csv
     *
     * @return http request with csv
     */
    @GetMapping(produces = "text/csv")
    public ResponseEntity generateCsv() {
        long startTime = new Date().getTime();
        String csv = CDL.toString(parserReqServiceImpl.getJson(60).getWines());
        metricsCollector.parseSiteCsv(new Date().getTime() - startTime);
        return new ResponseEntity<>(csv, HttpStatus.OK);

    }

}
