package com.winit.haldiram.ui.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.winit.common.constant.ConstantExtraKey;
import com.winit.haldiram.R;
import com.winit.haldiram.dataaccesslayer.StocksDA;
import com.winit.haldiram.dataobject.StockDO;
import com.winit.haldiram.module.stocks.IStocksPresenter;
import com.winit.haldiram.module.stocks.IStocksView;
import com.winit.haldiram.module.stocks.StocksPresenter;
import com.winit.haldiram.pinch.ListStockFragment;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class StockActivity extends BaseActivity implements IStocksView {

    private IStocksPresenter iStocksPresenter;
    SampleFragmentPagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private PagerSlidingTabStrip tabsStrip;
    private HashMap<String,ArrayList<StockDO>> hmStocks;


    @Override
    protected void initialize() {
        inflater.inflate(R.layout.stock_pager_activity, flBody, true);
        ButterKnife.bind(this);
        iStocksPresenter = new StocksPresenter(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        new Thread(new Runnable() {
            @Override
            public void run() {
                hmStocks = new StocksDA().getStocks();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(),hmStocks);
                        viewPager.setAdapter(pagerAdapter);
                        tabsStrip.setViewPager(viewPager);
                    }
                });
            }
        }).start();
//        viewPager.setOffscreenPageLimit(1);
//        hmStocks = new HashMap<>();
//        pagerAdapter = new SampleFragmentPagerAdapter(getSupportFragmentManager(),hmStocks);
//        viewPager.setAdapter(pagerAdapter);
//
//        // Give the PagerSlidingTabStrip the ViewPager
//        // Attach the view pager to the tab strip
//        tabsStrip.setViewPager(viewPager);

//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(StockActivity.this);
//        rvCollections.setLayoutManager(mLayoutManager);
//        rvCollections.setItemAnimator(new DefaultItemAnimator());
//
//        rvCollections.setAdapter(collectionAdapter = new CollectionAdapter(StockActivity.this,null));
        iStocksPresenter.getStocks();
    }

    public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
//        final int PAGE_COUNT = 3;
        private HashMap<String,ArrayList<StockDO>> hmStocks;
        private String tabTitles[] = new String[] { "OOS", "Over Stock", "Model Stock" };

        public SampleFragmentPagerAdapter(FragmentManager fm,HashMap<String,ArrayList<StockDO>> hmStocks) {
            super(fm);
            this.hmStocks = hmStocks;
        }

        @Override
        public int getCount() {
            if(tabTitles!=null && tabTitles.length>0)
                return tabTitles.length;
            return 0;
        }

        @Override
        public Fragment getItem(int position) {
            ListStockFragment listStockFragment = new ListStockFragment();
            Bundle args = new Bundle();
            if(hmStocks!=null && hmStocks.containsKey(tabTitles[position]))
                args.putSerializable(ConstantExtraKey.ARR_STOCKS,hmStocks.get(tabTitles[position]));
            else
                args.putSerializable(ConstantExtraKey.ARR_STOCKS,new ArrayList<>());
            listStockFragment.setArguments(args);
            return listStockFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

        public void refresh(HashMap<String,ArrayList<StockDO>> hmStocks){
            this.hmStocks = hmStocks;
            notifyDataSetChanged();
        }

    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void onStocks(HashMap<String,ArrayList<StockDO>> hmStocks) {
        pagerAdapter.refresh(hmStocks);
        tabsStrip.notifyDataSetChanged();
    }

    @Override
    public void showAlert(String type) {

    }

    @Override
    public void onLoadFailed() {

    }
}
