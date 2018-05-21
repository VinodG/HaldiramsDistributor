package com.winit.haldiram.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.winit.haldiram.BR;
import com.winit.haldiram.R;
import com.winit.haldiram.dataobject.CollectionsDO;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    private Context context;
    private List<CollectionsDO> collectionsDOs;

    public CollectionAdapter(Context context, List<CollectionsDO> collectionsDOs) {
        this.context = context;
        this.collectionsDOs = collectionsDOs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.collection_cell, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CollectionAdapter.ViewHolder holder, int position) {
        final CollectionsDO collectionsDO = collectionsDOs.get(position);
        holder.bind(collectionsDO);
    }

    @Override
    public int getItemCount() {
        if (collectionsDOs != null)
            return collectionsDOs.size();
        return 0;
    }

    public void refresh(List<CollectionsDO> collectionsDOs) {
        this.collectionsDOs = collectionsDOs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(CollectionsDO collectionsDO) {
            binding.setVariable(BR.collectionDO, collectionsDO);
            binding.executePendingBindings();
        }
    }
}
