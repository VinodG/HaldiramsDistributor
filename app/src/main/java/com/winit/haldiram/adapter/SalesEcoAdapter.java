package com.winit.haldiram.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.winit.haldiram.BR;
import com.winit.haldiram.R;
import com.winit.haldiram.dataobject.SalesEcoDO;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class SalesEcoAdapter extends RecyclerView.Adapter<SalesEcoAdapter.ViewHolder> {

    private Context context;
    private List<SalesEcoDO> salesEcoDOs;

    public SalesEcoAdapter(Context context, List<SalesEcoDO> salesEcoDOs) {
        this.context = context;
        this.salesEcoDOs = salesEcoDOs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.sales_eco_cell, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(SalesEcoAdapter.ViewHolder holder, int position) {
        final SalesEcoDO salesEcoDO = salesEcoDOs.get(position);
        holder.bind(salesEcoDO);
    }

    @Override
    public int getItemCount() {
        if (salesEcoDOs != null)
            return salesEcoDOs.size();
        return 0;
    }

    public void refresh(List<SalesEcoDO> salesEcoDOs) {
        this.salesEcoDOs = salesEcoDOs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(SalesEcoDO salesEcoDO) {
            binding.setVariable(BR.salesEcoDO, salesEcoDO);
            binding.executePendingBindings();
        }
    }
}
