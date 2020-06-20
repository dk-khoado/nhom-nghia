package com.doan.quanlycachly.helpers;

import com.google.gson.Gson;

import java.util.Map;

public class MapDataToModel {
    private Map<Object, Object> data;
    private Object data_array;

    private Class model;

    public MapDataToModel(Map<Object, Object> data, Class model) {
        this.data = data;
        this.model = model;
    }

    public MapDataToModel(Object data, Class model) {
        this.data_array =  data;
        this.model = model;
    }

    public Object blind() {
        Gson gson = new Gson();
        if (data != null) {
            String json = gson.toJson(data);
            return gson.fromJson(json, model);
        } else if (data_array != null) {
            String json = gson.toJson(data_array);
            Object o = gson.fromJson(json, model);
            return o;
        }
        return null;
    }
}
