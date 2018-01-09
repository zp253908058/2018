package com.teeny.wms.datasouce.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.teeny.wms.dao.DaoMaster;
import com.teeny.wms.dao.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see DatabaseManager
 * @since 2017/7/30
 */

public class DatabaseManager {

    private static volatile DatabaseManager mInstance;

    public static DatabaseManager getInstance() {
        if (mInstance == null) {
            synchronized (DatabaseManager.class) {
                if (mInstance == null) {
                    mInstance = new DatabaseManager();
                }
            }
        }
        return mInstance;
    }


    private static final String DB_NAME = "wms-db";

    private DaoSession mDaoSession;

    private DatabaseManager() {

    }

    public void initialize(Context applicationContext) {
        //通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        //可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        //注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        //所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(applicationContext, DB_NAME, null);
        SQLiteDatabase db = helper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    /**
     * 打开输出日志，默认关闭
     */
    public void enableDebug() {
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }
}
