package com.teeny.wms.page.login.helper;

import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.service.LoginService;
import com.teeny.wms.model.AccountSetEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.UserEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see LoginHelper
 * @since 2017/8/5
 */

public class LoginHelper {

    private LoginService mLoginService;

    public LoginHelper() {

    }

    public void login(String dbName, String username, String password, FlowableSubscriber<UserEntity> subscriber) {
        synchronizeLoginService();
        Flowable<UserEntity> flowable = mLoginService.login(username + "@" + dbName, password, "wms", "password");
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    public void getAccountSets(FlowableSubscriber<ResponseEntity<List<AccountSetEntity>>> subscriber){
        synchronizeLoginService();
        Flowable<ResponseEntity<List<AccountSetEntity>>> flowable = mLoginService.getAccountSets();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    private void synchronizeLoginService() {
        if (mLoginService == null) {
            mLoginService = NetServiceManager.getInstance().getService(LoginService.class);
        }
    }
}
