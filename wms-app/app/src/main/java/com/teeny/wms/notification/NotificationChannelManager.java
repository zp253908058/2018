package com.teeny.wms.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see NotificationChannelManager
 * @since 2018/4/19
 */

public class NotificationChannelManager {
    //单据渠道实体
    public static final ChannelEntity DOCUMENT_CHANNEL = new ChannelEntity("document", "单据信息", 4);

    private Context mContext;
    private List<ChannelEntity> mChannels;

    @RequiresApi(Build.VERSION_CODES.O)
    private NotificationChannelManager(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            throw new RuntimeException();
        }
        this.mContext = context;
        mChannels = new ArrayList<>();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public NotificationChannelManager(Context context, ChannelEntity... entities) {
        this(context);
        mChannels.addAll(Arrays.asList(entities));
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public void initNotificationChannel() {
        for (ChannelEntity entity : mChannels) {
            createNotificationChannel(entity);
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(ChannelEntity entity) {
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.createNotificationChannel(new NotificationChannel(entity.id, entity.name, entity.importance));
        }
    }

    public NotificationChannelManager addChannel(ChannelEntity entity) {
        mChannels.add(entity);
        return this;
    }

    public static class ChannelEntity {
        public String id;
        public CharSequence name;
        private int importance;

        ChannelEntity(String id, CharSequence name, int importance) {
            this.id = id;
            this.name = name;
            this.importance = importance;
        }
    }
}
