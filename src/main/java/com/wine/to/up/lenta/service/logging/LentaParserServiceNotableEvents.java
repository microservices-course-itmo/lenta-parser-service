package com.wine.to.up.lenta.service.logging;

import com.wine.to.up.commonlib.logging.NotableEvent;

/** Class for noting events*/
public enum LentaParserServiceNotableEvents implements NotableEvent {
    /** Event that tell about Warn situation*/
    W_SOME_WARN_EVENT("Warn situation. Description: {}"),

    /** Event that tell about absent of attribute*/
    W_WINE_ATTRIBUTE_ABSENT("Can't set {} , url: {}"),

    /** Event that tell about parsing fail*/
    W_FIELD_PARSING_FAILED("Can't parse {} of wine {}"),

    /** Event that tell about parsing fail*/
    W_PAGE_PARSING_FAILED("Can't parse page: {}"),

    /** Event that tell about characteristics parsing fail */
    E_CHARACTERISTICS_ERROR("Can't parse wine characteristics: {}"),

    /** Event that tell about getting document*/
    E_NULL_DOCUMENT("Get null document: {}"),

    /** Event that tell about bad request*/
    E_BAD_REQUEST("Bad request: `%s` \n; Catch exception: `%s`"),

    /** Event that tell about exporting list fail*/
    E_PRODUCT_LIST_EXPORT_ERROR("Can't export product list: {}"),

    /** Event that tell about kafka sending*/
    I_KAFKA_SEND_MESSAGE_SUCCESS("Kafka send message: {}"),

    /** Event that tell about starting of method */
    I_START_JOB("Start run job method at {}"),

    /** Event that tell about ending of method and it's duration*/
    I_END_JOB("End run job method at {}; duration = {}"),

    /** Event that tell about successfull end of parsing*/
    I_WINES_PARSED("Wines  successfully parsed {}"),

    /** Event that tell about starting of parsing*/
    I_START_PARSING("Start parsing"),

    /** Event that tell about successfull parsing of wine details*/
    I_WINE_DETAILS_PARSED("Wine details parsed successfully"),

    /** Event that tell about numbers of page that was parsed */
    I_WINES_PAGE_PARSED("Page {} parsed"),

    /** Event that tell about successfull parsing of details*/
    I_DETAILS_PARSED("Details parsed");

    /** Template of event*/
    private final String template;

    /**
     * Class builder
     * @param template name of template of event
     * */
    LentaParserServiceNotableEvents(String template) {
        this.template = template;
    }

    /** Method of getting template
     * @return template of event */
    public String getTemplate() {
        return template;
    }

    /**
     * Method of geting name of event
     *@return name of event */
    @Override
    public String getName() {
        return name();
    }


}
