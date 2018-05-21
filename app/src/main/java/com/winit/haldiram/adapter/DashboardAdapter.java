package com.winit.haldiram.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.winit.haldiram.BR;
import com.winit.haldiram.R;
import com.winit.haldiram.dataobject.DashboardDO;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private Context context;
    private List<DashboardDO> dashboardDOs;

    public DashboardAdapter(Context context, List<DashboardDO> dashboardDOs) {
        this.context = context;
        this.dashboardDOs = dashboardDOs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.dashboard_cell, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DashboardAdapter.ViewHolder holder, int position) {
        final DashboardDO dashboardDO = dashboardDOs.get(position);
        holder.bind(dashboardDO);
    }

    @Override
    public int getItemCount() {
        if (dashboardDOs != null)
            return dashboardDOs.size();
        return 0;
    }

    public void refresh(List<DashboardDO> dashboardDOs) {
        this.dashboardDOs = dashboardDOs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DashboardDO dashboardDO) {
            binding.setVariable(BR.dashboardDO, dashboardDO);
            binding.executePendingBindings();
        }
    }
}
