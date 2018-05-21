package com.winit.haldiram.module.investmentAnalysis;

import com.winit.haldiram.dataobject.InvestmentAnalysisDO;
import com.winit.haldiram.module.base.BasePresenter;
import com.winit.haldiram.module.investmentAnalysis.interactors.InvestmentAnalysisInteractor;
import com.winit.haldiram.module.investmentAnalysis.interactors.IInvestmentAnalysisInteractor;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class InvestmentAnalysisPresenter extends BasePresenter implements IInvestmentAnalysisPresenter,IInvestmentAnalysisInteractor.OnInvesmentAnalysisListener {
    private InvestmentAnalysisInteractor interactor;
    private IInvestmentAnalysisView view;
    public InvestmentAnalysisPresenter(IInvestmentAnalysisView view){
        this.view = view;
        this.interactor = new InvestmentAnalysisInteractor();
    }

    @Override
    public void getInvestmentAnalysis() {
        interactor.fetchInvesmentAnalysis(InvestmentAnalysisPresenter.this);
    }

    @Override
    public void onError(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.showAlert(message);
            }
        });
    }

    @Override
    public void onSuccess(final List<InvestmentAnalysisDO> investmentAnalysisDOs) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onInvestmentAnalysis(investmentAnalysisDOs);
            }
        });
    }

    @Override
    public void loadData() {

    }
}

