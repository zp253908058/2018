package com.teeny.wms.datasouce.local;

import android.annotation.SuppressLint;
import android.content.Context;

import com.teeny.wms.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SharedPreferencesManager
 * @since 2017/7/16
 */

public class SharedPreferencesManager {

    private static final String NAME_SYSTEM_CONFIG = "system_config";

    private static final String NAME_USER = "user";

    private static final String NAME_ALLOT_FILTER = "allot_filter";

    private static final String NAME_ALLOT_ORDER_FILTER = "allot_order_filter";

    private static final String NAME_REFRESH_TIME = "refresh_time";

    @SuppressLint("StaticFieldLeak")
    private static volatile SharedPreferencesManager mInstance;

    public static SharedPreferencesManager getInstance() {
        if (mInstance == null) {
            synchronized (SharedPreferencesManager.class) {
                if (mInstance == null) {
                    mInstance = new SharedPreferencesManager();
                }
            }
        }
        return mInstance;
    }

    private Context mContext;
    private Map<String, Preferences> mCaches;

    private SharedPreferencesManager() {
        mCaches = new HashMap<>();
    }

    public void initialize(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public Preferences getPreferences(String name) {
        return getPreferences(name, Context.MODE_PRIVATE);
    }

    public Preferences getPreferences(String name, int mode) {
        Preferences preferences = mCaches.get(name);
        if (Validator.isNull(preferences)) {
            preferences = new Preferences(mContext, name, mode);
            mCaches.put(name, preferences);
        }
        return preferences;
    }

    public Preferences getServerConfigPreferences() {
        return getPreferences(NAME_SYSTEM_CONFIG);
    }

    public Preferences getUserPreferences() {
        return getPreferences(NAME_USER);
    }

    public Preferences getAllotFilterPreferences(){
        return getPreferences(NAME_ALLOT_FILTER);
    }

    public Preferences getAllotOrderFilterPreferences(){
        return getPreferences(NAME_ALLOT_ORDER_FILTER);
    }

    public Preferences getRefreshTimePreferences(){
        return getPreferences(NAME_REFRESH_TIME);
    }
}
