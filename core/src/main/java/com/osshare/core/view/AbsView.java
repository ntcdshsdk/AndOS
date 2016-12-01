package com.osshare.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by apple on 16/10/6.
 */
public abstract class AbsView extends View {
    public AbsView(Context context) {
        super(context);
    }

    public AbsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
