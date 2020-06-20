package com.doan.quanlycachly.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.doan.quanlycachly.R;
import com.doan.quanlycachly.model.ResponseProfile;
import com.doan.quanlycachly.presenter.IPresenter.ProfileRresenter;
import com.doan.quanlycachly.presenter.ProfilePresenterImpl;
import com.doan.quanlycachly.view.iview.IInfoView;
import com.marcinmoskala.arcseekbar.ArcSeekBar;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment implements IInfoView {

    private EditText editFullName;
    private EditText editNamSinh;
    private EditText editCMND;
    private ArcSeekBar numberDay;
    private ProfileRresenter profileRresenter;
    private TextView txtDay;
    private TextView txtResult;
    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editFullName = view.findViewById(R.id.profile_fullname);
        editCMND = view.findViewById(R.id.profile_cmnd);
        editNamSinh = view.findViewById(R.id.profile_namSinh);
        txtDay = view.findViewById(R.id.txt_day);
        txtResult = view.findViewById(R.id.result);
        numberDay = view.findViewById(R.id.number_dayCL);

        profileRresenter = new ProfilePresenterImpl(this, view.getContext());
        profileRresenter.getProfile();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getProfile(ResponseProfile profile) {
        editFullName.setText(profile.getHoTen());
        editCMND.setText(profile.getCMND());
        editNamSinh.setText(String.valueOf(profile.getNamSinh()));
        Date now = new Date();

        int days = daysBetween(profile.getNgayBatDau(), now);
        if (days <= profile.getSoNgayCL()){
            numberDay.setProgress(days);
            txtDay.setText(days+"/"+profile.getSoNgayCL());
        }
        if (days > profile.getSoNgayCL()){
            txtResult.setText("Đã hoàn thành cách ly");
        }
    }

    private int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
}
