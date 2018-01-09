package com.teeny.wms.util.log;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see Settings
 * @since 2017/5/9
 */

public class Settings {

    private static Settings sInstance;

    private int mMethodCount;
    private boolean mIsShowThreadInfo;
    private int mMethodOffset;
    private LogAdapter mLogAdapter;
    private boolean mIsLogAvailable;

    private Settings() {
        reset();
    }

    public static Settings getInstance() {
        if (sInstance == null) {
            synchronized (Settings.class) {
                if (sInstance == null) {
                    sInstance = new Settings();
                }
            }
        }
        return sInstance;
    }

    public int getMethodCount() {
        return mMethodCount;
    }

    public Settings setMethodCount(int methodCount) {
        this.mMethodCount = methodCount;
        return this;
    }

    public boolean isShowThreadInfo() {
        return mIsShowThreadInfo;
    }

    public Settings setShowThreadInfo(boolean showThreadInfo) {
        this.mIsShowThreadInfo = showThreadInfo;
        return this;
    }

    public int getMethodOffset() {
        return mMethodOffset;
    }

    public Settings setMethodOffset(int methodOffset) {
        this.mMethodOffset = methodOffset;
        return this;
    }

    public synchronized LogAdapter getLogAdapter() {
        if (mLogAdapter == null) {
            mLogAdapter = new AndroidLogAdapter();
        }
        return mLogAdapter;
    }

    public Settings setLogAdapter(LogAdapter logAdapter) {
        this.mLogAdapter = logAdapter;
        return this;
    }

    public boolean isLogAvailable() {
        return mIsLogAvailable;
    }

    public Settings openLogger() {
        mIsLogAvailable = true;
        return this;
    }

    public Settings closeLogger() {
        mIsLogAvailable = false;
        return this;
    }

    public void reset() {
        mMethodCount = 2;
        mMethodOffset = 0;
        mIsShowThreadInfo = true;
        mIsLogAvailable = true;
    }
}
