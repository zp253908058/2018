package com.teeny.wms.app;

import android.app.Application;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatDelegate;

import com.teeny.wms.datasouce.local.DatabaseManager;
import com.teeny.wms.datasouce.local.SharedPreferencesManager;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.log.Logger;


/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ApplicationDelegateImpl
 * @since 2017/5/9
 */

class ApplicationDelegateImpl implements ApplicationDelegate {


    @Override
    public void onCreate(Application application) {
        Toaster.initialize(application.getApplicationContext());
        Logger.init();
        SharedPreferencesManager.getInstance().initialize(application.getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        databaseManager.initialize(application.getApplicationContext());
        databaseManager.enableDebug();

//        UncaughtExceptionHandlerImpl.getInstance().init(application);

        Logger.e("application created.");
    }

    @Override
    public void onTerminate() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int level) {

    }
}
