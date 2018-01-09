package com.teeny.wms.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.teeny.wms.app.AppManager;
import com.teeny.wms.app.ScannerHelper;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.view.ProgressView;

import org.greenrobot.eventbus.EventBus;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BaseActivity
 * @since 2017/7/8
 */

public abstract class BaseActivity extends AppCompatActivity implements ProgressView {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private static final String SCN_CUST_ACTION_START = "android.intent.action.SCANNER_BUTTON_DOWN";
    private static final String SCN_CUST_ACTION_CANCEL = "android.intent.action.SCANNER_BUTTON_UP";

    /**
     * Activity的堆栈管理
     */
    private AppManager mAppManager;
    private KeyEventCallback mKeyEventCallback;
    private EventBus mEventBus;
    private ProgressDialog mProgressDialog;

    protected ScannerHelper mScannerHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppManager = AppManager.getInstance();
        mAppManager.push(this);
        mScannerHelper = ScannerHelper.getInstance();
    }

    @Override
    protected void onDestroy() {
        mAppManager.remove(this);
        super.onDestroy();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = false;
        if (mKeyEventCallback != null) {
            handled = mKeyEventCallback.dispatchKeyEvent(event);
        }
        return handled || super.dispatchKeyEvent(event);
    }

    public void setEventCallback(KeyEventCallback eventCallback) {
        mKeyEventCallback = eventCallback;
    }

    /**
     * 获取ActivityStackManager
     *
     * @return AppManager
     */
    public AppManager getAppManager() {
        return mAppManager;
    }

    /**
     * 页面跳转
     *
     * @param cls  跳转的类
     * @param args 携带的消息
     */
    public void startActivity(@NonNull Class<?> cls, @Nullable Bundle args) {
        Intent intent = new Intent();
        if (args != null) {
            intent.putExtras(args);
        }
        intent.setClass(this, cls);
        this.startActivity(intent);
    }

    /**
     * 带返回的页面跳转
     *
     * @param cls     跳转的类
     * @param args    携带的消息
     * @param reqCode 请求码
     */
    public void startActivityForResult(@NonNull Class<?> cls, @Nullable Bundle args, @IntRange(from = 1) int reqCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (args != null) {
            intent.putExtras(args);
        }
        this.startActivityForResult(intent, reqCode);
    }

    /**
     * 获取上下文
     *
     * @return Context
     */
    public Context getContext() {
        return this;
    }

    public void registerEventBus() {
        if (mEventBus == null) {
            mEventBus = EventBus.getDefault();
        }
        mEventBus.register(this);
    }

    public void unregisterEventBus() {
        mEventBus.unregister(this);
    }

    public EventBus getEventBus() {
        if (mEventBus == null) {
            mEventBus = EventBus.getDefault();
        }
        return mEventBus;
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = DialogFactory.showProgressDialog(this);
            return;
        }
        mProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}