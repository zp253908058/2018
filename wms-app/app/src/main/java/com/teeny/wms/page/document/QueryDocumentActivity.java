package com.teeny.wms.page.document;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseFragmentPagerAdapter;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.page.document.controller.DocumentHelper;
import com.teeny.wms.page.document.fragment.DocumentFragment;
import com.teeny.wms.util.Validator;

/**
 * Class description:单据查询
 *
 * @author zp
 * @version 1.0
 * @see QueryDocumentActivity
 * @since 2017/7/16
 */

public class QueryDocumentActivity extends ToolbarActivity implements BaseFragmentPagerAdapter.Callback, ViewPager.OnPageChangeListener {

    private static final String KEY_TYPE = "type";
    private static final String TAG = "QueryDocumentActivity";

    public static void startActivity(Context context, int type) {
        Intent intent = new Intent();
        intent.setClass(context, QueryDocumentActivity.class);
        intent.putExtra(KEY_TYPE, type);
        context.startActivity(intent);
    }

    private SparseArrayCompat<Fragment> mFragmentHolder = new SparseArrayCompat<>();
    private String[] mTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_tab_view_pager_layout);
        initView();
    }

    private void initView() {
        mTitles = getResources().getStringArray(R.array.sku_title);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), this));
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        int type = getIntent().getIntExtra(KEY_TYPE, 0);
        viewPager.setCurrentItem(type);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DocumentHelper.getInstance().notifyDocumentChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragmentHolder.get(position);
        if (Validator.isNull(fragment)) {
            fragment = createFragment(position);
            mFragmentHolder.put(position, fragment);
        }
        return fragment;
    }

    private Fragment createFragment(int position) {
        return DocumentFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
