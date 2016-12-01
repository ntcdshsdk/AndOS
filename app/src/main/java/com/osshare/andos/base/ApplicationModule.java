package com.osshare.andos.base;

import android.content.Context;

import com.osshare.andos.manager.AndOsApplication;

import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by apple on 16/10/31.
 */

@Module
public class ApplicationModule {
    private final AndOsApplication application;

    public ApplicationModule(AndOsApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    ExecutorService provideThreadExecutor(ExecutorService executor) {
        return executor;
    }
}
