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

@RestController
@RequestMapping("/csv")
@Validated
@Slf4j
@AllArgsConstructor
public class CsvController {

    private final ParserReqServiceImpl parserReqServiceImpl;
    private final LentaServiceMetricsCollector metricsCollector;

    @GetMapping(produces = "text/csv")
    public ResponseEntity generateCsv() {
        long startTime = new Date().getTime();
        String csv = CDL.toString(parserReqServiceImpl.getJson().getWines());
        metricsCollector.parseSiteCsv(new Date().getTime() - startTime);
        return new ResponseEntity<>(csv, HttpStatus.OK);

    }

}
