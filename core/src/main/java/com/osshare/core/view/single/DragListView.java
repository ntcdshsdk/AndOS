package com.osshare.core.view.single;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by apple on 16/11/4.
 */
public class DragListView extends RecyclerView {
    public DragListView(Context context) {
        this(context,null);
    }

    public DragListView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragListView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
