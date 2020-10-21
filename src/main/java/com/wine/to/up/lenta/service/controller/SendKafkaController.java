package com.wine.to.up.lenta.service.controller;

import com.wine.to.up.commonlib.messaging.KafkaMessageSender;
import com.wine.to.up.lenta.service.db.constans.Color;
import com.wine.to.up.lenta.service.db.constans.Sugar;
import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import com.wine.to.up.lenta.service.parser.impl.LentaWineParserServiceImpl;
import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import com.wine.to.up.parser.common.api.schema.ParserApi;
import com.wine.to.up.parser.common.api.schema.UpdateProducts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lenta")
@Validated
@Slf4j
public class SendKafkaController {

    @Autowired
    private  ParserReqServiceImpl requestsService;
    private  LentaWineParserServiceImpl parseService;
    private  KafkaMessageSender<ParserApi.WineParsedEvent> kafkaSendMessageService;

    @GetMapping("/kafka")
    public void sendKafkaMessage() {
        log.info("Endpoint sendKafkaMessage is starting");
        log.info("Job started");

        List<ParserApi.Wine> wines = parseService
                .parseWineList(requestsService.getJsonList())
                .stream()
                .map(this::getProtobufProduct)
                .collect(Collectors.toList());

        ParserApi.WineParsedEvent message = ParserApi.WineParsedEvent.newBuilder()
                .setShopLink("https://www.lenta.com")
                .addAllWines(wines)
                .build();

        log.info("Parsed: {} wines", wines.size());

        kafkaSendMessageService.sendMessage(message);

        log.info("Send message to Kafka");
    }

    private ParserApi.Wine getProtobufProduct(ProductDTO wine) {
        return ParserApi.Wine.newBuilder()
                .setName(wine.getName())
                .setBrand(wine.getBrand())
                .setCountry(wine.getCountry())
                .setCapacity(wine.getCapacity())
                .setStrength(wine.getStrength())
                .setColor(convertColor(wine.getColor()))
                .setSugar(convertSugar(wine.getSugar()))
                .setOldPrice(wine.getOldPrice())
                .setImage(wine.getImage())
                .setManufacturer(wine.getManufacturer())
                .setLink(wine.getLink())
                .setYear(wine.getYear())
                .setDescription(wine.getDescription())
                .setGastronomy(wine.getGastronomy())
                .setTaste(wine.getTaste())
                .setFlavor(wine.getFlavor())
                .setRating(wine.getRating())
                .build();
    }

    private ParserApi.Wine.Sugar convertSugar(String value) {
        return Sugar.resolve(value);
    }

    private ParserApi.Wine.Color convertColor(String value) {
        return Color.resolve(value);
    }

}
