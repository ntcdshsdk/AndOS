package com.osshare.framework.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by apple on 16/11/28.
 */
public class ImageLoader {
    public static void loadImage(Context context, String url, ImageView view) {
        Glide.with(context).load(url).fitCenter().crossFade().into(view);
    }
}
