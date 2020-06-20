package com.doan.quanlycachly.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doan.quanlycachly.R;
import com.doan.quanlycachly.model.ResponseHistory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private int resource;
    Context context;
    List<ResponseHistory> data = new ArrayList<>();

    public HistoryAdapter(int resource, Context context, List<ResponseHistory> data) {
        this.resource = resource;
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(resource, parent, false);
        return new ViewHolder(view);
    }
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponseHistory item= data.get(position);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            holder.date.setText(format.format(item.getDateCheckin()));
            format = new SimpleDateFormat("HH:mm");
            holder.time.setText(format.format(item.getDateCheckin()));
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.suckhoe.setText(item.getStatus());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView time;
        TextView suckhoe;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.history_date);
            time = itemView.findViewById(R.id.history_time);
            suckhoe = itemView.findViewById(R.id.history_suckhoe);
        }
    }
}
