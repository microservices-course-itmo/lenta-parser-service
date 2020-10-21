package com.wine.to.up.lenta.service.parser;

import org.json.JSONObject;

import java.util.List;

/**
 * Wrapper class for JSONArray that contain put method and toString method
 */
public interface ParserRsp {

    /**
     * Method that add JSONObject to list
     *
     * @param jsonObject - List of JSONObject
     */
    public void add(JSONObject jsonObject);

    /**
     * Method that convert List of JSONObject to String
     *
     * @return List of JSONObject that was convert to String
     */
    public String toString();

    /**
     * Method that return List of JSONObject
     *
     * @return List of JSONObject
     */
    public List<Object> getJsonList();
}
