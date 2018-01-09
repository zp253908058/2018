package com.teeny.wms.page.sku;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.SKUService;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.SKUEntity;
import com.teeny.wms.page.sku.adapter.SKUAdapter;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.CollectionsUtils;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.WindowUtils;
import com.teeny.wms.widget.KeyValueTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description: 单品盘点
 *
 * @author zp
 * @version 1.0
 * @see SKUCheckActivity
 * @since 2017/7/16
 */

public class SKUCheckActivity extends ToolbarActivity implements RecyclerViewTouchListener.OnItemClickListener {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SKUCheckActivity.class);
        context.startActivity(intent);
    }

    private static final int INVALID_POSITION = -1;

    private EditText mGoodsTextView;
    private EditText mAllocationTextView;
    private EditText mFocusView;

    private KeyValueTextView mCounterView;
    private SKUAdapter mAdapter;

    private int mClickPosition = INVALID_POSITION;

    private SKUService mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sku_check_layout);

        mScannerHelper.openScanner(this, this::handleResult);
        registerEventBus();
        initView();
    }

    private void handleResult(String result) {
        if (mFocusView != null) {
            mFocusView.setText(result);
            obtainData();
        } else {
            Toaster.showToast("当前没有焦点.");
        }
    }

    @Override
    protected void onDestroy() {
        mScannerHelper.unregisterReceiver(this);
        unregisterEventBus();
        super.onDestroy();
    }

    private void initView() {
        mGoodsTextView = (EditText) findViewById(R.id.sku_check_goods);
        mGoodsTextView.setOnFocusChangeListener(this::onFocusChanged);
        mGoodsTextView.setOnEditorActionListener(this::onEditorAction);
        mAllocationTextView = (EditText) findViewById(R.id.sku_check_goods_allocation);
        mAllocationTextView.setOnFocusChangeListener(this::onFocusChanged);
        mAllocationTextView.setOnEditorActionListener(this::onEditorAction);

        mCounterView = (KeyValueTextView) findViewById(R.id.sku_check_counter);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new SKUAdapter(null);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(manager);
        VerticalDecoration decoration = new VerticalDecoration(this.getContext());
        decoration.setHeight(this.getContext().getResources().getDimensionPixelSize(R.dimen.dp_16));
        decoration.setNeedDraw(false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this.getContext(), this));

        mService = NetServiceManager.getInstance().getService(SKUService.class);
    }

    private void onFocusChanged(View view, boolean hasFocus) {
        if (hasFocus) {
            this.mFocusView = (EditText) view;
        }
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            //执行搜索的代码
            obtainData();
            WindowUtils.hideInputSoft(v);
            return true;
        }
        return false;
    }

    private void obtainData() {
        String location = mAllocationTextView.getText().toString();
        String barcode = mGoodsTextView.getText().toString();
        if (Validator.isEmpty(location) && Validator.isEmpty(barcode)) {
            Toaster.showToast("货位和商品不能同时为空。");
            return;
        }
        Flowable<ResponseEntity<List<SKUEntity>>> flowable = mService.getList(location, barcode);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<SKUEntity>>(this) {
            @Override
            public void doNext(List<SKUEntity> data) {
                mAdapter.setItems(data);
                mCounterView.setValue(String.valueOf(CollectionsUtils.sizeOf(data)));
            }

            @Override
            public void doComplete() {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        mClickPosition = position;
        SKUDetailActivity.startActivity(this, mAdapter.getItem(position));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                SKUAddActivity.startActivity(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChanged(DataChangedFlag flag) {
        SKUEntity entity = mAdapter.getItem(mClickPosition);
        entity.setQuantity(flag.amount);
        mAdapter.notifyItemChanged(mClickPosition);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataAdded(DataAddedFlag flag) {
        String location = mAllocationTextView.getText().toString();
        String barcode = mGoodsTextView.getText().toString();
        if (Validator.isNotEmpty(location) || Validator.isNotEmpty(barcode)){
            obtainData();
        }
    }

    public static final class DataChangedFlag {
        public int amount;
    }

    public static final class DataAddedFlag {
    }
}
