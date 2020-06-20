package com.doan.quanlycachly.presenter;

import android.content.Context;
import android.util.Log;

import com.doan.quanlycachly.helpers.APIConnect;
import com.doan.quanlycachly.helpers.URLApi;
import com.doan.quanlycachly.listener.onAPIConnect;
import com.doan.quanlycachly.model.ResponseChangePassword;
import com.doan.quanlycachly.model.ResponseLogin;
import com.doan.quanlycachly.presenter.IPresenter.ChangePasswordPresenter;
import com.doan.quanlycachly.view.iview.IChangePasswordView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordPresenterImpl implements ChangePasswordPresenter {

    IChangePasswordView changePasswordView;
    Context context;

    public ChangePasswordPresenterImpl(IChangePasswordView changePasswordView, Context context) {
        this.changePasswordView = changePasswordView;
        this.context = context;
    }

    @Override
    public void Change(String username, String password, String confirmPassword) {
        APIConnect apiConnect = new APIConnect(new onAPIConnect() {
            @Override
            public void onSuccess(String response) {
                Gson gson =new Gson();
                ResponseChangePassword res = gson.fromJson(response, ResponseChangePassword.class);

                if (res.getSuccess()){
                    changePasswordView.changeSuccess();
                }else{
                    changePasswordView.changeFail();
                }
            }

            @Override
            public void onError(String message) {

            }
        });

        Map<String, String> data= new HashMap<>();
        data.put("username", username);
        data.put("password", confirmPassword);
        data.put("oldpassword", password);
        apiConnect.Post(URLApi.BASE_URL+ URLApi.CHANGE_PASS,data);
    }
}
