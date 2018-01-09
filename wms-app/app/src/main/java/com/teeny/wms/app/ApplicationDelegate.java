package com.teeny.wms.app;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ApplicationDelegate
 * @since 2017/7/8
 */

public interface ApplicationDelegate {
    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    void onCreate(Application app);

    /**
     * This method is for use in emulated process environments.  It will
     * never be called on a production Android device, where processes are
     * removed by simply killing them; no user code (including this callback)
     * is executed when doing so.
     */
    void onTerminate();

    void onConfigurationChanged(Configuration newConfig);

    void onLowMemory();

    void onTrimMemory(int level);
}
