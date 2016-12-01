package com.osshare.andos.util;

import android.text.TextUtils;

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
}
