package com.teeny.wms.service;

import android.content.Context;
import android.content.Intent;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see PollingHelper
 * @since 2017/9/1
 */

public class PollingHelper {

    private Context mContext;
    private Class<PollingService> mTarget;

    public PollingHelper(Context context) {
        mContext = context;
        mTarget = PollingService.class;
    }

    //开启轮询服务
    public void startPollingService() {
        Intent intent = new Intent(mContext, mTarget);
        mContext.startService(intent);
    }

    //停止轮询服务
    public void stopPollingService() {
        Intent intent = new Intent(mContext, mTarget);
        mContext.stopService(intent);
    }
}
