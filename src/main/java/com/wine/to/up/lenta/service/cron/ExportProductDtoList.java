package com.wine.to.up.lenta.service.cron;

import com.wine.to.up.commonlib.messaging.KafkaMessageSender;

import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import com.wine.to.up.lenta.service.helpers.ExportProductDtoListHelper;
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
    private String shopLink;

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
                .parseWineList(requestsService.getJson())
                .stream()
                .map(this::getProtobufProduct)
                .collect(Collectors.toList());

        ParserApi.WineParsedEvent message = ParserApi.WineParsedEvent.newBuilder()
                .setShopLink(shopLink)
                .addAllWines(wines)
                .build();

        log.info("Parsed: {} wines", wines.size());

        kafkaSendMessageService.sendMessage(message);

        log.info("Send message to Kafka");
    }

    private ParserApi.Wine getProtobufProduct(ProductDTO wine) {
        var builder = ParserApi.Wine.newBuilder();

        ExportProductDtoListHelper.fillBrand(builder, wine);
        ExportProductDtoListHelper.fillSparkling(builder, wine);
        ExportProductDtoListHelper.fillCountry(builder, wine);
        ExportProductDtoListHelper.fillCapacity(builder, wine);
        ExportProductDtoListHelper.fillStrength(builder, wine);
        ExportProductDtoListHelper.fillColor(builder, wine);
        ExportProductDtoListHelper.fillSugar(builder, wine);
        ExportProductDtoListHelper.fillOldPrice(builder, wine);
        ExportProductDtoListHelper.fillImage(builder, wine);
        ExportProductDtoListHelper.fillManufacturer(builder, wine);
        ExportProductDtoListHelper.fillLink(builder, wine);
        ExportProductDtoListHelper.fillGrapeSort(builder, wine);
        ExportProductDtoListHelper.fillGastronomy(builder, wine);
        ExportProductDtoListHelper.fillTaste(builder, wine);
        ExportProductDtoListHelper.fillFlavor(builder, wine);
        ExportProductDtoListHelper.fillRating(builder, wine);

        return builder.build();
    }
}
