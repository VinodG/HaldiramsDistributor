package com.winit.haldiram.ui.activities;

import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.winit.common.util.CustomBuilder;
import com.winit.common.util.StringUtils;
import com.winit.haldiram.R;
import com.winit.haldiram.adapter.CollectionAdapter;
import com.winit.haldiram.dataobject.CollectionsDO;
import com.winit.haldiram.dataobject.UserDO;
import com.winit.haldiram.module.collection.CollectionPresenter;
import com.winit.haldiram.module.collection.ICollectionPresenter;
import com.winit.haldiram.module.collection.ICollectionView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public class CollectionReportActivity extends BaseActivity implements ICollectionView {
    @Nullable
    @Bind(R.id.rvCollections)
    RecyclerView rvCollections;

    @Nullable
    @Bind(R.id.tv_select_dsm)
    TextView tvSelectDSM;

    @Nullable
    @Bind(R.id.tv_total_due)
    TextView tvTotalDue;

    private ICollectionPresenter iCollectionPresenter;
    private CollectionAdapter collectionAdapter;
    private Vector<UserDO> vecUsers;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.collection_activity, flBody, true);
        ButterKnife.bind(this);

        tvSelectDSM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //show custom builder
                CustomBuilder builder = new CustomBuilder(CollectionReportActivity.this, "Select DSM Name", true);
                builder.setSingleChoiceItems(vecUsers, tvSelectDSM.getTag()!=null?tvSelectDSM.getTag():-1, new CustomBuilder.OnClickListener()
                {
                    @Override
                    public void onClick(CustomBuilder builder, Object selectedObject)
                    {
                        builder.dismiss();
                        UserDO userDO = (UserDO) selectedObject;

                        tvSelectDSM.setText(userDO.userId);
                        tvSelectDSM.setTag(userDO);
                    }
                });
                builder.show();
            }
        });

        iCollectionPresenter = new CollectionPresenter(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CollectionReportActivity.this);
        rvCollections.setLayoutManager(mLayoutManager);
        rvCollections.setItemAnimator(new DefaultItemAnimator());

        rvCollections.setAdapter(collectionAdapter = new CollectionAdapter(CollectionReportActivity.this,null));
        iCollectionPresenter.getCollections();
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void onCollections(HashMap<String,Vector<CollectionsDO>> hmCollections, List<UserDO> users,Double totalDue) {
        vecUsers = new Vector<>(users);
        if(TextUtils.isEmpty(tvSelectDSM.getText().toString())){
            if(vecUsers!=null && vecUsers.size()>0){
                UserDO userDO = vecUsers.get(0);
                tvSelectDSM.setText(userDO.userId);
                tvSelectDSM.setTag(userDO);
            }
        }
        if(totalDue!=null)
            tvTotalDue.setText("Total Due : "+StringUtils.roundDouble(totalDue,2));
        else
            tvTotalDue.setText("Total Due : 0.00");
        List<CollectionsDO> collectionsDOs = hmCollections.get(tvSelectDSM.getText().toString());
        if(collectionsDOs == null)
            collectionsDOs = new ArrayList<>();
        collectionAdapter.refresh(collectionsDOs);
    }

    @Override
    public void showAlert(String type) {

    }

    @Override
    public void onLoadFailed() {

    }
}
