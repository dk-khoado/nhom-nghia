package com.doan.quanlycachly.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.doan.quanlycachly.R;
import com.doan.quanlycachly.presenter.ChangePasswordPresenterImpl;
import com.doan.quanlycachly.presenter.IPresenter.ChangePasswordPresenter;
import com.doan.quanlycachly.view.iview.IChangePasswordView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment implements IChangePasswordView {

    ChangePasswordPresenter changePasswordPresenter;

    private EditText editPassword;
    private EditText editC_Password;
    private Button btnChange;
    private View view;
    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changePasswordPresenter = new ChangePasswordPresenterImpl(this, view.getContext());

        this.view = view;
        editPassword = view.findViewById(R.id.edti_c_password);
        editC_Password = view.findViewById(R.id.edti_c_confirmPassword);
        btnChange = view.findViewById(R.id.btn_change);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getArguments() != null;
                String username = getArguments().getString("username");
                String password = getArguments().getString("password");
                if (validate()){
                    changePasswordPresenter.Change(username, password,editC_Password.getText().toString());
                }
            }
        });
    }

    private boolean validate(){
        if(editPassword.getText().toString().equals("")){
            editPassword.setError(getString(R.string.empty));
            return false;
        }
        if(editC_Password.getText().toString().equals("")){
            editC_Password.setError(getString(R.string.empty));
            return false;
        }
        if (!editPassword.getText().toString().equals(editC_Password.getText().toString())){
            editC_Password.setError("Mật Khẩu không trùng");
            return  false;
        }
        return  true;
    }
    @Override
    public void changeSuccess() {
        Navigation.findNavController(view).navigate(R.id.action_changePasswordFragment_to_loginFragment2);
    }

    @Override
    public void changeFail() {

    }
}
