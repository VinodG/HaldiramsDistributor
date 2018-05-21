package com.winit.haldiram.ui.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.winit.common.util.SyncData;
import com.winit.haldiram.R;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.winit.common.util.NetworkUtility.isNetworkConnectionAvailable;

/**
 * Created by Manoj Kumar on 14-05-2017.
 */

public class Settings extends BaseActivity implements SyncData.SyncProcessListner {


    @Nullable
    @Bind(R.id.btnUploadData)
    Button btnUploadData;

    @Nullable
    @Bind(R.id.btnSync)
    Button btnSync;

    @Override
    protected void initialize() {
        inflater.inflate(R.layout.settings, flBody, true);
        ButterKnife.bind(this);

        btnUploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isNetworkConnectionAvailable(Settings.this))
                    showCustomDialog(Settings.this, getString(R.string.alert), getString(R.string.no_internet), "OK", null, "");
                else {
                    showLoader("Syncing....");
                    SyncData.setListner(Settings.this);
                    Intent intent = new Intent(Settings.this, SyncData.class);
                    startService(intent);
                }
            }
        });
    }

    @Override
    protected void setTypeFace() {

    }

    @Override
    public void setProgress(String msg) {

    }

    @Override
    public void error(String message) {

    }

    @Override
    public void end() {
        hideLoader();
    }
}
