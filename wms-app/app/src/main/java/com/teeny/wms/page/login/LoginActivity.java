package com.teeny.wms.page.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseActivity;
import com.teeny.wms.page.login.fragment.LoginFragment;
import com.teeny.wms.page.login.fragment.RegisterFragment;
import com.teeny.wms.util.Validator;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see LoginActivity
 * @since 2017/7/8
 */

public class LoginActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private static final String TAG_LOGIN = "login";
    private static final String TAG_REGISTER = "register";

    private static final int REQUEST_CODE_SYSTEM_CONFIG = 0x01;

    private Button mButton;
    private FragmentManager mFragmentManager;
    private SparseArrayCompat<Fragment> mFragmentHolder;
    private String mFragmentTag;
    private int mStatus = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_login_layout);
        initView();
    }

    private void initView() {
        registerEventBus();

        mButton = (Button) findViewById(R.id.login_switch_button);

        mFragmentHolder = new SparseArrayCompat<>();
        mFragmentManager = getSupportFragmentManager();
        switchFragment();
    }

    @Override
    protected void onDestroy() {
        unregisterEventBus();
        super.onDestroy();
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.login_switch_button:
//                MainActivity.startActivity(getContext());
//                功能暂时未开放
//                updateStatusAndText();
//                switchFragment();
                break;
            case R.id.login_system_config_button:
                ServerConfigActivity.startActivityForResult(this, REQUEST_CODE_SYSTEM_CONFIG);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_SYSTEM_CONFIG:

                break;
        }
    }

    private void updateStatusAndText() {
        mStatus++;
        mStatus = mStatus % 2;
        switch (mStatus) {
            case 0:
                mButton.setText(R.string.text_device_register);
                mButton.setCompoundDrawablesWithIntrinsicBounds(ResourcesCompat.getDrawable(getResources(), R.drawable.icon_vector_device_register, null), null, null, null);
                break;
            case 1:
                mButton.setText(R.string.text_login);
                mButton.setCompoundDrawablesWithIntrinsicBounds(ResourcesCompat.getDrawable(getResources(), R.drawable.icon_vector_account, null), null, null, null);
                break;
        }
    }

    /**
     * select fragment.
     */
    private void switchFragment() {
        FragmentTransaction ft = mFragmentManager.beginTransaction();

        if (Validator.isNotNull(mFragmentTag)) {
            Fragment fragment = mFragmentManager.findFragmentByTag(mFragmentTag);
            ft.hide(fragment);
        }

        mFragmentTag = String.valueOf(mStatus);
        Fragment target = mFragmentManager.findFragmentByTag(mFragmentTag);
        if (target == null) {
            target = getFragmentById(mStatus);
            ft.add(R.id.login_container_layout, target, mFragmentTag);
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
            case 0:
                fragment = LoginFragment.newInstance();
                break;
            case 1:
                fragment = RegisterFragment.newInstance();
                break;
        }
        mFragmentHolder.put(itemId, fragment);
        return fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccess(LoginSuccess msg) {
        finish();
    }

    public static class LoginSuccess {

    }
}
