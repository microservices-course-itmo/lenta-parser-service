package com.wine.to.up.lenta.service.cron;

import com.wine.to.up.commonlib.messaging.KafkaMessageSender;
import com.wine.to.up.lenta.service.db.constans.Color;
import com.wine.to.up.lenta.service.db.constans.Sugar;
import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import com.wine.to.up.lenta.service.parser.LentaWineParserService;
import com.wine.to.up.lenta.service.parser.ParserReqService;
import com.wine.to.up.parser.common.api.schema.UpdateProducts;
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

    private final ParserReqService requestsService;
    private final LentaWineParserService parseService;
    private final KafkaMessageSender<UpdateProducts.UpdateProductsMessage> kafkaSendMessageService;

    public ExportProductDtoList(ParserReqService requestsService, LentaWineParserService parseService,
                                KafkaMessageSender<UpdateProducts.UpdateProductsMessage> kafkaSendMessageService) {
        this.requestsService = Objects.requireNonNull(requestsService, "Can't get requestsService");
        this.parseService = Objects.requireNonNull(parseService, "Can't get parseService");
        this.kafkaSendMessageService = Objects.requireNonNull(kafkaSendMessageService, "Can't get kafkaSendMessageService");
    }

    @Scheduled(cron = "${cron.expression}")
    public void runCronTask() {

        log.info("Job started");

        List<UpdateProducts.Product> wines = parseService
                .parseWineList(requestsService.getJsonList())
                .stream()
                .map(this::getProtobufProduct)
                .collect(Collectors.toList());

        UpdateProducts.UpdateProductsMessage message = UpdateProducts.UpdateProductsMessage.newBuilder()
                .setShopLink(SHOP)
                .addAllProducts(wines)
                .build();

        log.info("Parsed: {} wines", wines.size());

        kafkaSendMessageService.sendMessage(message);

        log.info("Send message to Kafka");
    }

    private UpdateProducts.Product getProtobufProduct(ProductDTO wine) {

        return UpdateProducts.Product.newBuilder().setName(wine.getName())
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

    private UpdateProducts.Product.Sugar convertSugar(String value) {
        return Sugar.resolve(value);
    }

    private UpdateProducts.Product.Color convertColor(String value) {
        return Color.resolve(value);
    }
}
