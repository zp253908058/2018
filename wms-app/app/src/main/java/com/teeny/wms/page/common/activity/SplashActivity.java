package com.teeny.wms.page.common.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseActivity;
import com.teeny.wms.datasouce.local.cache.ServerConfigManager;
import com.teeny.wms.datasouce.local.cache.UserManager;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.model.ServerConfigEntity;
import com.teeny.wms.page.login.LoginActivity;
import com.teeny.wms.page.main.MainActivity;
import com.teeny.wms.util.SystemUtils;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SplashActivity
 * @since 2017/7/8
 */

public class SplashActivity extends BaseActivity implements Runnable {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final long DEFAULT_DURATION = 2000;

    private Handler mHandler = new Handler();
    private boolean mIsTokenAvailable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        if(Build.VERSION.SDK_INT<16){
//            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }else{
//
//        }

        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN //hide statusBar
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; //hide navigationBar
        getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        super.onCreate(savedInstanceState);

        configServer();

        mIsTokenAvailable = !UserManager.getInstance().get().isEmpty();
        setContentView(R.layout.activity_splash_layout);

        initView();
    }

    private void initView() {
        TextView versionCode = (TextView) findViewById(R.id.splash_version_code);
        versionCode.setText(SystemUtils.getVersionName(this));
    }

    private void configServer() {
        ServerConfigEntity entity = ServerConfigManager.getInstance().get();
        boolean isServerConfigAvailable = !entity.isEmpty();
        if (isServerConfigAvailable) {
            NetServiceManager manager = NetServiceManager.getInstance();
            manager.initialize(entity.getServerAddress(), entity.getPort());
        }
    }


    @Override
    public void run() {
        if (mIsTokenAvailable) {
            MainActivity.startActivity(this);
        } else {
            LoginActivity.startActivity(this);
        }
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mHandler != null) {
            mHandler.removeCallbacks(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(this, 2000);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}