package com.teeny.wms.datasouce.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see Preferences
 * @since 2017/7/16
 */

public class Preferences {
    private SharedPreferences mSharedPreferences;

    public Preferences(Context context, String name, int mode) {
        mSharedPreferences = context.getSharedPreferences(name, mode);
    }

    public Preferences putString(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
        return this;
    }

    public Preferences putBoolean(String key, boolean value) {
        mSharedPreferences.edit().putBoolean(key, value).apply();
        return this;
    }

    public Preferences putFloat(String key, float value) {
        mSharedPreferences.edit().putFloat(key, value).apply();
        return this;
    }

    public Preferences putInt(String key, int value) {
        mSharedPreferences.edit().putInt(key, value).apply();
        return this;
    }

    public Preferences putLong(String key, long value) {
        mSharedPreferences.edit().putLong(key, value).apply();
        return this;
    }

    public Preferences putStringSet(String key, Set<String> value) {
        mSharedPreferences.edit().putStringSet(key, value).apply();
        return this;
    }

    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return mSharedPreferences.getFloat(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    public Set<String> getStringSet(String key, Set<String> defValue) {
        return mSharedPreferences.getStringSet(key, defValue);
    }

    public Preferences remove(String key) {
        mSharedPreferences.edit().remove(key).apply();
        return this;
    }

    public Preferences clear() {
        mSharedPreferences.edit().clear().apply();
        return this;
    }

    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }
}
