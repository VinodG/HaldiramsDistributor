package com.winit.haldiram.ui.customview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ExpandableHeightRecyclerView extends RecyclerView
{
    boolean expanded = false;
    public ExpandableHeightRecyclerView(Context context){
        super(context);
    }
    public ExpandableHeightRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    public ExpandableHeightRecyclerView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }
    public boolean isExpanded()
    {
        return expanded;
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = getMeasuredHeight();
    }
    public void setExpanded(boolean expanded)
    {
        this.expanded = expanded;
    }
}
