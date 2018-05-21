package com.winit.haldiram.module.expiryManagement;

import com.winit.haldiram.dataobject.CollectionsDO;
import com.winit.haldiram.module.base.IBaseView;

import java.util.List;

/**
 * Created by Srikanth on 5/9/2017.
 */

public interface IExpiryManagementView extends IBaseView {
    public void onCollections(List<CollectionsDO> collectionsDOs);
}
