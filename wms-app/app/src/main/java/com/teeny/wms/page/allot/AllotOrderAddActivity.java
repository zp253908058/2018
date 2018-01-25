package com.teeny.wms.page.allot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseFragmentPagerAdapter;
import com.teeny.wms.base.TableViewPagerActivity;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.AllotOrderService;
import com.teeny.wms.model.AllotGoodsEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.page.allot.fragment.AllotGoodsQueryFragment;
import com.teeny.wms.page.allot.fragment.AllotGoodsSelectedFragment;
import com.teeny.wms.page.allot.fragment.AllotOrderHeaderFragment;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotOrderAddActivity
 * @since 2018/1/4
 */

public class AllotOrderAddActivity extends TableViewPagerActivity implements BaseFragmentPagerAdapter.Callback, ViewPager.OnPageChangeListener, DialogInterface.OnClickListener {

    private static final String KEY_GOODS = "goods";

    public static void startActivity(Context context) {
        startActivity(context, "");
    }

    public static void startActivity(Context context, String goods) {
        Intent intent = new Intent();
        intent.setClass(context, AllotOrderAddActivity.class);
        if (Validator.isNotEmpty(goods)) {
            intent.putExtra(KEY_GOODS, goods);
        }
        context.startActivity(intent);
    }

    private String[] mTitles;
    private SparseArrayCompat<Fragment> mFragmentHolder = new SparseArrayCompat<>();

    private Fragment mHeaderFragment;
    private AlertDialog mFinishDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hideFloatingActionButton();
        hideCounterView();
        Intent intent = getIntent();
        String goods = intent.hasExtra(KEY_GOODS) ? intent.getStringExtra(KEY_GOODS) : "";
        setupHeader(goods);

        mTitles = getResources().getStringArray(R.array.allot_tab);
        setupViewPager(new BaseFragmentPagerAdapter(getSupportFragmentManager(), this), this);

        mFinishDialog = DialogFactory.createAlertDialog(getContext(), getString(R.string.prompt_allot_add_finish_bill), this);
    }

    private void setupHeader(String goods) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (mHeaderFragment == null) {
            mHeaderFragment = createHeaderFragment(goods);
        }
        ft.replace(getHeaderLayoutId(), mHeaderFragment);
        ft.commitAllowingStateLoss();
    }

    private Fragment createHeaderFragment(String goods) {
        return AllotOrderHeaderFragment.newInstance(goods);
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
        if (position == 0) {
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            hideFloatingActionButton();
        } else {
            showFloatingActionButton();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.table_view_pager_action_button:
                mFinishDialog.show();
                break;
        }
    }


    private void finishBill() {
        AllotOrderService service = NetServiceManager.getInstance().getService(AllotOrderService.class);
        Flowable<ResponseEntity<EmptyEntity>> flowable = service.finish();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                Toaster.showToast("已完成.");
            }

            @Override
            public void doComplete() {
                getEventBus().post(new AllotGoodsSelectedFragment.CompleteFlag());
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        finishBill();
    }
}