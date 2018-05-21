package com.winit.haldiram.module.expiryManagement;

import com.winit.haldiram.dataobject.CollectionsDO;
import com.winit.haldiram.module.base.BasePresenter;
import com.winit.haldiram.module.expiryManagement.interactors.ExpiryManagementInteractor;
import com.winit.haldiram.module.expiryManagement.interactors.IExpiryManagementInteractor;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class ExpiryManagementPresenter extends BasePresenter implements IExpiryManagementPresenter,IExpiryManagementInteractor.OnCollectionListener {
    private ExpiryManagementInteractor interactor;
    private IExpiryManagementView view;
    public ExpiryManagementPresenter(IExpiryManagementView view){
        this.view = view;
        this.interactor = new ExpiryManagementInteractor();
    }

    @Override
    public void getCollections() {
        interactor.fetchCollections(ExpiryManagementPresenter.this);
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
    public void onSuccess(final List<CollectionsDO> collectionsDOs) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onCollections(collectionsDOs);
            }
        });
    }

    @Override
    public void loadData() {

    }
}

