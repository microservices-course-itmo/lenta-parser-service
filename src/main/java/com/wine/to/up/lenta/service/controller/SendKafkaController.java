package com.wine.to.up.lenta.service.controller;

import com.wine.to.up.lenta.service.cron.ExportProductDtoList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lenta")
@Validated
@Slf4j
@AllArgsConstructor
public class SendKafkaController {

    private ExportProductDtoList exportProductDtoList;

    @GetMapping("/kafka")
    public ResponseEntity sendKafkaMessage() {
        exportProductDtoList.runCronTask();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
