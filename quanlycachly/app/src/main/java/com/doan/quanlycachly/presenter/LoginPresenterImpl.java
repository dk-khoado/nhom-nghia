package com.doan.quanlycachly.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.doan.quanlycachly.helpers.APIConnect;
import com.doan.quanlycachly.helpers.URLApi;
import com.doan.quanlycachly.helpers.data.SharedData;
import com.doan.quanlycachly.listener.onAPIConnect;
import com.doan.quanlycachly.model.ResponseLogin;
import com.doan.quanlycachly.presenter.IPresenter.LoginPresenter;
import com.doan.quanlycachly.view.iview.ILoginView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class LoginPresenterImpl  implements LoginPresenter {
    private ILoginView loginView;
    private SharedData sharedData;
    private Context context;
    public LoginPresenterImpl(Context context, ILoginView loginView) {
        this.loginView = loginView;
        this.context = context;
        sharedData = new SharedData(context);
    }

    @Override
    public void Login(String username, String password) {

        APIConnect apiConnect = new APIConnect(new onAPIConnect() {
            @SuppressLint("ShowToast")
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                ResponseLogin responseLogin =gson.fromJson(response, ResponseLogin.class);
                if (responseLogin.getSuccess()){
                    if (responseLogin.getToken().equals("")){
                        loginView.LoginFirst();
                        return;
                    }
                    Log.i("aaa","");
                    sharedData.setField("token", responseLogin.getToken());
                    sharedData.setField("id", responseLogin.getId());
                    sharedData.commit();
                    loginView.LoginSuccess();
                }else {
                    Toast.makeText(context, "Sai tên đăng nhập hoặc mât khẩu", Toast.LENGTH_SHORT).show();
                    loginView.loginFail();
                }
            }

            @Override
            public void onError(String message) {

            }
        });
        Map<String, String> data= new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        apiConnect.Post(URLApi.BASE_URL+ URLApi.LOGIN, data);
    }
}
