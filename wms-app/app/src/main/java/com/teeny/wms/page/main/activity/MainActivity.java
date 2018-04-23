package com.teeny.wms.page.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseActivity;
import com.teeny.wms.datasouce.local.cache.UserManager;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.HomeService;
import com.teeny.wms.model.ResponseEntity;
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

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mFragmentManager;
    private SparseArrayCompat<Fragment> mFragmentHolder;
    private String mFragmentTag;
    private PollingHelper mPollingHelper;

    private long mLastPressedTime = 0;

    private DrawerLayout mDrawerLayout;

    private TextView mUserName;
    private TextView mWarehouse;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);

        registerEventBus();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.text_navigation_drawer_open, R.string.text_navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.main_navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationItemSelectedListener());

        View view = navigationView.getHeaderView(0);
        mUserName = view.findViewById(R.id.nav_header_user_name);
        mWarehouse = view.findViewById(R.id.nav_header_warehouse);


        mPollingHelper = new PollingHelper(this);
        mPollingHelper.startPollingService();

        initView();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return;
        }
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
        obtainUsername();
    }

    private void obtainUsername() {
        HomeService homeService = NetServiceManager.getInstance().getService(HomeService.class);
        Flowable<ResponseEntity<String>> flowable = homeService.getUsername();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<String>() {
            @Override
            public void doNext(String data) {
                mUserName.setText(data);
            }

            @Override
            public void doComplete() {

            }
        });
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

    private class NavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            int id = item.getItemId();
            switch (id) {
                case R.id.nav_switch_warehouse:
                    WarehouseSwitchActivity.startActivity(MainActivity.this);
                    break;
                case R.id.nav_refresh_time:
                    RefreshTimeActivity.startActivity(MainActivity.this);
                    break;
                case R.id.nav_logout:
                    logout();
                    break;
            }
            return true;
        }
    }

    private void logout() {
        UserManager.getInstance().clear();
        LoginActivity.startActivity(this);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWarehouseSelected(WarehouseFlag flag) {
        mWarehouse.setText(flag.getWarehouse());
    }

    public static final class WarehouseFlag {
        private String warehouse;

        public WarehouseFlag(String warehouse) {
            this.warehouse = warehouse;
        }

        public String getWarehouse() {
            return warehouse;
        }

        public void setWarehouse(String warehouse) {
            this.warehouse = warehouse;
        }
    }
}
