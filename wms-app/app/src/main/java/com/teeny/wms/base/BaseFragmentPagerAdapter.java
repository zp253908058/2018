package com.teeny.wms.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.teeny.wms.util.CollectionsUtils;
import com.teeny.wms.util.Preconditions;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BaseFragmentPagerAdapter
 * @since 2017/7/15
 */

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    public interface Callback {
        Fragment getItem(int position);

        int getCount();

        CharSequence getPageTitle(int position);
    }

    private Callback mCallback;

    public BaseFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragments, String[] titles) {
        super(fm);
        mCallback = new CallbackImpl(fragments, titles);
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, Callback callback) {
        super(fm);
        mCallback = Preconditions.checkNotNull(callback);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Override
    public Fragment getItem(int position) {
        return mCallback.getItem(position);
    }

    @Override
    public int getCount() {
        return mCallback.getCount();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCallback.getPageTitle(position);
    }

    private static final class CallbackImpl implements Callback {
        private List<BaseFragment> mFragments;
        private String[] mTitles;

        private CallbackImpl(List<BaseFragment> fragments, String[] titles) {
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return CollectionsUtils.get(mFragments, position);
        }

        @Override
        public int getCount() {
            return CollectionsUtils.sizeOf(mFragments);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}

