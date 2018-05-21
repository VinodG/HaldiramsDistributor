package com.winit.haldiram.ui.activities;

import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.winit.haldiram.R;
import com.winit.haldiram.adapter.SalesEcoAdapter;
import com.winit.haldiram.dataobject.SalesEcoDO;
import com.winit.haldiram.module.salesEco.ISalesEcoPresenter;
import com.winit.haldiram.module.salesEco.ISalesEcoView;
import com.winit.haldiram.module.salesEco.SalesEcoPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class SalesEcoActivity extends BaseActivity implements ISalesEcoView {
    @Nullable
    @Bind(R.id.rvSalesEco)
    RecyclerView rvSalesEco;

    private ISalesEcoPresenter iSalesEcoPresenter;
    private SalesEcoAdapter salesEcoAdapter;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.sales_eco_activity, flBody, true);
        ButterKnife.bind(this);
        iSalesEcoPresenter = new SalesEcoPresenter(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SalesEcoActivity.this);
        rvSalesEco.setLayoutManager(mLayoutManager);
        rvSalesEco.setItemAnimator(new DefaultItemAnimator());

        rvSalesEco.setAdapter(salesEcoAdapter = new SalesEcoAdapter(SalesEcoActivity.this,null));
        iSalesEcoPresenter.getSalesEco();
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void onSalesEco(List<SalesEcoDO> salesEcoDOs) {
        salesEcoAdapter.refresh(salesEcoDOs);
    }

    @Override
    public void showAlert(String type) {

    }

    @Override
    public void onLoadFailed() {

    }
}
