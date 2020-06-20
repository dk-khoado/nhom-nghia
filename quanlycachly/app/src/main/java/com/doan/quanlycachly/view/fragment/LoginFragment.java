package com.doan.quanlycachly.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.doan.quanlycachly.R;
import com.doan.quanlycachly.helpers.data.SharedData;
import com.doan.quanlycachly.presenter.IPresenter.LoginPresenter;
import com.doan.quanlycachly.presenter.LoginPresenterImpl;
import com.doan.quanlycachly.view.iview.ILoginView;
import com.google.android.gms.location.FusedLocationProviderClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements ILoginView {

    LoginPresenter loginPresenter;
    private View view;

    private EditText editUsername;
    private EditText editPassword;
    Button btnLogin;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedData data = new SharedData(view.getContext());
        if (!data.getToken().equals("")){
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
            return;
        }

        this.view = view;
        loginPresenter = new LoginPresenterImpl(view.getContext(), this);

        btnLogin = view.findViewById(R.id.btn_login);

        editUsername = view.findViewById(R.id.edti_username);
        editPassword = view.findViewById(R.id.edit_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                if (validate()) {
                    loginPresenter.Login(editUsername.getText().toString(), editPassword.getText().toString());
                    btnLogin.setEnabled(false);
                    btnLogin.setText("Đang đăng nhập");
                }
            }
        });
    }

    private boolean validate() {
        if (editUsername.getText().toString().equals("")) {
            editUsername.setError("không được bỏ trống");
            return false;
        } else if (editPassword.getText().toString().equals("")) {
            editPassword.setError("không được bỏ trống");
            return false;
        }
        return true;
    }

    @SuppressLint("ResourceType")
    @Override
    public void LoginSuccess() {
        btnLogin.setEnabled(true);
        btnLogin.setText(getString(R.id.btn_login));
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
    }

    @SuppressLint("ResourceType")
    @Override
    public void LoginFirst() {
        btnLogin.setEnabled(true);
        btnLogin.setText(getString(R.id.btn_login));
        Bundle bundle = new Bundle();
        bundle.putString("username", editUsername.getText().toString());
        bundle.putString("password", editPassword.getText().toString());
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_changePasswordFragment, bundle);
    }

    @SuppressLint("ResourceType")
    @Override
    public void loginFail() {
        btnLogin.setEnabled(true);
        btnLogin.setText(getString(R.string.btnLogin));
    }
}
