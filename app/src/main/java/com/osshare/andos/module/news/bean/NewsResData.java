package com.osshare.andos.module.news.bean;

import com.osshare.framework.base.BaseResData;

import java.util.List;

/**
 * Created by apple on 16/11/27.
 */
public class NewsResData extends BaseResData {
    private List<News> newslist;

    public List<News> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<News> newslist) {
        this.newslist = newslist;
    }
}
