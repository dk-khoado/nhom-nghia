package com.doan.quanlycachly.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.doan.quanlycachly.helpers.APIConnect;
import com.doan.quanlycachly.helpers.URLApi;
import com.doan.quanlycachly.helpers.data.SharedData;
import com.doan.quanlycachly.listener.onAPIConnect;
import com.doan.quanlycachly.model.ResponseCheckIn;
import com.doan.quanlycachly.model.ResponseHistory;
import com.doan.quanlycachly.presenter.IPresenter.CheckInPresenter;
import com.doan.quanlycachly.view.iview.ICheckIn;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckInPresenterImpl implements CheckInPresenter {

    private Context context;
    private ICheckIn checkInView;

    public CheckInPresenterImpl(Context context, ICheckIn checkInView) {
        this.context = context;
        this.checkInView = checkInView;
    }

    @Override
    public void checkIn(double longitude, double latitude, String status, String userID) {
        APIConnect apiConnect = new APIConnect(new onAPIConnect() {
            @SuppressLint("ShowToast")
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                ResponseCheckIn res = gson.fromJson(response, ResponseCheckIn.class);
                if (res.isSuccess()) {
                    checkInView.onCheckInSuccess();
                }else{
                    Toast.makeText(context, "lỗi vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
                checkInView.hideLoading();
            }

            @Override
            public void onError(String message) {

            }
        });
        Map<String, String> data = new HashMap<>();
        data.put("latitude", String.valueOf(latitude));
        data.put("longitude", String.valueOf(longitude));
        data.put("status", status);
        data.put("userID", userID);
        apiConnect.Post(URLApi.BASE_URL + URLApi.CHECK_IN, data);
//        checkInView.showLoading();
    }

    @Override
    public void getHistory() {
        APIConnect apiConnect = new APIConnect(new onAPIConnect() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                try {

                    ResponseHistory[] responseHistories = gson.fromJson(response, ResponseHistory[].class);
                    checkInView.showHistory(Arrays.asList(responseHistories));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {

            }
        });
        SharedData sharedData = new SharedData(context);
        Map<String, String> data = new HashMap<>();
        data.put("checkSum", sharedData.getField("token"));
        apiConnect.Post(URLApi.BASE_URL + URLApi.CHECK_IN_HISTORY, data);
    }
}
