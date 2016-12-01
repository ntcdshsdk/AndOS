package com.osshare.framework.manager;

/**
 * Created by apple on 16/9/14.
 */
public class Config {

    public static final int EDITION = Constant.RELEASE;

    public static final String BASE_URL = EDITION == Constant.DEBUG ? Constant.BASE_URL_DEBUG : Constant.BASE_URL_RELEASE;


}
