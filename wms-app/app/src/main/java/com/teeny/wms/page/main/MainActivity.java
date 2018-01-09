package com.teeny.wms.page.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.SparseArrayCompat;
import android.view.MenuItem;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseActivity;
import com.teeny.wms.page.login.LoginActivity;
import com.teeny.wms.page.main.fragment.MeFragment;
import com.teeny.wms.page.main.fragment.MiningSalesFragment;
import com.teeny.wms.page.main.fragment.StatisticsFragment;
import com.teeny.wms.page.main.fragment.WarehouseFragment;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.service.PollingHelper;
import com.teeny.wms.util.Validator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    private FragmentManager mFragmentManager;
    private SparseArrayCompat<Fragment> mFragmentHolder;
    private String mFragmentTag;
    private PollingHelper mPollingHelper;

    private long mLastPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        mPollingHelper = new PollingHelper(this);
        mPollingHelper.startPollingService();

        registerEventBus();
        initView();
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();//获取第一次按键时间
        if (currentTime - mLastPressedTime > 2000) {
            Toaster.showToast("再按一次退出程序");
            mLastPressedTime = currentTime;
        } else {
            getAppManager().exit();
        }
    }

    @Override
    protected void onDestroy() {
        mPollingHelper.stopPollingService();
        unregisterEventBus();
        super.onDestroy();
    }

    private void initView() {
        mFragmentHolder = new SparseArrayCompat<>();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();
        selectFragment(R.id.item_main_navigation_warehouse);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        selectFragment(itemId);
        return true;
    }

    /**
     * select fragment.
     *
     * @param itemId the id of the menu item.
     */
    private void selectFragment(final int itemId) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (Validator.isNotNull(mFragmentTag)) {
            Fragment fragment = mFragmentManager.findFragmentByTag(mFragmentTag);
            ft.hide(fragment);
        }

        mFragmentTag = String.valueOf(itemId);
        Fragment target = mFragmentManager.findFragmentByTag(mFragmentTag);
        if (target == null) {
            target = getFragmentById(itemId);
            ft.add(R.id.main_container, target, mFragmentTag);
        } else {
            ft.show(target);
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * get the fragment from holder by the special id of menu item.
     * if it is not exist.create and save it.
     *
     * @param itemId the id of the menu item.
     * @return fragment
     */
    private Fragment getFragmentById(final int itemId) {
        Fragment fragment = mFragmentHolder.get(itemId);
        if (Validator.isNotNull(fragment)) {
            return fragment;
        }
        switch (itemId) {
            case R.id.item_main_navigation_warehouse:
                fragment = WarehouseFragment.newInstance();
                break;
            case R.id.item_main_navigation_mining_sales:
                fragment = MiningSalesFragment.newInstance();
                break;
            case R.id.item_main_navigation_text_statistics:
                fragment = StatisticsFragment.newInstance();
                break;
            case R.id.item_main_navigation_me:
                fragment = MeFragment.newInstance();
                break;
        }
        mFragmentHolder.put(itemId, fragment);
        return fragment;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogout(LogoutFlag flag) {
        LoginActivity.startActivity(this);
        finish();
    }

    public static final class LogoutFlag {
    }
}
