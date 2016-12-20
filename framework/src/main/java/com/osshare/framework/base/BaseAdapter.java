package com.osshare.framework.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/11/27.
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private final String TAG = getClass().getSimpleName();

    protected LayoutInflater inflater;
    private Context mContext;
    private List<T> mData;

    private OnItemClickListener mItemClickListener;

    public BaseAdapter(Context context, List<T> data) {
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final BaseViewHolder holder = new BaseViewHolder(getItemView(parent, viewType));
        holder.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(parent,holder);
                }
            }
        });
        return holder;
    }

    public T getItem(int position) {
        return mData == null || mData.size() == 0 ? null : mData.get(position);
    }

    public abstract View getItemView(ViewGroup parent, int viewType);

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public void addData(T data) {
        if (data == null) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(data);
        notifyItemRangeInserted(mData.size(), 1);
    }

    public void addData(int location, T data) {
        if (data == null) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(location, data);
        notifyItemRangeInserted(location, 1);
    }

    public void addData(List<T> data) {
        if (data == null) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(data);
        notifyItemRangeInserted(mData.size(), data.size());
    }

    public void addData(int location, List<T> data) {
        if (data == null) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.addAll(location, data);
        notifyItemRangeInserted(location, data.size());
    }

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void clearData() {
        if (mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }

    public OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ViewGroup parent, BaseViewHolder holder);
    }
}
