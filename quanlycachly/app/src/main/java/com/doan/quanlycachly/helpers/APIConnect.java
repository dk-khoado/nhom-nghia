package com.doan.quanlycachly.helpers;


import com.doan.quanlycachly.listener.onAPIConnect;

import java.util.Map;

public class APIConnect {

    private onAPIConnect listener;

    public APIConnect(onAPIConnect listener) {
        this.listener = listener;
    }

    public void Get(String url, Map<String, String> data) {
        HttpTask apiConnect = new HttpTask(url, Method.GET, data, listener);
        apiConnect.execute();
    }

    public void Post(String url) {
        HttpTask apiConnect = new HttpTask(url, Method.POST);
        apiConnect.setListener(listener);
        apiConnect.execute();
    }

    public void Post(String url, Map<String, String> data) {
        HttpTask apiConnect = new HttpTask(url, Method.POST, data, listener);
        apiConnect.execute();
    }

    public void PostWithAuth(String url, Map<String, String> data, String token) {
        HttpTask apiConnect = new HttpTask(url, Method.POST, data, listener);
        apiConnect.setAuth(true);
        apiConnect.setToken(token);
        apiConnect.execute();
    }
}
