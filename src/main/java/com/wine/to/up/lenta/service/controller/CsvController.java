package com.wine.to.up.lenta.service.controller;

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

@RestController
@RequestMapping("/csv")
@Validated
@Slf4j
@AllArgsConstructor
public class CsvController {

    private final ParserReqServiceImpl parserReqServiceImpl;

    @GetMapping(produces = "text/csv")
    public ResponseEntity generateCsv() {
        String csv = CDL.toString(parserReqServiceImpl.getJson().getWines());
        return new ResponseEntity<>(csv, HttpStatus.OK);
    }

}
