package com.doan.quanlycachly.presenter.IPresenter;

import com.doan.quanlycachly.model.ResponseHistory;

import java.util.List;
import java.util.Map;

public interface CheckInPresenter {
    void checkIn(double longitude, double latitude, String status, String userID);

    void getHistory();
}
