package com.teeny.wms.datasouce.local.cache;

import com.teeny.wms.datasouce.local.Preferences;
import com.teeny.wms.datasouce.local.SharedPreferencesManager;
import com.teeny.wms.model.UserEntity;
import com.teeny.wms.util.Preconditions;
import com.teeny.wms.util.PreferencesUtils;
import com.teeny.wms.util.log.Logger;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see UserManager
 * @since 2017/8/5
 */

public class UserManager implements Cache<UserEntity> {

    private static volatile UserManager mInstance;

    public static UserManager getInstance() {
        if (mInstance == null) {
            synchronized (UserManager.class) {
                if (mInstance == null) {
                    mInstance = new UserManager();
                }
            }
        }
        return mInstance;
    }

    private Preferences mUserPreference;
    private UserEntity mUserEntity;

    private UserManager() {
        mUserPreference = SharedPreferencesManager.getInstance().getUserPreferences();
        mUserEntity = new UserEntity();
    }

    @Override
    public UserEntity get() {
        if (mUserEntity.isEmpty()) {
            try {
                PreferencesUtils.putInEntity(mUserEntity, mUserPreference);
            } catch (IllegalAccessException e) {
                Logger.e(e, "UserManager get error.");
            }
        }
        return mUserEntity;
    }

    @Override
    public UserManager set(UserEntity cache) {
        Preconditions.checkNotNull(cache);
        if (mUserEntity != cache) {
            mUserEntity = cache;
        }
        return this;
    }

    @Override
    public void put(String key, Object value) {
        PreferencesUtils.putInSharedPreferences(key, value.getClass().getSimpleName(), value, mUserPreference);
    }

    @Override
    public Object peek(String key) {
        return mUserPreference.getAll().get(key);
    }

    @Override
    public void clear() {
        mUserPreference.clear();
        mUserEntity = new UserEntity();
    }

    @Override
    public void save() {
        try {
            PreferencesUtils.putInSharedPreferences(mUserEntity, mUserPreference);
        } catch (IllegalAccessException e) {
            Logger.e(e, "UserManager save error.");
        }
    }
}
