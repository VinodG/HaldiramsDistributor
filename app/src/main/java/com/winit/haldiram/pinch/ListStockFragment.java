package com.winit.haldiram.pinch;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winit.common.constant.ConstantExtraKey;
import com.winit.haldiram.R;
import com.winit.haldiram.adapter.StockAdapter;
import com.winit.haldiram.dataobject.StockDO;

import java.util.ArrayList;

/**
 * Created by Awaneesh on 5/11/2017.
 */

public class ListStockFragment extends Fragment {
    private ArrayList<StockDO> stockDOs;
    private StockAdapter stockAdapter;
    public ListStockFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stockDOs = (ArrayList<StockDO>) getArguments().getSerializable(ConstantExtraKey.ARR_STOCKS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_activity, container, false);
        RecyclerView rvStocks = (RecyclerView)view.findViewById(R.id.rvStocks);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rvStocks.setLayoutManager(mLayoutManager);
        rvStocks.setItemAnimator(new DefaultItemAnimator());
//        ListView lvStocks = (ListView)view.findViewById(R.id.lvStocks);
        stockAdapter = new StockAdapter(stockDOs);
//        lvStocks.setAdapter(stockAdapter);
        rvStocks.setAdapter(stockAdapter);
        return view;
    }

    private class StocksAdapter extends BaseAdapter{

        private ArrayList<StockDO> arrStocks;
        public StocksAdapter(ArrayList<StockDO> arrStocks){
            this.arrStocks = arrStocks;
        }
        @Override
        public int getCount() {
            if (arrStocks != null && arrStocks.size() > 0)
                return arrStocks.size();

            return 0;
        }
        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null)
                convertView = (LinearLayout) LayoutInflater.from(getActivity())
                        .inflate(R.layout.stock_cell_new, null);
            TextView tvItem = (TextView)convertView.findViewById(R.id.tv_item);
            TextView tvProductNorm = (TextView)convertView.findViewById(R.id.tv_product_norm);
            TextView tvStockQty = (TextView)convertView.findViewById(R.id.tv_stock_quantity);
            TextView tvNoOfDays = (TextView)convertView.findViewById(R.id.tv_item);
            return convertView;
        }

    }
}
