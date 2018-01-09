package com.teeny.wms.datasouce.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.teeny.wms.datasouce.local.cache.UserManager;
import com.teeny.wms.datasouce.net.service.LoginService;
import com.teeny.wms.model.UserEntity;
import com.teeny.wms.util.log.Logger;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see TokenAuthenticator
 * @since 2017/8/5
 */

public class TokenAuthenticator implements Authenticator {

    private UserManager mUserManager;

    TokenAuthenticator() {
        mUserManager = UserManager.getInstance();
    }

    @Nullable
    @Override
    public Request authenticate(@NonNull Route route, @NonNull Response response) throws IOException {
        Logger.e("token invalid.");
        String refreshToken = mUserManager.get().getRefreshToken();
        LoginService service = NetServiceManager.getInstance().getService(LoginService.class);
        Call<UserEntity> call = service.refreshToken(refreshToken, "refresh_token", "wms");
        UserEntity userEntity = call.execute().body();
        if (userEntity == null) {
            mUserManager.clear();
            return null;
        }
        Logger.e(userEntity.toString());
        UserEntity cache = mUserManager.get();
        cache.setAccessToken(userEntity.getAccessToken());
        mUserManager.save();
        Request.Builder builder = response.request().newBuilder();
        builder.header("Authorization", userEntity.getTokenType() + " " + userEntity.getAccessToken());
        return builder.build();
    }
}
