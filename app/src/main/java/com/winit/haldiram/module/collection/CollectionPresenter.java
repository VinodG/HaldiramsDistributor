package com.winit.haldiram.module.collection;

import com.winit.haldiram.dataobject.CollectionsDO;
import com.winit.haldiram.dataobject.UserDO;
import com.winit.haldiram.module.base.BasePresenter;
import com.winit.haldiram.module.collection.interactors.CollectionInteractor;
import com.winit.haldiram.module.collection.interactors.ICollectionInteractor;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Srikanth on 5/9/2017.
 */

public class CollectionPresenter extends BasePresenter implements ICollectionPresenter,ICollectionInteractor.OnCollectionListener {
    private CollectionInteractor interactor;
    private ICollectionView view;
    public CollectionPresenter(ICollectionView view){
        this.view = view;
        this.interactor = new CollectionInteractor();
    }

    @Override
    public void getCollections() {
        interactor.fetchCollections(CollectionPresenter.this);
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
    public void onSuccess(final HashMap<String,Vector<CollectionsDO>> hmCollections, final List<UserDO> users, final Double totalDue) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                view.onCollections(hmCollections,users,totalDue);
            }
        });
    }

    @Override
    public void loadData() {

    }
}

