package com.wine.to.up.lenta.service.parser;

import com.wine.to.up.lenta.service.parser.impl.ParserRspImpl;

/**
 * Class that parse data from "Лента" website
 */
public interface ParserReqService {

    /**
     * Parsing method
     *
     * @return Array of JSONObject
     */
    ParserRspImpl getJson();
}
