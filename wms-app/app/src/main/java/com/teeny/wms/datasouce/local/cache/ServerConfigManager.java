package com.teeny.wms.datasouce.local.cache;

import com.teeny.wms.datasouce.local.Preferences;
import com.teeny.wms.datasouce.local.SharedPreferencesManager;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.model.ServerConfigEntity;
import com.teeny.wms.util.Preconditions;
import com.teeny.wms.util.PreferencesUtils;
import com.teeny.wms.util.log.Logger;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ServerConfigManager
 * @since 2017/8/6
 */

public class ServerConfigManager implements Cache<ServerConfigEntity> {

    private static volatile ServerConfigManager mInstance;

    public static ServerConfigManager getInstance() {
        if (mInstance == null) {
            synchronized (ServerConfigManager.class) {
                if (mInstance == null) {
                    mInstance = new ServerConfigManager();
                }
            }
        }
        return mInstance;
    }

    private Preferences mServerPreferences;
    private ServerConfigEntity mServerConfigEntity;

    private ServerConfigManager() {
        mServerPreferences = SharedPreferencesManager.getInstance().getServerConfigPreferences();
        mServerConfigEntity = new ServerConfigEntity();
    }

    @Override
    public ServerConfigEntity get() {
        if (mServerConfigEntity.isEmpty()) {
            try {
                PreferencesUtils.putInEntity(mServerConfigEntity, mServerPreferences);
            } catch (IllegalAccessException e) {
                Logger.e(e, "ServerConfigManager get error.");
            }
        } else {
            Logger.e(mServerConfigEntity.toString());
        }
        return mServerConfigEntity;
    }

    @Override
    public ServerConfigManager set(ServerConfigEntity cache) {
        Preconditions.checkNotNull(cache);
        if (mServerConfigEntity != cache) {
            mServerConfigEntity = cache;
            NetServiceManager.getInstance().initialize(mServerConfigEntity.getServerAddress(), mServerConfigEntity.getPort());
        }
        return this;
    }

    @Override
    public void put(String key, Object value) {
        PreferencesUtils.putInSharedPreferences(key, value.getClass().getSimpleName(), value, mServerPreferences);
    }

    @Override
    public Object peek(String key) {
        return mServerPreferences.getAll().get(key);
    }

    @Override
    public void clear() {
        mServerPreferences.clear();
        mServerConfigEntity = new ServerConfigEntity();
    }

    @Override
    public void save() {
        try {
            PreferencesUtils.putInSharedPreferences(mServerConfigEntity, mServerPreferences);
        } catch (IllegalAccessException e) {
            Logger.e(e, "ServerConfigManager save error.");
        }
    }
}