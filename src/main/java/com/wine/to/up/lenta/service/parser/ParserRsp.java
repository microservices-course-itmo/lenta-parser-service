package com.wine.to.up.lenta.service.parser;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter

public class ParserRsp {

    private ArrayList<Optional<JSONObject>> wineList;

    public void add(Optional<JSONObject> jsonObject){
        wineList.add(jsonObject);
    }

    public String toString() {

        List<String> list = wineList.stream()
                .map(Optional::get)
                .map(JSONObject::toString)
                .collect(Collectors.toList());

        return list.toString();
    }
}
