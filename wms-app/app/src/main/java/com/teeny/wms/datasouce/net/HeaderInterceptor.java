package com.teeny.wms.datasouce.net;

import android.support.annotation.NonNull;

import com.teeny.wms.datasouce.local.cache.UserManager;
import com.teeny.wms.model.UserEntity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see HeaderInterceptor
 * @since 2017/7/29
 */

public class HeaderInterceptor implements Interceptor {

    private UserManager mUserManager = UserManager.getInstance();

    private int mWarehouseId;

    public int getWarehouseId() {
        return mWarehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        mWarehouseId = warehouseId;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        builder.header("Content-Type", "application/json;charset=UTF-8");

        UserEntity entity = mUserManager.get();
        if (!entity.isEmpty()) {
            builder.header("account", String.valueOf(entity.getDatabaseName()));
            builder.header("Authorization", entity.getTokenType() + " " + entity.getAccessToken());
        }
        builder.header("sId", String.valueOf(mWarehouseId));
        Request newRequest = builder.build();

        return chain.proceed(newRequest);
    }
}
