package com.teeny.wms.page.login.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseFragment;
import com.teeny.wms.datasouce.local.cache.ServerConfigManager;
import com.teeny.wms.datasouce.local.cache.UserManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.model.AccountSetEntity;
import com.teeny.wms.model.UserEntity;
import com.teeny.wms.page.login.LoginActivity;
import com.teeny.wms.page.login.helper.LoginHelper;
import com.teeny.wms.page.main.MainActivity;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.WindowUtils;
import com.teeny.wms.util.log.Logger;
import com.teeny.wms.view.ProgressView;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.FlowableSubscriber;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see LoginFragment
 * @since 2017/7/15
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener, ProgressView, DialogInterface.OnClickListener {

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    private ServerConfigManager mServerConfigManager;

    private TextView mAccountSetView;
    private AccountSetEntity mAccountSet;
    private TextView mUsernameView;
    private TextView mPasswordView;
    private AlertDialog mAlertDialog;
    private LoginHelper mLoginHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mServerConfigManager = ServerConfigManager.getInstance();
    }

    @Override
    protected View onCreateHolderView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_login_layout, container, false);
    }

    @Override
    protected void onInitialize() {
        findView(R.id.login_button).setOnClickListener(this);
        mAccountSetView = findView(R.id.login_account_set_text_view);
        mAccountSetView.setOnClickListener(this);
        mUsernameView = findView(R.id.login_account_text_view);
        mPasswordView = findView(R.id.login_password_text_view);
        mPasswordView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                //执行登陆的代码
                login();
                WindowUtils.hideInputSoft(v);
                return true;
            }
            return false;
        });
        mLoginHelper = new LoginHelper();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAlertDialog = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.login_button:
                login();
                break;
            case R.id.login_account_set_text_view:
                showAccountSets();
                break;
        }
    }

    private void login() {
        if (mServerConfigManager.get().isEmpty()) {
            Toaster.showToast(R.string.prompt_server_config_first);
            return;
        }
        if (Validator.isNull(mAccountSet)) {
            Toaster.showToast(R.string.prompt_select_account_set_first);
            return;
        }
        String username = mUsernameView.getText().toString();
        if (Validator.isEmpty(username)) {
            Toaster.showToast(getString(R.string.prompt_input_account));
            mUsernameView.requestFocus();
            return;
        }
        String password = mPasswordView.getText().toString();
        if (Validator.isEmpty(password)) {
            Toaster.showToast(getString(R.string.prompt_input_password));
            mPasswordView.requestFocus();
            return;
        }
        mLoginHelper.login(mAccountSet.getDatabaseName(), username, password, new FlowableSubscriber<UserEntity>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
                showProgressDialog();
            }

            @Override
            public void onNext(UserEntity data) {
                if (Validator.isNull(data) || Validator.isEmpty(data.getRefreshToken())) {
                    onError(new Throwable("账号异常,请检查是否多设备登录。"));
                    return;
                }
                UserManager manager = UserManager.getInstance();
                data.setAccountSetName(mAccountSet.getAccountSetName());
                data.setDatabaseName(mAccountSet.getDatabaseName());
                manager.set(data).save();
                MainActivity.startActivity(getContext());
                EventBus.getDefault().post(new LoginActivity.LoginSuccess());
            }

            @Override
            public void onError(Throwable t) {
                dismissProgressDialog();
                Toaster.showToast(t.getMessage());
                Logger.e(t, t.getMessage());
            }

            @Override
            public void onComplete() {
                dismissProgressDialog();
            }
        });
    }

    private void showAccountSets() {
        if (mAlertDialog != null) {
            mAlertDialog.show();
            return;
        }

        if (mServerConfigManager.get().isEmpty()) {
            Toaster.showToast(R.string.prompt_server_config_first);
            return;
        }

        mLoginHelper.getAccountSets(new ResponseSubscriber<List<AccountSetEntity>>(this) {
            @Override
            public void doNext(List<AccountSetEntity> data) {
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("服务器没有账套.");
                }
                mAlertDialog = DialogFactory.createSingleChoiceDialog(getContext(), getString(R.string.text_select_account_set), data.toArray(), LoginFragment.this);
            }

            @Override
            public void doComplete() {
                mAlertDialog.show();
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        mAccountSet = (AccountSetEntity) mAlertDialog.getListView().getAdapter().getItem(which);
        mAccountSetView.setText(mAccountSet.getAccountSetName());
        dialog.dismiss();
    }
}
