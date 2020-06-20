package com.doan.quanlycachly.helpers;

import android.accounts.NetworkErrorException;
import android.os.AsyncTask;

import com.doan.quanlycachly.listener.onAPIConnect;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

class HttpTask extends AsyncTask<String, Void, String> {

    private String base_url;
    private String method;
    private Map<String, String> data;

    private onAPIConnect listener = null;
    private boolean auth = false;
    private String token = "";

    private boolean IS_FAIL = false;

    HttpTask(String url, Method method) {
        base_url = url;
        this.method = method.name();
        data = new HashMap<>();
    }

    public HttpTask(String base_url, Method method, Map<String, String> data) {
        this.base_url = base_url;
        this.method = method.name();
        this.data = data;
    }

    public HttpTask(String base_url, Method method, Map<String, String> data, onAPIConnect listener) {
        this.base_url = base_url;
        this.method = method.name();
        this.data = data;
        this.listener = listener;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setListener(onAPIConnect listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {

        String result = null;

        HttpURLConnection httpURLConnection = null;

        try {
            if (method.equals(Method.GET.name()) && data != null) {

                StringBuilder url = new StringBuilder(base_url);

                for (Map.Entry<String, String> entry : data.entrySet()) {
                    url.append("?").append(entry.getKey()).append("=").append(entry.getValue());
                }

                base_url = url.toString();
            }

            URL url = new URL(base_url);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            if (auth) {

                httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);
            }

            httpURLConnection.setRequestMethod(method);
            httpURLConnection.setDoInput(true);

            if (method.equals(Method.POST.name()) && data != null) {
                JSONObject jsonObject = new JSONObject(data);
                String str = jsonObject.toString();
                OutputStream os = httpURLConnection.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                osw.write(str);
                osw.flush();
                osw.close();
            }
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode >= 400 && responseCode <= 599) {
                throw new NetworkErrorException("Lỗi:" + responseCode + " | " + httpURLConnection.getResponseMessage());
            }

            InputStream in = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);

            BufferedReader reader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            reader.close();
            httpURLConnection.disconnect();
            result = sb.toString();

        } catch (Exception e) {
            IS_FAIL = true;
            result = e.getMessage();
        }

        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (listener != null && s != null) {

            if (!s.equals("")) {

                if (IS_FAIL) {
                    listener.onError(s);
                } else {
                    listener.onSuccess(s);
                }
            }
        }
    }

    @Override
    protected void onCancelled(String s) {
        super.onCancelled(s);
        if (listener != null)
            listener.onError(s);
    }

}