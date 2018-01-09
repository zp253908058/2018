package com.teeny.wms.page.allot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.SparseArrayCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseFragmentPagerAdapter;
import com.teeny.wms.base.TableViewPagerActivity;
import com.teeny.wms.page.allot.fragment.AllotGoodsQueryFragment;
import com.teeny.wms.page.allot.fragment.AllotGoodsSelectedFragment;
import com.teeny.wms.page.allot.fragment.AllotOrderHeaderFragment;
import com.teeny.wms.util.Validator;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotOrderAddActivity
 * @since 2018/1/4
 */

public class AllotOrderAddActivity extends TableViewPagerActivity implements BaseFragmentPagerAdapter.Callback {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AllotOrderAddActivity.class);
        context.startActivity(intent);
    }

    private String[] mTitles;
    private SparseArrayCompat<Fragment> mFragmentHolder = new SparseArrayCompat<>();

    private Fragment mHeaderFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideFloatingActionButton();
        hideCounterView();
        setupHeader();

        mTitles = getResources().getStringArray(R.array.allot_tab);
        setupViewPager(new BaseFragmentPagerAdapter(getSupportFragmentManager(), this));
    }

    private void setupHeader() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (mHeaderFragment == null) {
            mHeaderFragment = createHeaderFragment();
        }
        ft.replace(getHeaderLayoutId(), mHeaderFragment);
        ft.commitAllowingStateLoss();
    }

    private Fragment createHeaderFragment() {
        return AllotOrderHeaderFragment.newInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AllotOrderFilterActivity.startActivity(this);
        return super.onOptionsItemSelected(item);
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

    protected Fragment createFragment(int position) {
        if (position == 0){
            return AllotGoodsQueryFragment.newInstance();
        }
        return AllotGoodsSelectedFragment.newInstance();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}