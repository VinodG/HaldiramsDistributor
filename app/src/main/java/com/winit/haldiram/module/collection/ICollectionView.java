package com.winit.haldiram.module.collection;

import com.winit.haldiram.dataobject.CollectionsDO;
import com.winit.haldiram.dataobject.UserDO;
import com.winit.haldiram.module.base.IBaseView;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Srikanth on 5/9/2017.
 */

public interface ICollectionView extends IBaseView {
    public void onCollections(HashMap<String,Vector<CollectionsDO>> hmCollections, List<UserDO> users,Double totalDue);
}
