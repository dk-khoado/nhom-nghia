package com.doan.quanlycachly.presenter;

import android.content.Context;

import com.doan.quanlycachly.helpers.APIConnect;
import com.doan.quanlycachly.helpers.URLApi;
import com.doan.quanlycachly.helpers.data.SharedData;
import com.doan.quanlycachly.listener.onAPIConnect;
import com.doan.quanlycachly.model.ResponseProfile;
import com.doan.quanlycachly.presenter.IPresenter.ProfileRresenter;
import com.doan.quanlycachly.view.iview.IInfoView;
import com.google.gson.Gson;

public class ProfilePresenterImpl implements ProfileRresenter {

    IInfoView infoView;
    Context context;

    public ProfilePresenterImpl(IInfoView infoView, Context context) {
        this.infoView = infoView;
        this.context = context;
    }

    @Override
    public void getProfile() {
        APIConnect apiConnect = new APIConnect(new onAPIConnect() {
            @Override
            public void onSuccess(String response) {
                try{
                    Gson gson = new Gson();
                    ResponseProfile responseProfile = gson.fromJson(response, ResponseProfile.class);
                    infoView.getProfile(responseProfile);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String message) {
            }
        });

        SharedData data = new SharedData(context);
        apiConnect.Get(URLApi.BASE_URL + URLApi.GET_PROFILE + data.getField("id"), null);
    }
}
