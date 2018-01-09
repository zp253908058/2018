package com.teeny.wms.util.log;

import android.util.Log;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AndroidLogAdapter
 * @since 2017/5/9
 */

class AndroidLogAdapter implements LogAdapter {

    @Override
    public int v(String tag, String msg) {
        return Log.v(tag, msg);
    }

    @Override
    public int v(String tag, String msg, Throwable tr) {
        return Log.v(tag, msg, tr);
    }

    @Override
    public int d(String tag, String msg) {
        return Log.d(tag, msg);
    }

    @Override
    public int d(String tag, String msg, Throwable tr) {
        return Log.d(tag, msg, tr);
    }

    @Override
    public int i(String tag, String msg) {
        return Log.i(tag, msg);
    }

    @Override
    public int i(String tag, String msg, Throwable tr) {
        return Log.i(tag, msg, tr);
    }

    @Override
    public int w(String tag, String msg) {
        return Log.w(tag, msg);
    }

    @Override
    public int w(String tag, String msg, Throwable tr) {
        return Log.w(tag, msg, tr);
    }

    @Override
    public int w(String tag, Throwable tr) {
        return Log.w(tag, tr);
    }

    @Override
    public int e(String tag, String msg) {
        return Log.e(tag, msg);
    }

    @Override
    public int e(String tag, String msg, Throwable tr) {
        return Log.e(tag, msg, tr);
    }

    @Override
    public int wtf(String tag, String msg) {
        return Log.wtf(tag, msg);
    }

    @Override
    public int wtf(String tag, Throwable tr) {
        return Log.wtf(tag, tr);
    }

    @Override
    public int wtf(String tag, String msg, Throwable tr) {
        return Log.v(tag, msg, tr);
    }
}
