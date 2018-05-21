package com.winit.haldiram.dataobject.response;

import com.winit.haldiram.dataobject.CollectionsDO;

import java.util.List;

/**
 * Created by Awaneesh on 5/9/2017.
 */

public class CollectionsResponse extends Response{
    private int isMoreAvailable;

    public List<CollectionsDO> getCollections() {
        return collections;
    }

    public void setCollections(List<CollectionsDO> collections) {
        this.collections = collections;
    }

    private List<CollectionsDO> collections;

    public int getIsMoreAvailable() {
        return isMoreAvailable;
    }

    public void setIsMoreAvailable(int isMoreAvailable) {
        this.isMoreAvailable = isMoreAvailable;
    }
}
