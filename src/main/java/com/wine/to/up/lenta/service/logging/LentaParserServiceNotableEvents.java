package com.wine.to.up.lenta.service.logging;

import com.wine.to.up.commonlib.logging.NotableEvent;

public enum LentaParserServiceNotableEvents implements NotableEvent {
    W_SOME_WARN_EVENT("Warn situation. Description: {}"),
    W_WINE_ATTRIBUTE_ABSENT("Can't set {} , url: {}"),
    W_WINE_DETAILS_PARSING_FAILED("Can't parse {} of wine {}"),
    W_PAGE_PARSING_FAILED("Can't parse page."),

    E_CHARACTERISTICS_ERROR("Can't parse wine characteristics: {}"),
    E_NULL_DOCUMENT("Get null document: {}"),
    E_BAD_REQUEST("Bad request: `%s` \n; Catch exception: `%s`"),
    E_PRODUCT_LIST_EXPORT_ERROR("Can't export product list: {}"),

    I_KAFKA_SEND_MESSAGE_SUCCESS("Kafka send message: {}"),
    I_START_JOB("Start run job method at {}"),
    I_END_JOB("End run job method at {}; duration = {}"),
    I_WINES_PARSED("Wines  successfully parsed {}"),
    I_START_PARSING("Start parsing"),
    I_WINE_DETAILS_PARSED("Wine details parsed successfully"),
    I_WINES_PAGE_PARSED("Page {} parsed"),
    I_DETAILS_PARSED("Details parsed");

    private final String template;

    LentaParserServiceNotableEvents(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    @Override
    public String getName() {
        return name();
    }


}
