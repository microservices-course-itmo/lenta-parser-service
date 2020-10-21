package com.wine.to.up.lenta.service.controller;

import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import com.wine.to.up.lenta.service.parser.impl.ParserRspImpl;
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
    private ParserReqServiceImpl parserReqServiceImpl;

    @GetMapping("/parser")
    public List<ParserRspImpl> getParserResult() {
        log.info("Endpoint getParserResult is starting");
        List<ParserRspImpl> list = new ArrayList<>();
        list.add(parserReqServiceImpl.getJsonList());
        return list;
    }
}
