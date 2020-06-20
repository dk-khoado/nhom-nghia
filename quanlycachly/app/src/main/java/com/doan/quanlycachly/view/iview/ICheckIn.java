package com.doan.quanlycachly.view.iview;

import com.doan.quanlycachly.model.ResponseHistory;

import java.util.List;

public interface ICheckIn {
    void onCheckInSuccess();
    void showLoading();
    void hideLoading();
    void showHistory(List<ResponseHistory> histories);
}
