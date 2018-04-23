package com.teeny.wms.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.teeny.wms.R;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.WindowUtils;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ToolbarActivity
 * @since 2017/7/8
 */

public abstract class ToolbarActivity extends BaseActivity {
    private static final String TAG = ToolbarActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private LinearLayout mContentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base_toolbar_layout);
        onInitialize(savedInstanceState);
    }

    private void onInitialize(@Nullable Bundle savedInstanceState) {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mContentLayout = findViewById(R.id.base_toolbar_content_layout);
        mContentLayout.setOnClickListener(WindowUtils::hideInputSoft);
        //Toolbar 初始化 获取toolbar的方式是getSupportActionBar()
        //有些操作通过ActionBar来设置也是一样的，注意要在setSupportActionBar(toolbar);之后，不然就报错了
        Drawable drawable = mToolbar.getNavigationIcon();
        if (drawable != null) {
            DrawableCompat.setTint(drawable, ResourcesCompat.getColor(getResources(), R.color.white, null));
        }

        showNavigationIcon(false);
    }

    public void showNavigationIcon(boolean isShow) {
        ActionBar bar = getSupportActionBar();
        if (Validator.isNotNull(bar)) {
            bar.setDisplayHomeAsUpEnabled(isShow);
            bar.setHomeButtonEnabled(isShow);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mContentLayout.removeAllViews();
        getLayoutInflater().inflate(layoutResID, mContentLayout);
    }

    @Override
    public void setContentView(View view) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        mContentLayout.addView(view, params);
    }

    protected void setToolbarTitle(@StringRes int titleId) {
        mToolbar.setTitle(titleId);
    }

    protected void setToolbarTitle(CharSequence title) {
        mToolbar.setTitle(title);
    }

    protected void setToolbarSubtitle(@StringRes int resId) {
        mToolbar.setSubtitle(resId);
    }

    protected void setToolbarSubtitle(CharSequence subtitle) {
        mToolbar.setSubtitle(subtitle);
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    protected LinearLayout getContentLayout() {
        return mContentLayout;
    }

    protected void setNavigationOnClickListener(View.OnClickListener listener) {
        mToolbar.setNavigationOnClickListener(listener);
    }
}
