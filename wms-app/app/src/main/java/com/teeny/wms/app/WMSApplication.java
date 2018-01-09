package com.teeny.wms.app;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see WMSApplication
 * @since 2017/7/8
 */

public class WMSApplication extends Application {
    private ApplicationDelegate mDelegate;

    @Override
    public void onCreate() {
        super.onCreate();
        mDelegate = ApplicationDelegateFactory.create();
        mDelegate.onCreate(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mDelegate.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDelegate.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mDelegate.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        mDelegate.onTrimMemory(level);
    }
}
