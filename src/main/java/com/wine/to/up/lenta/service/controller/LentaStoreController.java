package com.wine.to.up.lenta.service.controller;

import com.wine.to.up.lenta.service.parser.ParserReqService;
import com.wine.to.up.lenta.service.parser.ParserRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/lenta")
@Validated
@Slf4j
public class LentaStoreController {

    @Autowired
    private ParserReqService parserReqService;

    @GetMapping("/parser")
    public List<ParserRsp> getParserResult() {
        List<ParserRsp> list = new ArrayList<>();
        list.add(parserReqService.getJsonList());
        return list;
    }
}
