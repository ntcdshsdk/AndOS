package com.osshare.andos.base;

import android.app.Activity;

import com.osshare.framework.annotate.ActivityScope;

import dagger.Module;
import dagger.Provides;
import io.reactivex.BackpressureOverflowStrategy;

/**
 * Created by apple on 16/10/31.
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity activity() {
        return this.activity;
    }
}
