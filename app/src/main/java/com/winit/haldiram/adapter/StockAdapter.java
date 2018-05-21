package com.winit.haldiram.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.winit.haldiram.BR;
import com.winit.haldiram.R;
import com.winit.haldiram.dataobject.StockDO;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

//    private Context context;
    private List<StockDO> stockDOs;

    public StockAdapter( List<StockDO> stockDOs) {
        this.stockDOs = stockDOs;
    }

    @Override
    public StockAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.stock_cell, parent, false);
        return new StockAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(StockAdapter.ViewHolder holder, int position) {
        final StockDO stockDO = stockDOs.get(position);
        holder.bind(stockDO);
    }

    @Override
    public int getItemCount() {
        if (stockDOs != null && stockDOs.size()>0)
            return stockDOs.size();
        return 0;
    }

    public void refresh(List<StockDO> stockDOs) {
        this.stockDOs = stockDOs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(StockDO stockDO) {
            binding.setVariable(BR.stockDO, stockDO);
            binding.executePendingBindings();
        }
    }
}
