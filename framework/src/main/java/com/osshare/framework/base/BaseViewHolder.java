package com.osshare.framework.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by apple on 16/11/27.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> views;
    private View.OnClickListener clickListener;

    public BaseViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
    }

    public <V extends View> V getView(int id) {
        View v = views.get(id);
        if (v == null) {
            v = itemView.findViewById(id);
        }
        return (V) v;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        if (clickListener != null) {
            itemView.setOnClickListener(clickListener);
        }
    }
}
