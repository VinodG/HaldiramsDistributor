package com.winit.haldiram.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.winit.haldiram.BR;
import com.winit.haldiram.R;
import com.winit.haldiram.dataobject.InvestmentAnalysisDO;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class InvestmentAnalysisAdapter extends RecyclerView.Adapter<InvestmentAnalysisAdapter.ViewHolder> {

    private Context context;
    private List<InvestmentAnalysisDO> investmentAnalysisDOs;

    public InvestmentAnalysisAdapter(Context context, List<InvestmentAnalysisDO> investmentAnalysisDOs) {
        this.context = context;
        this.investmentAnalysisDOs = investmentAnalysisDOs;
    }

    @Override
    public InvestmentAnalysisAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.investment_analysis_cell, parent, false);
        return new InvestmentAnalysisAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(InvestmentAnalysisAdapter.ViewHolder holder, int position) {
        final InvestmentAnalysisDO investmentAnalysisDO = investmentAnalysisDOs.get(position);
        holder.bind(investmentAnalysisDO);
    }

    @Override
    public int getItemCount() {
        if (investmentAnalysisDOs != null)
            return investmentAnalysisDOs.size();
        return 0;
    }

    public void refresh(List<InvestmentAnalysisDO> investmentAnalysisDOs) {
        this.investmentAnalysisDOs = investmentAnalysisDOs;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(InvestmentAnalysisDO investmentAnalysisDO) {
            binding.setVariable(BR.investmentAnalysisDO, investmentAnalysisDO);
            binding.executePendingBindings();
        }
    }
}
