package com.teeny.wms.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.support.v4.app.NotificationCompat;

import com.teeny.wms.R;
import com.teeny.wms.datasouce.local.SharedPreferencesManager;
import com.teeny.wms.datasouce.local.cache.Cache;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.HomeService;
import com.teeny.wms.model.DocumentResponseEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.notification.NotificationChannelManager;
import com.teeny.wms.page.document.controller.DocumentHelper;
import com.teeny.wms.page.main.activity.MainActivity;
import com.teeny.wms.page.main.activity.RefreshTimeActivity;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.log.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see PollingService
 * @since 2017/9/1
 */

public class PollingService extends Service {

    private static final int DEFAULT_INTERVAL_SECOND = 10 * 60;

    private static final int NOTIFICATION_ID = 0x01;

    private Notification mNotification;
    private NotificationManager mManager;
    private HomeService mService;
    private Disposable mInterval;
    private EventBus mEventBus = EventBus.getDefault();
    private int mIntervalSecond;

    private static class IBinder extends Binder {

    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.e("onBind");
        return new IBinder();
    }

    @Override
    public void onCreate() {
        Logger.e("onCreate");
        mService = NetServiceManager.getInstance().getService(HomeService.class);
        mEventBus.register(this);
        initNotifyManager();
        mIntervalSecond = SharedPreferencesManager.getInstance().getRefreshTimePreferences().getInt(RefreshTimeActivity.REFRESH_TIME, DEFAULT_INTERVAL_SECOND);
        if (mIntervalSecond == 0) {
            mIntervalSecond = DEFAULT_INTERVAL_SECOND;
        }
        Logger.e(String.valueOf(mIntervalSecond));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.e("onStartCommand");
        startPolling();
        return START_STICKY;
    }

    //初始化通知栏配置
    private void initNotifyManager() {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationChannelManager.DOCUMENT_CHANNEL.id);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("单据更新");
        builder.setContentText("刚刚更新了单据.");
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        mNotification = builder.build();
        mNotification.defaults |= Notification.DEFAULT_SOUND;
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
    }

    //弹出Notification
    private void showNotification() {
        mNotification.when = System.currentTimeMillis();
        mManager.notify(NOTIFICATION_ID, mNotification);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshTimeChanged(RefreshTimeActivity.RefreshTimeFlag flag) {
        mIntervalSecond = flag.getIntervalSecond();
        Logger.e(String.valueOf(mIntervalSecond));
        startPolling();
    }

    private void startPolling() {
        Logger.e("startPolling");
        if (mInterval != null && !mInterval.isDisposed()) {
            mInterval.dispose();
        }
        mInterval = Flowable.interval(0, mIntervalSecond, TimeUnit.SECONDS).doOnNext(aLong -> {
            Logger.e("interval times : " + aLong);
            onDocumentChanged(null);
        }).subscribe();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDocumentChanged(DocumentHelper.DocumentChangedFlag flag) {
        Flowable<ResponseEntity<DocumentResponseEntity>> flowable = mService.getDocumentInfo();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<DocumentResponseEntity>() {
            @Override
            public void doNext(DocumentResponseEntity data) {
                if (Validator.isNotNull(data)) {
                    DocumentHelper.getInstance().setEntity(data);
                } else {
                    Toaster.showToast("拉取单据信息失败.");
                }
            }

            @Override
            public void doComplete() {
                showNotification();
            }
        });
    }

    @Override
    public void onDestroy() {
        Logger.e("onDestroy");
        mInterval.dispose();
        mEventBus.unregister(this);
        mManager.cancelAll();
        super.onDestroy();
    }
}
