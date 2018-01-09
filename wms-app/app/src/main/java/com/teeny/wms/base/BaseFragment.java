package com.teeny.wms.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teeny.wms.util.Validator;
import com.teeny.wms.view.ProgressView;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BaseFragment
 * @since 2017/7/8
 */

public abstract class BaseFragment extends Fragment implements KeyEventCallback, ProgressView {

    private ViewHolder mViewHolder;
    private boolean mLoaded;
    private ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            ((BaseActivity) context).setEventCallback(this);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewHolder = new ViewHolder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = mViewHolder.getItemView();
        if (Validator.isNotNull(view)) {
            return view;
        }
        view = onCreateHolderView(inflater, container);
        mViewHolder.setItemView(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        mViewHolder.clear();
        super.onDestroyView();
    }

    protected abstract View onCreateHolderView(LayoutInflater inflater, @Nullable ViewGroup container);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onInitialize();
    }

    protected void onInitialize() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mLoaded && getUserVisibleHint()) {
            obtainData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isAdded() && isVisibleToUser && !mLoaded) {
            obtainData();
        }
    }

    protected void obtainData() {

    }

    protected void setLoaded(boolean loaded) {
        this.mLoaded = loaded;
    }

    public <V extends View> V findView(@IdRes int resId) {
        return mViewHolder.get(resId);
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(getContext(), "", "请稍候...");
        }
        mProgressDialog.show();
    }

    public void dismissProgressDialog() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return false;
    }
}
