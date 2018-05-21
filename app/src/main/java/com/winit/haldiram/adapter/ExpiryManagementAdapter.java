package com.winit.haldiram.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.winit.haldiram.BR;
import com.winit.haldiram.R;
import com.winit.haldiram.dataobject.ExpiryManagemantDO;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class ExpiryManagementAdapter extends RecyclerView.Adapter<ExpiryManagementAdapter.ViewHolder> {

    private Context context;
    private List<ExpiryManagemantDO> expiryManagemantDOs;

    public ExpiryManagementAdapter(Context context, List<ExpiryManagemantDO> expiryManagemantDOs) {
        this.context = context;
        this.expiryManagemantDOs = expiryManagemantDOs;
    }

    @Override
    public ExpiryManagementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.expiry_management_cell, parent, false);
        return new ExpiryManagementAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ExpiryManagementAdapter.ViewHolder holder, int position) {
        final ExpiryManagemantDO expiryManagemantDO = expiryManagemantDOs.get(position);
        holder.bind(expiryManagemantDO);
    }

    @Override
    public int getItemCount() {
        if (expiryManagemantDOs != null)
            return expiryManagemantDOs.size();
        return 0;
    }

    public void refresh(List<ExpiryManagemantDO> expiryManagemantDOs) {
        this.expiryManagemantDOs = expiryManagemantDOs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ExpiryManagemantDO expiryManagemantDO) {
            binding.setVariable(BR.expiryManagementDO, expiryManagemantDO);
            binding.executePendingBindings();
        }
    }
}
