package com.teeny.wms.page.allot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.app.ScannerHelper;
import com.teeny.wms.base.BaseFragment;
import com.teeny.wms.datasouce.local.Preferences;
import com.teeny.wms.datasouce.local.SharedPreferencesManager;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.AllotOrderService;
import com.teeny.wms.model.AllotGoodsEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.page.allot.AllotOrderFilterActivity;
import com.teeny.wms.page.allot.helper.AllotOrderHelper;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.WindowUtils;
import com.teeny.wms.util.log.Logger;

import org.greenrobot.eventbus.EventBus;
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
 * @see AllotOrderHeaderFragment
 * @since 2018/1/9
 */

public class AllotOrderHeaderFragment extends BaseFragment {
    private static final String KEY_GOODS = "goods";

    public static AllotOrderHeaderFragment newInstance(String goods) {
        AllotOrderHeaderFragment fragment = new AllotOrderHeaderFragment();
        Bundle bundle = new Bundle();
        if (Validator.isNotEmpty(goods)) {
            bundle.putString(KEY_GOODS, goods);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    private AppCompatEditText mLocationView;
    private AppCompatEditText mGoodsView;
    private EditText mFocusView;
    private ScannerHelper mScannerHelper;

    private AllotOrderService mService;
    private AllotOrderHelper mHelper;

    private int mWarehouseId = 0;
    private int mRepositoryId = 0;
    private int mAreaId = 0;

    private boolean mChanged;

    private EventBus mEventBus;

    private String mGoods;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoods = getArguments().getString(KEY_GOODS);
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
        mScannerHelper = ScannerHelper.getInstance();
        mScannerHelper.openScanner(getContext(), this::handleResult);

        mService = NetServiceManager.getInstance().getService(AllotOrderService.class);

        Preferences preferences = SharedPreferencesManager.getInstance().getAllotOrderFilterPreferences();
        mWarehouseId = preferences.getInt(AllotOrderFilterActivity.KEY_WAREHOUSE_ID, 0);
        mRepositoryId = preferences.getInt(AllotOrderFilterActivity.KEY_REPOSITORY_ID, 0);
        mAreaId = preferences.getInt(AllotOrderFilterActivity.KEY_AREA_ID, 0);

        mHelper = new AllotOrderHelper();
    }

    private void handleResult(String msg) {
        if (mFocusView != null) {
            mFocusView.setText(msg);
            mFocusView.requestFocus();
            mFocusView.requestFocusFromTouch();
        } else {
            Toaster.showToast("当前没有焦点.");
        }
    }

    @Override
    public void onDestroy() {
        mEventBus.unregister(this);
        super.onDestroy();
        mScannerHelper.unregisterReceiver(getContext());
    }

    @Override
    protected View onCreateHolderView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_allot_order_header_layout, container, false);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        mLocationView = findView(R.id.allot_order_header_location);
        mLocationView.setOnFocusChangeListener(this::onFocusChanged);
        mLocationView.setOnEditorActionListener(this::onEditorAction);

        mGoodsView = findView(R.id.allot_order_header_goods);
        mGoodsView.setOnFocusChangeListener(this::onFocusChanged);
        mGoodsView.setOnEditorActionListener(this::onEditorAction);
        if (Validator.isNotEmpty(mGoods)) {
            mGoodsView.setText(mGoods);
            search();
        }
    }

    private void onFocusChanged(View view, boolean hasFocus) {
        if (hasFocus) {
            mFocusView = (EditText) view;
        }
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            //执行搜索的代码
            search();
            WindowUtils.hideInputSoft(v);
            return true;
        }
        return false;
    }

    private void search() {
        String location = mLocationView.getText().toString();
        String goods = mGoodsView.getText().toString();
        if (Validator.isEmpty(location) && Validator.isEmpty(goods)) {
            if (mChanged) {
                return;
            }
            Toaster.showToast("货位和商品不能同时为空.");
            return;
        }
        Flowable<ResponseEntity<List<AllotGoodsEntity>>> flowable = mService.getAllotGoodsList(location, goods, mWarehouseId, mRepositoryId, mAreaId);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<AllotGoodsEntity>>(this) {
            @Override
            public void doNext(List<AllotGoodsEntity> data) {
                mHelper.setItems(data);
            }

            @Override
            public void doComplete() {
                mChanged = false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mChanged) {
            search();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFiltered(AllotOrderFilterActivity.FilterFlag flag) {
        mWarehouseId = flag.getWarehouseId();
        mRepositoryId = flag.getRepositoryId();
        mAreaId = flag.getAreaId();
        mChanged = true;
    }
}
