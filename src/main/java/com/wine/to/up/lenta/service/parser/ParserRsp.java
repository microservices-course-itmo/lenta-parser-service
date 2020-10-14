package com.wine.to.up.lenta.service.parser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

@NoArgsConstructor
@Getter
@Setter

public class ParserRsp {

    private JSONArray wineList = new JSONArray();

    public void add(JSONObject jsonObject){
        wineList.put(jsonObject);
    }

    public String toString() {
        return wineList.toString();
    }
}