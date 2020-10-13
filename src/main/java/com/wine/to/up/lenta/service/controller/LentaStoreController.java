package com.wine.to.up.lenta.service.controller;

import com.wine.to.up.lenta.service.parser.ParserReqService;
import com.wine.to.up.lenta.service.parser.ParserRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lenta")
@Validated
@Slf4j
public class LentaStoreController {

    @Autowired
    private ParserReqService parserReqService;

    @GetMapping("/parser")
    public String getParserResult(){
        return parserReqService.getJsonList().toString();
    }
}
