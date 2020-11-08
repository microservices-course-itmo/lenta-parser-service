package com.wine.to.up.lenta.service.cron;

import com.wine.to.up.commonlib.messaging.KafkaMessageSender;
import com.wine.to.up.lenta.service.db.constans.Color;
import com.wine.to.up.lenta.service.db.constans.Sugar;
import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import com.wine.to.up.lenta.service.parser.impl.LentaWineParserServiceImpl;
import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import com.wine.to.up.parser.common.api.schema.ParserApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@PropertySource("classpath:lenta-site.properties")
public class ExportProductDtoList {

    @Value("${string.for.cron}")
    private String SHOP;

    private final ParserReqServiceImpl requestsService;
    private final LentaWineParserServiceImpl parseService;
    private final KafkaMessageSender<ParserApi.WineParsedEvent> kafkaSendMessageService;

    public ExportProductDtoList(ParserReqServiceImpl requestsService, LentaWineParserServiceImpl parseService,
                                KafkaMessageSender<ParserApi.WineParsedEvent> kafkaSendMessageService) {
        this.requestsService = Objects.requireNonNull(requestsService, "Can't get requestsService");
        this.parseService = Objects.requireNonNull(parseService, "Can't get parseService");
        this.kafkaSendMessageService = Objects.requireNonNull(kafkaSendMessageService, "Can't get kafkaSendMessageService");
    }

    @Scheduled(cron = "${cron.expression}")
    public void runCronTask() {

        log.info("Job started");

        List<ParserApi.Wine> wines = parseService
                .parseWineList(requestsService.getJsonList())
                .stream()
                .map(this::getProtobufProduct)
                .collect(Collectors.toList());

        ParserApi.WineParsedEvent message = ParserApi.WineParsedEvent.newBuilder()
                .setShopLink(SHOP)
                .addAllWines(wines)
                .build();

        log.info("Parsed: {} wines", wines.size());

        kafkaSendMessageService.sendMessage(message);

        log.info("Send message to Kafka");
    }

    private ParserApi.Wine getProtobufProduct(ProductDTO wine) {
        ParserApi.Wine.Sugar sugar = convertSugar(wine.getSugar());
        ParserApi.Wine.Color color = convertColor(wine.getColor());

        var builder = ParserApi.Wine.newBuilder();

        if (wine.getBrand() != null) {
            builder.setBrand(wine.getBrand());
        }
        if (wine.getCountry() != null) {
            builder.setCountry(wine.getCountry());
        }
        if (wine.getCapacity() != null) {
            builder.setCapacity(wine.getCapacity());
        }
        if (wine.getStrength() != null) {
            builder.setStrength(wine.getStrength());
        }
        if (color != null) {
            builder.setColor(color);
        }
        if (sugar != null) {
            builder.setSugar(sugar);
        }
        if (wine.getOldPrice() != null) {
            builder.setOldPrice(wine.getOldPrice());
        }
        if (wine.getImage() != null) {
            builder.setImage(wine.getImage());
        }
        if (wine.getManufacturer() != null) {
            builder.setManufacturer(wine.getManufacturer());
        }
        if (wine.getLink() != null) {
            builder.setLink(wine.getLink());
        }
        if (wine.getGrapeSort() != null) {
            builder.addAllGrapeSort(wine.getGrapeSort());
        }
        if (wine.getGastronomy() != null) {
            builder.setGastronomy(wine.getGastronomy());
        }
        if (wine.getTaste() != null) {
            builder.setTaste(wine.getTaste());
        }
        if (wine.getFlavor() != null) {
            builder.setFlavor(wine.getFlavor());
        }
        if (wine.getRating() != null) {
            builder.setRating(wine.getRating());
        }
        return builder.build();
    }

    private ParserApi.Wine.Sugar convertSugar(String value) {
        return Sugar.resolve(value);
    }

    private ParserApi.Wine.Color convertColor(String value) {
        return Color.resolve(value);
    }
}
