package com.teeny.wms.datasouce.net;

import com.teeny.wms.util.Converter;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.log.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see NetServiceManager
 * @since 2017/7/29
 */

public class NetServiceManager {

    private static final String SCHEME = "http";
    private static final int TIMEOUT = 30;

    private static volatile NetServiceManager mInstance;

    public static NetServiceManager getInstance() {
        if (mInstance == null) {
            synchronized (NetServiceManager.class) {
                if (mInstance == null) {
                    mInstance = new NetServiceManager();
                }
            }
        }
        return mInstance;
    }

    private Retrofit mRetrofit;
    private final Object mLock = new Object();
    private Map<Class<?>, Object> mServiceHolder;
    private HeaderInterceptor mHeaderInterceptor;

    private NetServiceManager() {

    }

    public void initialize(String host, String port) {
        synchronized (mLock) {
            Retrofit.Builder builder = new Retrofit.Builder();
            OkHttpClient client = createOkHttpClient();
            builder.client(client);
            builder.baseUrl(createHttpUrl(host, port));
            builder.addConverterFactory(GsonConverterFactory.create());
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync());
            mRetrofit = builder.build();
            mServiceHolder = new HashMap<>();
            Logger.e("NetServiceManager.initialize(). host: " + host + ", port: " + port);
        }
    }

    private HttpUrl createHttpUrl(String host, String port) {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme(SCHEME);
        builder.host(host);
        if (Validator.isNotEmpty(port)) {
            builder.port(Converter.toInt(port, 8080));
        }
        builder.addEncodedPathSegment("wms");
        builder.addEncodedPathSegment("api");
        builder.addPathSegment("");
        return builder.build();
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(true);
        builder.authenticator(new TokenAuthenticator());
        builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        mHeaderInterceptor = new HeaderInterceptor();
        builder.addInterceptor(mHeaderInterceptor);
        builder.addInterceptor(new LoggingInterceptor());
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
//        builder.addInterceptor(httpLoggingInterceptor);
        return builder.build();
    }

    public <T> T getService(Class<T> clz) {
        synchronized (mLock) {
            //noinspection unchecked
            T service = (T) mServiceHolder.get(clz);
            if (service == null) {
                service = mRetrofit.create(clz);
                mServiceHolder.put(clz, service);
            }
            return service;
        }
    }

    public void setWarehouseId(int id) {
        mHeaderInterceptor.setWarehouseId(id);
    }

    public int getWarehouseId() {
        return mHeaderInterceptor.getWarehouseId();
    }
}
