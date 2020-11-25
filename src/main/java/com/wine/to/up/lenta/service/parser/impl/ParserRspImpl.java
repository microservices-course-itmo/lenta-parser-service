package com.wine.to.up.lenta.service.parser.impl;

import com.wine.to.up.lenta.service.parser.ParserRsp;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ParserRspImpl implements ParserRsp {

    private JSONArray wines = new JSONArray();

    public void add(JSONObject jsonObject) {
        wines.put(jsonObject);
    }

    public String toString() {
        return wines.toString();
    }

    public List<Object> getJsonList(){
        return wines.toList();
    }
}
