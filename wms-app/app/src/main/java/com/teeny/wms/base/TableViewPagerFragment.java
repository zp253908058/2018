package com.teeny.wms.base;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teeny.wms.R;
import com.teeny.wms.util.Validator;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see TableViewPagerFragment
 * @since 2018/4/2
 */
public class TableViewPagerFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected View onCreateHolderView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_table_view_pager_layout, container, false);
    }

    @Override
    protected void onInitialize() {
        mTabLayout = findView(R.id.tab_layout);
        mViewPager = findView(R.id.view_pager);
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

    public int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }
}
