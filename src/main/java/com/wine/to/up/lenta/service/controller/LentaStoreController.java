package com.wine.to.up.lenta.service.controller;

import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.CDL;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/lenta")
@Validated
@Slf4j
@AllArgsConstructor
public class LentaStoreController {

    private ParserReqServiceImpl parserReqServiceImpl;

    @GetMapping(name = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getParserResult() {
        if (parserReqServiceImpl.getJson() == null) {
            return new ResponseEntity<>("Parser return nothing, check internet connection or check lenta api request", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(parserReqServiceImpl.getJson().getWines().toList(), HttpStatus.OK);
        }
    }
}
