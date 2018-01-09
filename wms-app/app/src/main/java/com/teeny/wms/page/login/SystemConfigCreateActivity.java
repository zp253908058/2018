package com.teeny.wms.page.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.dao.ServerConfigEntityDao;
import com.teeny.wms.datasouce.local.DatabaseManager;
import com.teeny.wms.datasouce.local.cache.ServerConfigManager;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.service.LoginService;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.ServerConfigEntity;
import com.teeny.wms.pop.Toaster;

import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SystemConfigCreateActivity
 * @since 2017/7/8
 */

public class SystemConfigCreateActivity extends ToolbarActivity {

    public static void startActivity(Activity context, int requestCode) {
        Intent intent = new Intent(context, SystemConfigCreateActivity.class);
        context.startActivityForResult(intent, requestCode);
    }

    private EditText mServiceNameView;
    private EditText mServiceAddressView;
    private EditText mPortView;

    private String mServiceName;
    private String mServiceAddress;
    private String mPort;

    private boolean mIsTestPassed = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_config_create_layout);

        initView();
    }

    private void initView() {
        mServiceNameView = (EditText) findViewById(R.id.system_config_create_service_name);
        mServiceAddressView = (EditText) findViewById(R.id.system_config_create_service_address);
        mPortView = (EditText) findViewById(R.id.system_config_create_port);
    }

    public void onClick(View view) {
        connectTest();
    }

    private void connectTest() {
        mServiceName = mServiceNameView.getText().toString();
        mServiceAddress = mServiceAddressView.getText().toString();
        mPort = mPortView.getText().toString();

        NetServiceManager manager = NetServiceManager.getInstance();
        manager.initialize(mServiceAddress, mPort);
        LoginService service = manager.getService(LoginService.class);
        Flowable<ResponseEntity<EmptyEntity>> flowable = service.test();
        flowable.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<ResponseEntity<EmptyEntity>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                        showProgressDialog();
                    }

                    @Override
                    public void onNext(ResponseEntity<EmptyEntity> data) {
                        Toaster.showToast(data.getMsg());
                        mIsTestPassed = true;
                    }

                    @Override
                    public void onError(Throwable t) {
                        dismissProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        dismissProgressDialog();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_text, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.save:
                save();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {
        if (!mIsTestPassed) {
            Toaster.showToast(R.string.prompt_connect_test_first);
            return;
        }
        ServerConfigEntity entity = new ServerConfigEntity();
        entity.setServerName(mServiceName);
        entity.setServerAddress(mServiceAddress);
        entity.setPort(mPort);
        ServerConfigEntityDao dao = DatabaseManager.getInstance().getDaoSession().getServerConfigEntityDao();
        dao.insert(entity);
        ServerConfigManager.getInstance().set(entity).save();
        getEventBus().post(entity);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
    }
}
