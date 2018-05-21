package com.winit.haldiram.module.investmentAnalysis;

import com.winit.haldiram.dataobject.InvestmentAnalysisDO;
import com.winit.haldiram.module.base.IBaseView;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public interface IInvestmentAnalysisView extends IBaseView {
    public void onInvestmentAnalysis(List<InvestmentAnalysisDO> investmentAnalysisDOs);
}
