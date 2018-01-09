package com.teeny.wms.datasouce.net;

import android.content.Context;

import com.teeny.wms.app.AppManager;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.page.login.LoginActivity;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.log.Logger;
import com.teeny.wms.view.ProgressView;

import org.reactivestreams.Subscription;

import io.reactivex.FlowableSubscriber;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ResponseSubscriber
 * @since 2017/8/2
 */

public abstract class ResponseSubscriber<T> implements FlowableSubscriber<ResponseEntity<T>> {

    public static class SimpleProgressView implements ProgressView {

        @Override
        public void showProgressDialog() {

        }

        @Override
        public void dismissProgressDialog() {

        }
    }

    private ProgressView mProgressView;
    private AppManager mAppManager;
    private boolean mIsHandled;

    public ResponseSubscriber() {
        this(new SimpleProgressView());
    }

    public ResponseSubscriber(ProgressView progressView) {
        mProgressView = progressView;
        mAppManager = AppManager.getInstance();
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
        mProgressView.showProgressDialog();
    }

    @Override
    public void onNext(ResponseEntity<T> responseEntity) {
        if (Validator.isNull(responseEntity)) {
            onError(new Throwable("请求失败,请重试."));
            mIsHandled = true;
            return;
        }
        if (responseEntity.getResult() > 0) {
            onError(new Throwable(responseEntity.getMsg()));
            mIsHandled = true;
            return;
        }
        doNext(responseEntity.getData());
    }

    @Override
    public void onError(Throwable t) {
        mProgressView.dismissProgressDialog();
        String msg = t.getMessage();
        Toaster.showToast(msg);
        if (Validator.isNotEmpty(msg) && msg.contains("401")) {
            Context context = mAppManager.peek();
            LoginActivity.startActivity(context);
        }
        Logger.e(t, t.getMessage());
    }

    @Override
    public void onComplete() {
        if (!mIsHandled) {
            mProgressView.dismissProgressDialog();
            doComplete();
        }
    }

    public abstract void doNext(T data);

    public abstract void doComplete();
}
