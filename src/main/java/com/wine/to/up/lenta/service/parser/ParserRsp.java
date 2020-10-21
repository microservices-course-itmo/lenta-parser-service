package com.wine.to.up.lenta.service.parser;

import org.json.JSONObject;

/**
 * Wrapper class for JSONArray that contain put method and toString method
 */
public interface ParserRsp {

    /**
     * Method that add JSONObject to list
     *
     * @param jsonObject
     */
    public void add(JSONObject jsonObject);

    /**
     * Method that convert List of JSONObject to String
     *
     * @return
     */
    public String toString();
}
