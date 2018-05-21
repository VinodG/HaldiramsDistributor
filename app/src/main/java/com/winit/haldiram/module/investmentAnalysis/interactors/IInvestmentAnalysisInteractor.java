package com.winit.haldiram.module.investmentAnalysis.interactors;

import com.winit.haldiram.dataobject.InvestmentAnalysisDO;
import com.winit.haldiram.module.base.interactors.IBaseInteractor;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public interface IInvestmentAnalysisInteractor extends IBaseInteractor {
    void fetchInvesmentAnalysis(final IInvestmentAnalysisInteractor.OnInvesmentAnalysisListener listener);

    /**
     * Created by Srikanth on 09/05/17.
     */
    interface OnInvesmentAnalysisListener {
        void onError(String Message);
        void onSuccess(List<InvestmentAnalysisDO> investmentAnalysisDOs);
    }
}
