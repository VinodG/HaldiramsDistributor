package com.winit.haldiram.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.winit.haldiram.BR;
import com.winit.haldiram.R;
import com.winit.haldiram.dataobject.FeedsDO;

import java.util.List;

/**
 * Created by Girish Velivela on 5/9/2017.
 */

public class FeedsAdapter  extends RecyclerView.Adapter<FeedsAdapter.ViewHolder> {

    private Context context;
    private List<FeedsDO> feedsDOs;

    public FeedsAdapter(Context context, List<FeedsDO> feedsDOs){
        this.context = context;
        this.feedsDOs = feedsDOs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.feeds_cell, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FeedsDO feedsDO = feedsDOs.get(position);
        holder.bind(feedsDO);
    }

    @Override
    public int getItemCount() {
        if(feedsDOs != null)
            return feedsDOs.size();
        return 0;
    }

    public void refresh(List<FeedsDO> feedsDOs) {
        this.feedsDOs = feedsDOs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FeedsDO feedsDO) {
            binding.setVariable(BR.feedDo, feedsDO);
            binding.executePendingBindings();
        }
    }
}


