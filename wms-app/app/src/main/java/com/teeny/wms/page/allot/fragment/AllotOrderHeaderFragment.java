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
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.AllotOrderService;
import com.teeny.wms.model.AllotGoodsEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.page.allot.helper.AllotOrderHelper;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.WindowUtils;

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

    public static AllotOrderHeaderFragment newInstance() {
        return new AllotOrderHeaderFragment();
    }

    private AppCompatEditText mLocationView;
    private AppCompatEditText mGoodsView;
    private EditText mFocusView;
    private ScannerHelper mScannerHelper;

    private AllotOrderService mService;
    private AllotOrderHelper mHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerHelper = ScannerHelper.getInstance();
        mScannerHelper.openScanner(getContext(), this::handleResult);

        mService = NetServiceManager.getInstance().getService(AllotOrderService.class);
        mHelper = new AllotOrderHelper();
    }

    private void handleResult(String msg) {
        if (mFocusView != null) {
            mFocusView.setText(msg);
        } else {
            Toaster.showToast("当前没有焦点.");
        }
    }

    @Override
    public void onDestroy() {
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
        if (Validator.isEmpty(location) && Validator.isEmpty(goods)){
            Toaster.showToast("货位和商品不能同时为空.");
            return;
        }
        Flowable<ResponseEntity<List<AllotGoodsEntity>>> flowable = mService.getAllotGoodsList(location, goods);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<AllotGoodsEntity>>(this) {
            @Override
            public void doNext(List<AllotGoodsEntity> data) {
                mHelper.setItems(data);
            }

            @Override
            public void doComplete() {

            }
        });
    }
}
