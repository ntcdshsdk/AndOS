package com.osshare.andos.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.osshare.andos.R;
import com.osshare.andos.example.bean.Test001Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 16/9/13.
 */
public class Test001Adapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Test001Bean> data;

    public Test001Adapter(Context context, List<Test001Bean> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_test001, null);
            holder.body = (TextView) convertView.findViewById(R.id.tv_body);
            holder.button = (TextView) convertView.findViewById(R.id.tv_btn);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Test001Bean itemBean = data.get(position);

        holder.body.setText(itemBean.getBodyText());
        holder.button.setText(itemBean.getButtonText());

        return convertView;
    }

    public List<Test001Bean> getData() {
        return data;
    }


    public void addData(List<Test001Bean> data) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        if (data != null) {
            this.data.addAll(data);
        }
    }

    class ViewHolder {
        TextView body;
        TextView button;
    }
}
