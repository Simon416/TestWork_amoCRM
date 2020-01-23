package com.example.test_amocrm.ui.main.main;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test_amocrm.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.DealViewHolder> {

    private static final String DATE_FORMAT = "EEE, MMM d, ''yy" ;
    private List<DealUi> listDealUi;

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat dataFormat = new SimpleDateFormat(DATE_FORMAT);

    DealAdapter(List<DealUi> listDealUi) {
        this.listDealUi = listDealUi;
    }

    @NonNull
    @Override
    public DealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DealViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DealViewHolder holder, int position) {
        holder.onBind(listDealUi.get(position));
    }

    @Override
    public int getItemCount() {
        return listDealUi.size();
    }

    class DealViewHolder extends RecyclerView.ViewHolder {
        private TextView txNameDealView;
        private TextView txDateCreateView;
        private TextView txSaleView;
        private TextView txDataStateView;

        DealViewHolder(@NonNull View itemView) {
            super(itemView);
            txNameDealView = itemView.findViewById(R.id.txNameDealView);
            txDateCreateView = itemView.findViewById(R.id.txDateCreateView);
            txSaleView = itemView.findViewById(R.id.txSaleView);
            txDataStateView = itemView.findViewById(R.id.txDateStateView);
        }

        private void onBind(DealUi dealUi) {
            txNameDealView.setText(dealUi.getName());
            txDateCreateView.setText(dataFormat
                    .format(new Date(dealUi.getDataCreate() * 1000)));
            txSaleView.setText(txSaleView.getContext().getString(R.string.string_currents, dealUi.getSale()));
            txDataStateView.setText(dealUi.getStatusName());
        }
    }

}