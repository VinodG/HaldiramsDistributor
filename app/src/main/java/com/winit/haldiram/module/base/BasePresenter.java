package com.winit.haldiram.module.base;

import android.os.Handler;
import android.os.Looper;

import com.winit.common.Preference;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Girish Velivela on 11/15/2016.
 */

public abstract class BasePresenter implements IBasePresenter {

    private IBaseView view;
    protected Preference preference;
    protected String userCode;
    protected Handler handler;

    public BasePresenter(){
        preference = Preference.getInstance();
        handler = new Handler(Looper.getMainLooper());
    }

    public BasePresenter(IBaseView iBaseView){
        this();
        this.view = iBaseView;
    }

    public interface Predicate<T> {
        boolean apply(T type);
    }

    public static <T> Collection<T> filter(Collection<T> col, Predicate<T> predicate) {

        Collection<T> result = new ArrayList<T>();
        if(col!=null)
        {
            for (T element : col) {
                if (predicate.apply(element)) {
                    result.add(element);
                }
            }
        }
        return result;
    }

}
