package com.teeny.wms.base;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.teeny.wms.R;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see TableViewPagerActivity
 * @since 2017/12/26
 */

public abstract class TableViewPagerActivity extends ToolbarActivity {

    private FrameLayout mHeaderLayout;
    private TabLayout mTabLayout;
    private KeyValueTextView mCountView;
    private ViewPager mViewPager;
    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_view_pager_layout);

        initView();
    }

    protected void initView() {
        mHeaderLayout = (FrameLayout) findViewById(R.id.table_view_pager_header_layout);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mCountView = (KeyValueTextView) findViewById(R.id.key_value_text_view);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.table_view_pager_action_button);
        mFloatingActionButton.setOnClickListener(this::onClick);
        mFloatingActionButton.setOnLongClickListener(this::onLongClick);
    }

    public void setHeaderLayout(@LayoutRes int layoutResID) {
        mHeaderLayout.removeAllViews();
        getLayoutInflater().inflate(layoutResID, mHeaderLayout);
    }

    public void setHeaderLayout(View view) {
        mHeaderLayout.removeAllViews();
        mHeaderLayout.addView(view);
    }

    public void setHeaderLayout(View view, ViewGroup.LayoutParams params) {
        mHeaderLayout.removeAllViews();
        mHeaderLayout.addView(view, params);
    }

    @IdRes
    public int getHeaderLayoutId() {
        return R.id.table_view_pager_header_layout;
    }

    public void setCountValue(int value) {
        mCountView.setValue(String.valueOf(value));
    }

    public void hideCounterView() {
        mCountView.setVisibility(View.GONE);
    }

    public void setupViewPager(PagerAdapter adapter, ViewPager.OnPageChangeListener... listeners) {
        mViewPager.setAdapter(adapter);
        if (Validator.isNotEmpty(listeners)) {
            for (ViewPager.OnPageChangeListener listener : listeners) {
                mViewPager.addOnPageChangeListener(listener);
            }
        }
        mTabLayout.setupWithViewPager(mViewPager);
    }

    public void hideFloatingActionButton() {
        mFloatingActionButton.setVisibility(View.GONE);
    }

    public void showFloatingActionButton() {
        mFloatingActionButton.setVisibility(View.VISIBLE);
    }

    public void setupFloatingActionButton(@DrawableRes int drawable) {
        mFloatingActionButton.setVisibility(View.VISIBLE);
        mFloatingActionButton.setImageResource(drawable);
    }

    public int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }

    protected void onClick(View view) {

    }

    protected boolean onLongClick(View view) {
        return false;
    }
}
