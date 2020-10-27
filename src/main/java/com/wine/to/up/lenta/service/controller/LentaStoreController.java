package com.wine.to.up.lenta.service.controller;

import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lenta")
@Validated
@Slf4j
public class LentaStoreController {

    @Autowired
    private ParserReqServiceImpl parserReqServiceImpl;

    @GetMapping(name = "/parser", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getParserResult() {
        return new ResponseEntity<>(parserReqServiceImpl.getJsonList().getWineList().toList(), HttpStatus.OK);
    }
}
