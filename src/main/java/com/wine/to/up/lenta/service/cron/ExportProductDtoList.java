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

        var builder = UpdateProducts.Product.newBuilder();

        if (wine.getName() != null) {
            builder.setName(wine.getName());
        }

        if (wine.getBrand() != null) {
            builder.setBrand(wine.getBrand());
        }

        if (wine.getCountry() != null) {
            builder.setCountry(wine.getCountry());
        }

        if (wine.getCountry() != null) {
            builder.setCapacity(wine.getCapacity());
        }

        if (wine.getCountry() != null) {
            builder.setStrength(wine.getStrength());
        }

        if (convertColor(wine.getColor()) != null) {
            builder.setColor(convertColor(wine.getColor()));
        }

        if (convertSugar(wine.getSugar()) != null) {
            builder.setSugar(convertSugar(wine.getSugar()));
        }

        if (wine.getCountry() != null) {
            builder.setOldPrice(wine.getOldPrice());
        }

        if (wine.getImage() != null) {
            builder.setImage(wine.getImage());
        }

        if (wine.getCountry() != null) {
            builder.setDiscount(wine.getDiscount());
        }

        if (wine.getManufacturer() != null) {
            builder.setManufacturer(wine.getManufacturer());
        }

        if (wine.getRegion() != null) {
            builder.setRegion(wine.getRegion());
        }

        if (wine.getLink() != null) {
            builder.setLink(wine.getLink());
        }

        if (wine.getGrapeSort() != null) {
            builder.setGrapeSort(wine.getGrapeSort());
        }

        if (wine.getCountry() != null) {
            builder.setYear(wine.getYear());
        }

        if (wine.getDescription() != null) {
            builder.setDescription(wine.getDescription());
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

        if (wine.getCountry() != null) {
            builder.setRating(wine.getRating());
        }

        return builder.build();
    }

    private UpdateProducts.Product.Sugar convertSugar(String value) {
        return Sugar.resolve(value);
    }

    private UpdateProducts.Product.Color convertColor(String value) {
        return Color.resolve(value);
    }
}
