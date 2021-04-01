package com.wine.to.up.lenta.service.cron;

import com.wine.to.up.commonlib.annotations.InjectEventLogger;
import com.wine.to.up.commonlib.logging.EventLogger;
import com.wine.to.up.commonlib.messaging.KafkaMessageSender;

import com.wine.to.up.lenta.service.components.LentaServiceMetricsCollector;
import com.wine.to.up.lenta.service.db.dto.ProductDTO;
import com.wine.to.up.lenta.service.helpers.ExportProductDtoListHelper;
import com.wine.to.up.lenta.service.parser.impl.LentaWineParserServiceImpl;
import com.wine.to.up.lenta.service.parser.impl.ParserReqServiceImpl;
import com.wine.to.up.parser.common.api.schema.ParserApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.wine.to.up.lenta.service.logging.LentaParserServiceNotableEvents.*;

@Slf4j
@PropertySource("classpath:lenta-site.properties")
public class ExportProductDtoList {

    /**
     * Link Lenta website
     */
    @Value("${site.main.url}")
    private String shopLink;

    private final ParserReqServiceImpl requestsService;
    private final LentaWineParserServiceImpl parseService;
    private final KafkaMessageSender<ParserApi.WineParsedEvent> kafkaSendMessageService;
    private final LentaServiceMetricsCollector metricsCollector;

    @InjectEventLogger
    private EventLogger eventLogger;

    /**
     * Builder of this class
     *
     * @param requestsService
     * @param parseService
     * @param kafkaSendMessageService
     * @param metricsCollector
     *
     * @return an instance of this class
     */
    public ExportProductDtoList(ParserReqServiceImpl requestsService, LentaWineParserServiceImpl parseService, KafkaMessageSender<ParserApi.WineParsedEvent> kafkaSendMessageService, LentaServiceMetricsCollector metricsCollector) {
        this.parseService = Objects.requireNonNull(parseService, "Can't get parseService");
        this.requestsService = Objects.requireNonNull(requestsService, "Can't get requestsService");
        this.kafkaSendMessageService = Objects.requireNonNull(kafkaSendMessageService, "Can't get kafkaSendMessageService");
        this.metricsCollector = Objects.requireNonNull(metricsCollector, "Can't get metricsCollector");
    }

    /**
     * Method that runs parsing once a day
     */
    @Scheduled(cron = "${cron.expression}")
    public void runCronTask() {
        long startTime = new Date().getTime();
        eventLogger.info(I_START_JOB, startTime);
        try {
            Integer batchSize = new Date().getHours() * 60 + new Date().getMinutes();
            List<ParserApi.Wine> wines = parseService
                    .parseWineList(requestsService.getJson(batchSize))
                    .stream()
                    .map(this::getProtobufProduct)
                    .collect(Collectors.toList());

            ParserApi.WineParsedEvent message = ParserApi.WineParsedEvent.newBuilder()
                    .setShopLink(shopLink)
                    .addAllWines(wines)
                    .build();

            eventLogger.info(I_KAFKA_SEND_MESSAGE_SUCCESS, message);
            kafkaSendMessageService.sendMessage(message);
            countWinesByCity(wines);
        } catch (Exception ex) {
            eventLogger.error(E_PRODUCT_LIST_EXPORT_ERROR, ex);
        }

        eventLogger.info(I_END_JOB, new Date().getTime(), (new Date().getTime() - startTime));
        metricsCollector.productListJob(new Date().getTime() - startTime);
    }

    /**
     * Method that create object of wine for kafka from parsed wines
     *
     * @param wine - parsed from lenta website wine
     *
     * @return object wine for kafka
     */
    public ParserApi.Wine getProtobufProduct(ProductDTO wine) {
        var builder = ParserApi.Wine.newBuilder();

        ExportProductDtoListHelper.fillBuilder(builder, wine);

        return builder.build();
    }

    private void countWinesByCity(List<ParserApi.Wine> wines){

        int moscowCounter = 0;
        int spbCounter = 0;

        for (ParserApi.Wine wine : wines) {
            switch (wine.getCity()){
                case "Москва":
                    moscowCounter++;
                    break;
                case "Санкт-Петербург":
                    spbCounter++;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + wine.getCity());
            }
        }

        metricsCollector.incWinesSentToKafka(moscowCounter, "Москва");
        metricsCollector.incWinesSentToKafka(spbCounter, "Санкт-Петербург");
    }
}
