package com.teeny.wms.page.barcode;

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
import com.teeny.wms.datasouce.net.service.BarcodeService;
import com.teeny.wms.model.BarcodeGoodsEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.page.barcode.adapter.BarcodeGoodsAdapter;
import com.teeny.wms.page.sku.SKUAddActivity;
import com.teeny.wms.page.sku.SKUCheckActivity;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.WindowUtils;

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
 * @see BarcodeCollectActivity
 * @since 2018/1/4
 */

public class BarcodeCollectActivity extends ToolbarActivity implements RecyclerViewTouchListener.OnItemClickListener {

    private static final String KEY_GOODS = "goods";

    public static void startActivity(Context context) {
        startActivity(context, null);
    }

    public static void startActivity(Context context, String goods) {
        Intent intent = new Intent();
        intent.setClass(context, BarcodeCollectActivity.class);
        if (Validator.isNotEmpty(goods)) {
            intent.putExtra(KEY_GOODS, goods);
        }
        context.startActivity(intent);
    }

    private EditText mGoodsTextView;
    private EditText mLocationTextView;
    private EditText mFocusView;

    private BarcodeGoodsAdapter mAdapter;

    private BarcodeService mService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_collect_layout);

        mScannerHelper.openScanner(this, this::handleResult);
        initView();
    }

    private void handleResult(String result) {
        if (mFocusView != null) {
            mFocusView.setText("");
            mFocusView.setText(result);
        } else {
            Toaster.showToast("当前没有焦点.");
        }
    }

    @Override
    protected void onDestroy() {
        mScannerHelper.unregisterReceiver(this);
        super.onDestroy();
    }

    private void initView() {

        Intent intent = getIntent();
        String goods = intent.hasExtra(KEY_GOODS) ? intent.getStringExtra(KEY_GOODS) : "";

        mGoodsTextView = (EditText) findViewById(R.id.barcode_collect_goods);
        mGoodsTextView.setText(goods);
        mGoodsTextView.setOnEditorActionListener(this::onEditorAction);
        mGoodsTextView.setOnFocusChangeListener(this::onFocusChanged);
        mLocationTextView = (EditText) findViewById(R.id.barcode_collect_allocation);
        mLocationTextView.setOnEditorActionListener(this::onEditorAction);
        mGoodsTextView.setOnFocusChangeListener(this::onFocusChanged);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new BarcodeGoodsAdapter(null);
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

        mService = NetServiceManager.getInstance().getService(BarcodeService.class);
        if (Validator.isNotEmpty(goods)) {
            obtainData();
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
            obtainData();
            WindowUtils.hideInputSoft(v);
            return true;
        }
        return false;
    }

    private void obtainData() {
        String location = mLocationTextView.getText().toString();
        String goods = mGoodsTextView.getText().toString();
        if (Validator.isEmpty(location) && Validator.isEmpty(goods)) {
            Toaster.showToast("货位和商品不能同时为空.");
            return;
        }
        Flowable<ResponseEntity<List<BarcodeGoodsEntity>>> flowable = mService.getList(location, goods);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<BarcodeGoodsEntity>>(this) {
            @Override
            public void doNext(List<BarcodeGoodsEntity> data) {
                mAdapter.setItems(data);
            }

            @Override
            public void doComplete() {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        BarcodeGoodsEntity entity = mAdapter.getItem(position);
        BarcodeAddActivity.startActivity(this, entity.getId(), entity.getBarcode());
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
                BarcodeAddActivity.startActivity(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
