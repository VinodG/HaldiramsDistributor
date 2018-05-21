package com.winit.haldiram.ui.activities;

import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.winit.haldiram.R;
import com.winit.haldiram.adapter.InvestmentAnalysisAdapter;
import com.winit.haldiram.dataobject.InvestmentAnalysisDO;
import com.winit.haldiram.module.investmentAnalysis.IInvestmentAnalysisPresenter;
import com.winit.haldiram.module.investmentAnalysis.IInvestmentAnalysisView;
import com.winit.haldiram.module.investmentAnalysis.InvestmentAnalysisPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class InvestmentAnalysisActivity extends BaseActivity implements IInvestmentAnalysisView {
    @Nullable
    @Bind(R.id.rvInvestments)
    RecyclerView rvInvestments;

    private IInvestmentAnalysisPresenter iInvestmentAnalysisPresenter;
    private InvestmentAnalysisAdapter investmentAnalysisAdapter;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.investment_analysis_activity, flBody, true);
        ButterKnife.bind(this);
        iInvestmentAnalysisPresenter = new InvestmentAnalysisPresenter(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(InvestmentAnalysisActivity.this);
        rvInvestments.setLayoutManager(mLayoutManager);
        rvInvestments.setItemAnimator(new DefaultItemAnimator());

        rvInvestments.setAdapter(investmentAnalysisAdapter = new InvestmentAnalysisAdapter(InvestmentAnalysisActivity.this,null));
        iInvestmentAnalysisPresenter.getInvestmentAnalysis();
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void onInvestmentAnalysis(List<InvestmentAnalysisDO> investments) {
        investmentAnalysisAdapter.refresh(investments);
    }

    @Override
    public void showAlert(String type) {

    }

    @Override
    public void onLoadFailed() {

    }
}
