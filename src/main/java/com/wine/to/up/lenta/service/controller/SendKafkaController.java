package com.wine.to.up.lenta.service.controller;

import com.wine.to.up.lenta.service.cron.ExportProductDtoList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/lenta")
@Validated
@Slf4j
@AllArgsConstructor
public class SendKafkaController {

    private ExportProductDtoList exportProductDtoList;

    @GetMapping("/kafka")
    public void sendKafkaMessage() {
        exportProductDtoList.runCronTask();
    }
}
