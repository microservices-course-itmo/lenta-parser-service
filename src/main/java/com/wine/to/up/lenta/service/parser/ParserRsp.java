package com.wine.to.up.lenta.service.parser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
/**
 * Wrapper class for JSONArray that contain put method and toString method
 */
public class ParserRsp {

    private JSONArray wineList = new JSONArray();

    public void add(JSONObject jsonObject) {
        wineList.put(jsonObject);
    }

    public String toString() {
        return wineList.toString();
    }

    public List<Object> getJsonList(){
        return wineList.toList();
    };
}
