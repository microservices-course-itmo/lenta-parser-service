package com.wine.to.up.lenta.service.parser.impl;

import com.wine.to.up.lenta.service.parser.ParserRsp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Class that store wine properties in array of Json
 */
@NoArgsConstructor
@Getter
@Setter
public class ParserRspImpl implements ParserRsp {

    /** Array of json with wine properties */
    private JSONArray wines = new JSONArray();

    /**
     * Method of puting Json in Array
     *
     * @param jsonObject This is json that will be added
     */
    public void add(JSONObject jsonObject) {
        wines.put(jsonObject);
    }

    /**
     * Method to represent a class as a string
     *
     * @return representing an instance of a class as a string
     */
    public String toString() {
        return wines.toString();
    }

    /**
     * Method of returning array of json
     *
     * @return wines.toList() This is list that built from Json array
     */
    public List<Object> getJsonList(){
        return wines.toList();
    }
}
