package com.teeny.wms.page.allot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.datasouce.local.Preferences;
import com.teeny.wms.datasouce.local.SharedPreferencesManager;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.AllotListService;
import com.teeny.wms.model.AllotListEntity;
import com.teeny.wms.model.DocumentEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.page.allot.adapter.AllotListAdapter;
import com.teeny.wms.page.allot.helper.AllotListHelper;
import com.teeny.wms.page.common.adapter.KeyValueListAdapter;
import com.teeny.wms.page.document.controller.DocumentHelper;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.WindowUtils;
import com.teeny.wms.util.log.Logger;

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
 * @see AllotListActivity
 * @since 2017/7/16
 */

public class AllotListActivity extends ToolbarActivity implements RecyclerViewTouchListener.OnItemClickListener {

    private static final String KEY_ENTITY = "entity";

    public static void startActivity(Context context, DocumentEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, AllotListActivity.class);
        if (Validator.isNotNull(entity)) {
            intent.putExtra(KEY_ENTITY, entity);
        }
        context.startActivity(intent);
    }

//    private static final int REQUEST_CODE_EDIT = 0x03;
//    private static final int REQUEST_CODE_FILTER = 0x04;

    private static final int INVALID_POSITION = -1;

    private AutoCompleteTextView mDocumentView;
    private KeyValueListAdapter mDocumentAdapter;
    private String mDocumentCode = "";

    private AutoCompleteTextView mGoodsView;
    private KeyValueListAdapter mGoodsAdapter;
    private String mGoodsCode = "";

    private EditText mExportView;
    private EditText mImportView;

    private AllotListAdapter mAdapter;
    private AllotListService mService;
    private int mWarehouseId = 0;
    private int mRepositoryId = 0;
    private AlertDialog mOptionDialog;
    private int mSelectPosition = INVALID_POSITION;

    private EditText mFocusView;

    private AllotListHelper mHelper;
    private DocumentEntity mDocumentEntity;

    private AlertDialog mSubmitDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allot_list_layout);

        registerEventBus();
        mScannerHelper.openScanner(this, this::handleResult);
        initView();
    }

    private void handleResult(String msg) {
        if (mFocusView != null) {
            mFocusView.setText(msg);
            int id = mFocusView.getId();
            switch (id) {
                case R.id.allot_list_document_scan:
                    mDocumentCode = msg;
                    obtainData();
                    mExportView.setText("");
                    mImportView.setText("");
                    break;
                case R.id.allot_list_goods_scan:
                    mGoodsCode = msg;
                    obtainData();
                    mExportView.setText("");
                    mImportView.setText("");
                    break;
            }
        } else {
            Toaster.showToast("当前没有焦点.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.filter:
                AllotListFilterActivity.startActivity(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        mScannerHelper.unregisterReceiver(this);
        unregisterEventBus();
        super.onDestroy();
    }

    private void initView() {
        mDocumentView = (AutoCompleteTextView) findViewById(R.id.allot_list_document_scan);
        mDocumentView.setOnItemClickListener(this::onDocumentClick);
        mDocumentAdapter = new KeyValueListAdapter(null);
        mDocumentView.setAdapter(mDocumentAdapter);
        mDocumentView.setOnClickListener(v -> {
            if (!mDocumentView.isPopupShowing()) {
                mDocumentView.showDropDown();
            }
        });
        mDocumentView.setOnFocusChangeListener(this::onFocusChange);

        mGoodsView = (AutoCompleteTextView) findViewById(R.id.allot_list_goods_scan);
        mGoodsView.setOnItemClickListener(this::onGoodsClick);
        mGoodsAdapter = new KeyValueListAdapter(null);
        mGoodsView.setAdapter(mGoodsAdapter);
        mGoodsView.setOnClickListener(v -> {
            if (!mGoodsView.isPopupShowing()) {
                mGoodsView.showDropDown();
            }
        });
        mGoodsView.setOnFocusChangeListener(this::onFocusChange);

        mExportView = (EditText) findViewById(R.id.allot_list_export_goods_allocation);
        mExportView.setOnFocusChangeListener(this::onFocusChange);
        mExportView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mHelper.setExportLocation(s.toString());
            }
        });
        mImportView = (EditText) findViewById(R.id.allot_list_import_goods_allocation);
        mImportView.setOnFocusChangeListener(this::onFocusChange);
        mImportView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mHelper.setImportLocation(s.toString());
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new AllotListAdapter(null);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        VerticalDecoration decoration = new VerticalDecoration(this);
        decoration.setHeight(this.getContext().getResources().getDimensionPixelSize(R.dimen.dp_16));
        recyclerView.addItemDecoration(decoration);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this.getContext(), this));

        mOptionDialog = DialogFactory.createOptionMenuDialog(this, R.array.pd_option_1, this::onItemClick);
        mService = NetServiceManager.getInstance().getService(AllotListService.class);
        mHelper = new AllotListHelper();

        Preferences preferences = SharedPreferencesManager.getInstance().getAllotFilterPreferences();
        mWarehouseId = preferences.getInt(AllotListFilterActivity.KEY_WAREHOUSE_ID, 0);
        mRepositoryId = preferences.getInt(AllotListFilterActivity.KEY_REPOSITORY_ID, 0);

        Intent intent = getIntent();
        if (intent.hasExtra(KEY_ENTITY)) {
            mDocumentEntity = getIntent().getParcelableExtra(KEY_ENTITY);
        }

        mSubmitDialog = DialogFactory.createAlertDialog(this, getString(R.string.prompt_complete_all), this::onSubmit);

        obtainDocument();
    }

    private void obtainDocument() {
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = mService.getDocumentList(mWarehouseId, mRepositoryId);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<KeyValueEntity>>(this) {
            @Override
            public void doNext(List<KeyValueEntity> data) {
                mDocumentAdapter.setItems(data);
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("没有未完成的调拨单.");
                }
            }

            @Override
            public void doComplete() {
                obtainGoods();
            }
        });
    }

    private void obtainGoods() {
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = mService.getGoodsList(mWarehouseId, mRepositoryId);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<KeyValueEntity>>(this) {
            @Override
            public void doNext(List<KeyValueEntity> data) {
                mGoodsAdapter.setItems(data);
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("没有可调拨的商品.");
                }
            }

            @Override
            public void doComplete() {
                if (mDocumentEntity != null) {
                    mDocumentView.setText(mDocumentEntity.getNumber());
                    obtainData();
                }
            }
        });
    }

    private void onGoodsClick(AdapterView<?> adapterView, View view, int position, long id) {
        KeyValueEntity entity = mGoodsAdapter.getItem(position);
        if (!mGoodsCode.equals(entity.getValue())) {
            mGoodsCode = entity.getValue();
            mGoodsView.setSelection(mGoodsCode.length());
            obtainData();
            WindowUtils.hideInputSoft(view);
        }
    }

    private void onDocumentClick(AdapterView<?> adapterView, View view, int position, long id) {
        KeyValueEntity entity = mDocumentAdapter.getItem(position);
        if (!mDocumentCode.equals(entity.getValue())) {
            mDocumentCode = entity.getValue();
            mDocumentView.setSelection(mDocumentCode.length());
            obtainData();
            WindowUtils.hideInputSoft(view);
        }
    }

    private void onFocusChange(View view, boolean hasFocus) {
        int id = view.getId();
        switch (id) {
            case R.id.allot_list_document_scan:
                if (hasFocus) {
                    mDocumentView.showDropDown();
                } else {
                    mDocumentView.dismissDropDown();
                }
                break;
            case R.id.allot_list_goods_scan:
                if (hasFocus) {
                    mGoodsView.showDropDown();
                } else {
                    mGoodsView.dismissDropDown();
                }
                break;
        }
        if (hasFocus) {
            this.mFocusView = (EditText) view;
        }
    }

    private void obtainData() {
        Flowable<ResponseEntity<List<AllotListEntity>>> flowable = mService.getList(mDocumentCode, mGoodsCode, mWarehouseId, mRepositoryId);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<AllotListEntity>>(this) {
            @Override
            public void doNext(List<AllotListEntity> data) {
                mHelper.setDataSource(data);
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("没有商品。");
                }
            }

            @Override
            public void doComplete() {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChanged(AllotListHelper helper) {
        mAdapter.setItems(mHelper.getDataSource());
    }

    @Override
    public void onItemClick(View view, int position) {
        mSelectPosition = position;
        mOptionDialog.show();
    }

    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                single();
                break;
            case 1:
                AllotListEditActivity.startActivity(this, mAdapter.getItem(mSelectPosition));
                break;
        }
        mOptionDialog.dismiss();
    }

    private void single() {
        final AllotListEntity entity = mAdapter.getItem(mSelectPosition);
        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.single(entity.getOriginalId());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                mAdapter.remove(mSelectPosition);
                mHelper.remove(entity);
            }

            @Override
            public void doComplete() {
                DocumentHelper.getInstance().notifyDocumentChanged();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFiltered(AllotListFilterActivity.FilterFlag flag) {
        Preferences preferences = SharedPreferencesManager.getInstance().getAllotFilterPreferences();
        mWarehouseId = preferences.getInt(AllotListFilterActivity.KEY_WAREHOUSE_ID, 0);
        mRepositoryId = preferences.getInt(AllotListFilterActivity.KEY_REPOSITORY_ID, 0);
        mHelper.clear();
        mDocumentView.setText("");
        mGoodsView.setText("");
        mExportView.setText("");
        mImportView.setText("");
        mDocumentCode = "";
        mGoodsCode = "";
        obtainData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEdited(AllotListEditActivity.EditFlag flag) {
        AllotListEntity entity = mAdapter.getItem(mSelectPosition);
        mAdapter.remove(mSelectPosition);
        mHelper.remove(entity);
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.allot_list_complete:
                complete();
                break;
        }
    }

    private void complete() {
        int count = mAdapter.getItemCount();
        if (count <= 0) {
            Toaster.showToast("没有可以完成的数据.");
            return;
        }
        if (count >= 30) {
            Toaster.showToast("当前完成条数过多,请到PC端操作.");
            return;
        }
        if (count > 1) {
            mSubmitDialog.show();
        } else {
            submit();
        }
    }

    private void onSubmit(DialogInterface dialog, int which) {
        submit();
    }

    private void submit() {
        List<Integer> ids = mHelper.getIds(mAdapter.getItems());
        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.complete(ids);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                mHelper.removes(mAdapter.getItems());
                DocumentHelper.getInstance().notifyDocumentChanged();
            }

            @Override
            public void doComplete() {
                mAdapter.removes();
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}

