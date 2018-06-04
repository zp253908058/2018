package com.teeny.wms.page.delivery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.teeny.wms.R;
import com.teeny.wms.app.ScannerHelper;
import com.teeny.wms.base.BaseFragment;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.ShopDeliveryService;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.ShopDeliveryGoodsEntity;
import com.teeny.wms.page.common.adapter.SimpleAdapter;
import com.teeny.wms.page.delivery.helper.ShopDeliveryHelper;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;

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
 * @see ShopDeliveryHeaderFragment
 * @since 2018/1/18
 */

public class ShopDeliveryHeaderFragment extends BaseFragment {

    public static ShopDeliveryHeaderFragment newInstance() {
        return new ShopDeliveryHeaderFragment();
    }

    private AutoCompleteTextView mOrderTextView;    //库区
    private SimpleAdapter<KeyValueEntity> mOrderAdapter;

    private EditText mFocusView;
    private ScannerHelper mScannerHelper;

    private ShopDeliveryService mService;

    private KeyValueEntity mSelectedOrder;

    private EventBus mEventBus = EventBus.getDefault();

    private ShopDeliveryHelper mHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerHelper = ScannerHelper.getInstance();
        mScannerHelper.openScanner(getContext(), this::handleResult);
        mService = NetServiceManager.getInstance().getService(ShopDeliveryService.class);
        mEventBus.register(this);
        mHelper = new ShopDeliveryHelper();
    }

    private void handleResult(String msg) {
        if (mFocusView != null) {
            mFocusView.setText(msg);
            int count = mOrderAdapter.getCount();
            switch (count) {
                case 0:
                    Toaster.showToast("找不到该单据:" + msg);
                    break;
                case 1:
                    mSelectedOrder = mOrderAdapter.getItem(0);
                    obtainGoods();
                    break;
                default:
                    Toaster.showToast("找到多个类似单据,请手动选择。");
                    break;
            }
        } else {
            Toaster.showToast("当前没有焦点.");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerHelper.unregisterReceiver(getContext());
        mEventBus.unregister(this);
    }

    @Override
    protected View onCreateHolderView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_shop_delivery_layout, container, false);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        mOrderTextView = findView(R.id.shop_delivery_bill_number);
        mOrderTextView.setOnFocusChangeListener(this::onFocusChanged);
        mOrderTextView.setOnItemClickListener((parent, view, position, id) -> {
            KeyValueEntity orderEntity = mOrderAdapter.getItem(position);
            if (mSelectedOrder == orderEntity) {
                return;
            }
            mSelectedOrder = orderEntity;
            if (mSelectedOrder == null) {
                return;
            }
            obtainGoods();
        });
        mOrderAdapter = new SimpleAdapter<>(getContext());
        mOrderTextView.setAdapter(mOrderAdapter);
        mOrderTextView.setOnClickListener(v -> {
            if (!mOrderTextView.isPopupShowing()) {
                mOrderTextView.showDropDown();
            }
        });

        EditText goodsTextView = findView(R.id.shop_delivery_goods);
        goodsTextView.setOnFocusChangeListener(this::onFocusChanged);
        goodsTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mHelper.setGoodsCode(s.toString());
            }
        });

        obtainOrderNumber();
    }

    private void onFocusChanged(View view, boolean hasFocus) {
        if (hasFocus) {
            mFocusView = (EditText) view;
        }
    }

    private void obtainOrderNumber() {
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = mService.getOrderList();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<KeyValueEntity>>(this) {
            @Override
            public void doNext(List<KeyValueEntity> data) {
                mOrderAdapter.clear();
                mOrderAdapter.addAll(data);
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("获取到单据为空.");
                }
            }

            @Override
            public void doComplete() {

            }
        });
    }

    private void obtainGoods() {
        if (mSelectedOrder == null) {
            return;
        }
        final int id = mSelectedOrder.getKey();
        Flowable<ResponseEntity<List<ShopDeliveryGoodsEntity>>> flowable = mService.getDeliveryGoodsList(id);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<ShopDeliveryGoodsEntity>>(this) {
            @Override
            public void doNext(List<ShopDeliveryGoodsEntity> data) {
                mHelper.setDataList(id, data);
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("获取到商品为空.");
                }
            }

            @Override
            public void doComplete() {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChanged(DataChangedObserver observer) {
        obtainGoods();
    }

    public static class DataChangedObserver {

    }
}
