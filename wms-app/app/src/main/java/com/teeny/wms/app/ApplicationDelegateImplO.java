package com.teeny.wms.app;

import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.teeny.wms.notification.NotificationChannelManager;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ApplicationDelegateImplO
 * @since 2018/4/19
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class ApplicationDelegateImplO extends ApplicationDelegateImpl {

    @Override
    public void onCreate(Application application) {
        super.onCreate(application);
        new NotificationChannelManager(application.getApplicationContext(), NotificationChannelManager.DOCUMENT_CHANNEL).initNotificationChannel();
    }
}
