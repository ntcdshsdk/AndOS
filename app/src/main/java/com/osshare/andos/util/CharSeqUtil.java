package com.osshare.andos.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.widget.GridView;
import android.widget.ListView;

import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by apple on 16/6/15.
 */
public class CharSeqUtil {
    public static boolean isEmpty(CharSequence s) {
        if (s == null || TextUtils.getTrimmedLength(s) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void test(){
        boolean tt=new cc().cv();
        HashSet set;

    }

    public static class cc{
        private String aa;
        protected String bb;
         String cc;
        public String dd;
        public static int xx;RecyclerView recyclerView;
        public boolean cv(){
            LinearLayoutManager linearLayoutManager;
            StaggeredGridLayoutManager manager;
            ListView listView;
            GridLayoutManager manager1;
            RecyclerView.LayoutManager layoutManager;
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }
            });
           return isEmpty("");
        }

    }
}
