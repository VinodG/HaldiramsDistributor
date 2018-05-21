package com.winit.haldiram.module.stocks;

import com.winit.haldiram.dataobject.StockDO;
import com.winit.haldiram.module.base.IBaseView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public interface IStocksView extends IBaseView {
    public void onStocks(HashMap<String,ArrayList<StockDO>> hmStocks);
}
