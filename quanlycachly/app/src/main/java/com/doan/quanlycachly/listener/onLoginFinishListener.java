package com.doan.quanlycachly.listener;

public interface onLoginFinishListener extends onAPIConnect{
    void onUsernameError();

    void onPasswordError();
}
