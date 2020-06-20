package com.doan.quanlycachly.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.agik.AGIKSwipeButton.Controller.OnSwipeCompleteListener;
import com.agik.AGIKSwipeButton.View.Swipe_Button_View;
import com.doan.quanlycachly.R;
import com.doan.quanlycachly.adapters.HistoryAdapter;
import com.doan.quanlycachly.helpers.data.SharedData;
import com.doan.quanlycachly.model.ResponseHistory;
import com.doan.quanlycachly.presenter.CheckInPresenterImpl;
import com.doan.quanlycachly.presenter.IPresenter.CheckInPresenter;
import com.doan.quanlycachly.view.iview.ICheckIn;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.rey.material.widget.CheckBox;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class CheckInFragment extends Fragment implements LocationListener, ICheckIn {


    RecyclerView recyclerView;
    HistoryAdapter adapter;
    Swipe_Button_View swipe_button_view;
    private FusedLocationProviderClient fusedLocationClient;
    private CheckInPresenter checkInPresenter;

    private CheckBox checkBox_sot;
    private CheckBox checkBox_ho;
    private CheckBox checkBox_khoTho;
    private CheckBox checkBox_dauNguoi;
    private CheckBox checkBox_sucKhoeTot;
    ProgressDialog dialog;

    List<ResponseHistory> data = new ArrayList<>();;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setTitle("Đang gửi");

        swipe_button_view = view.findViewById(R.id.start);
        recyclerView = view.findViewById(R.id.historyCheckin);

        //checkbox
        checkBox_sot = view.findViewById(R.id.checkbox_sot);
        checkBox_ho = view.findViewById(R.id.checkbox_ho);
        checkBox_khoTho = view.findViewById(R.id.checkbox_khotho);
        checkBox_dauNguoi = view.findViewById(R.id.checkbox_daunguoi);
        checkBox_sucKhoeTot = view.findViewById(R.id.checkbox_suckhoetot);

        checkBox_sot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxValidate();
            }
        });

        checkBox_ho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxValidate();
            }
        });
        checkBox_khoTho.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxValidate();
            }
        });
        checkBox_dauNguoi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxValidate();
            }
        });
        checkBox_sucKhoeTot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxValidate();
            }
        });

        checkInPresenter = new CheckInPresenterImpl(view.getContext(), this);

        swipe_button_view.setSwipeBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        swipe_button_view.setThumbBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        swipe_button_view.swipe_both_direction = true;

        //history
        data.add(new ResponseHistory());
        adapter = new HistoryAdapter(R.layout.history_item, view.getContext(), data);
        Context context = view.getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        checkInPresenter.getHistory();
    }

    @SuppressLint("ShowToast")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        final SharedData sharedData = new SharedData(getContext());
        swipe_button_view.setOnSwipeCompleteListener_forward_reverse(new OnSwipeCompleteListener() {
            @Override
            public void onSwipe_Forward(Swipe_Button_View swipe_button_view) {
                String resutl = getResult();
                if (!resutl.equals("")) {
                    showLoading();
                    fusedLocationClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                checkInPresenter.checkIn(location.getLongitude(), location.getLatitude(), getResult(), sharedData.getField("id"));
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Vui lòng trả lời câu hỏi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSwipe_Reverse(Swipe_Button_View swipe_button_view) {

            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
    }


    private void checkBoxValidate() {
        if (checkBox_sucKhoeTot.isChecked()) {
            checkBox_ho.setChecked(false);
            checkBox_dauNguoi.setChecked(false);
            checkBox_khoTho.setChecked(false);
            checkBox_sot.setChecked(false);
        } else {
            checkBox_sucKhoeTot.setChecked(false);
        }
    }

    private String getResult() {
        StringBuilder result = new StringBuilder();
        if (checkBox_sucKhoeTot.isChecked()) {
            result.append("Sức khỏe tốt");
            return result.toString();
        }
        if (checkBox_sot.isChecked()) {
            result.append("Sốt ,");
        }
        if (checkBox_ho.isChecked()) {
            result.append("Ho ,");
        }
        if (checkBox_khoTho.isChecked()) {
            result.append("Khó Thở ,");
        }
        if (checkBox_dauNguoi.isChecked()) {
            result.append("Đau người ,mệt mỏi");
        }
        return result.toString();
    }

    @SuppressLint("ShowToast")
    @Override
    public void onCheckInSuccess() {
        checkInPresenter.getHistory();
        Toast.makeText(getContext(), "Đã gửi báo cáo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void showHistory(List<ResponseHistory> histories) {
        data.clear();
        data.addAll(histories);
        adapter.notifyDataSetChanged();
    }
}
