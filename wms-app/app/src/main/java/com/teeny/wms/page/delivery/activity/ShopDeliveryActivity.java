package com.teeny.wms.page.delivery.activity;

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
import com.teeny.wms.datasouce.net.service.ShopDeliveryService;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.ShopDeliveryRequestEntity;
import com.teeny.wms.page.allot.AllotOrderAddActivity;
import com.teeny.wms.page.delivery.fragment.ShopDeliveryFragment;
import com.teeny.wms.page.delivery.fragment.ShopDeliveryHeaderFragment;
import com.teeny.wms.page.delivery.helper.ShopDeliveryHelper;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.log.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopDeliveryActivity
 * @since 2018/1/18
 */

public class ShopDeliveryActivity extends TableViewPagerActivity implements BaseFragmentPagerAdapter.Callback, ViewPager.OnPageChangeListener {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ShopDeliveryActivity.class);
        context.startActivity(intent);
    }

    private String[] mTitles;
    private SparseArrayCompat<Fragment> mFragmentHolder = new SparseArrayCompat<>();

    private ShopDeliveryHeaderFragment mHeaderFragment;

    private AlertDialog mSubmitDialog;
    private ShopDeliveryHelper mHelper;

    private ShopDeliveryService mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupHeader();
        mTitles = getResources().getStringArray(R.array.delivery);
        setupViewPager(new BaseFragmentPagerAdapter(getSupportFragmentManager(), this), this);

        registerEventBus();

        mSubmitDialog = DialogFactory.createAlertDialog(this, getString(R.string.prompt_complete_all), this::onSubmit);
        mService = NetServiceManager.getInstance().getService(ShopDeliveryService.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

    @Override
    protected boolean onLongClick(View view) {
        Toaster.showToast("全部完成");
        return true;
    }

    @Override
    protected void onClick(View view) {
        complete();
    }

    private void complete() {
        if (mHelper == null) {
            return;
        }
        List<Integer> ids = mHelper.getAchievableIds();
        if (Validator.isEmpty(ids)) {
            Toaster.showToast("没有可以完成的商品.");
            return;
        }
        if (ids.size() >= 30) {
            Toaster.showToast("当前完成条数过多,请到PC端操作.");
            return;
        }
        if (ids.size() > 1) {
            mSubmitDialog.show();
        } else {
            submit();
        }
    }

    private void onSubmit(DialogInterface dialog, int which) {
        submit();
    }

    private void submit() {
        List<Integer> ids = mHelper.getAchievableIds();
        if (Validator.isEmpty(ids)) {
            Toaster.showToast("没有可完成数据");
            return;
        }
        ShopDeliveryRequestEntity entity = new ShopDeliveryRequestEntity();
        entity.setId(mHelper.getBillId());
        entity.setIds(ids);
        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.complete(entity);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                Toaster.showToast("全部完成操作成功.");
            }

            @Override
            public void doComplete() {
                mHelper.reverseAllStatus();
            }
        });
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

    private ShopDeliveryHeaderFragment createHeaderFragment() {
        return ShopDeliveryHeaderFragment.newInstance();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mHelper != null) {
            setCountValue(mHelper.getNumber(position));
        } else {
            Logger.e("mHelper == null");
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
        return ShopDeliveryFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNumberChanged(ShopDeliveryHelper helper) {
        mHelper = helper;
        setCountValue(mHelper.getNumber(getCurrentItem()));
    }
}
