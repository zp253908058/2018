package com.teeny.wms.page.allot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.datasouce.local.Preferences;
import com.teeny.wms.datasouce.local.SharedPreferencesManager;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.AllotOrderService;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.log.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotOrderFilterActivity
 * @since 2018/1/9
 */

public class AllotOrderFilterActivity extends ToolbarActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AllotOrderFilterActivity.class);
        context.startActivity(intent);
    }

    public static final String KEY_WAREHOUSE_ID = "warehouse_id";
    public static final String KEY_REPOSITORY_ID = "repository_id";
    public static final String KEY_AREA_ID = "area_id";

    private Spinner mWarehouse;     //仓库
    private KeyValueEntity mSelectWarehouse;
    private Spinner mRepository;    //库区
    private KeyValueEntity mSelectRepository;
    private Spinner mArea;    //区域
    private KeyValueEntity mSelectArea;

    private AllotOrderService mService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_allot_order_filter_layout);

        initView();
    }

    private void initView() {
        mWarehouse = (Spinner) findViewById(R.id.allot_order_filter_warehouse);
        mWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectWarehouse = (KeyValueEntity) parent.getAdapter().getItem(position);
                obtainRepository(mSelectWarehouse.getKey());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mRepository = (Spinner) findViewById(R.id.allot_order_filter_repository);
        mRepository.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectRepository = (KeyValueEntity) parent.getAdapter().getItem(position);
                obtainArea(mSelectRepository.getKey());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mArea = (Spinner) findViewById(R.id.allot_order_filter_area);
        mArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectArea = (KeyValueEntity) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mService = NetServiceManager.getInstance().getService(AllotOrderService.class);
        obtainData();
    }

    private void obtainData() {
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = mService.getWarehouseList();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<KeyValueEntity>>(this) {
            @Override
            public void doNext(List<KeyValueEntity> data) {
                List<KeyValueEntity> items = new ArrayList<>();
                items.add(new KeyValueEntity(0, "全部仓库"));
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("未获取到仓库.");
                } else {
                    items.addAll(data);
                }
                ArrayAdapter<KeyValueEntity> adapter = new ArrayAdapter<>(AllotOrderFilterActivity.this, android.R.layout.simple_spinner_item, items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mWarehouse.setAdapter(adapter);
                mWarehouse.setSelection(0, true);
            }

            @Override
            public void doComplete() {

            }
        });
    }

    private void obtainRepository(int key) {
        if (key <= 0) {
            return;
        }
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = mService.getRepositoryList(key);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<KeyValueEntity>>(this) {
            @Override
            public void doNext(List<KeyValueEntity> data) {
                List<KeyValueEntity> items = new ArrayList<>();
                items.add(new KeyValueEntity(0, "全部库区"));
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("未获取到库区.");
                } else {
                    items.addAll(data);
                }
                ArrayAdapter<KeyValueEntity> adapter = new ArrayAdapter<>(AllotOrderFilterActivity.this, android.R.layout.simple_spinner_item, items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mRepository.setAdapter(adapter);
                mRepository.setSelection(0, true);
            }

            @Override
            public void doComplete() {

            }
        });
    }

    private void obtainArea(int key) {
        if (key <= 0) {
            return;
        }
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = mService.getAreaList(key);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<KeyValueEntity>>(this) {
            @Override
            public void doNext(List<KeyValueEntity> data) {
                List<KeyValueEntity> items = new ArrayList<>();
                items.add(new KeyValueEntity(0, "全部区域"));
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("未获取到区域.");
                } else {
                    items.addAll(data);
                }
                ArrayAdapter<KeyValueEntity> adapter = new ArrayAdapter<>(AllotOrderFilterActivity.this, android.R.layout.simple_spinner_item, items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mArea.setAdapter(adapter);
                mArea.setSelection(0, true);
            }

            @Override
            public void doComplete() {

            }
        });
    }

    public void onClick(View view) {
        Preferences preferences = SharedPreferencesManager.getInstance().getAllotOrderFilterPreferences();
        FilterFlag flag = new FilterFlag();
        if (mSelectWarehouse != null) {
            preferences.putInt(KEY_WAREHOUSE_ID, mSelectWarehouse.getKey());
            flag.setWarehouseId(mSelectWarehouse.getKey());
        }
        if (mSelectRepository != null) {
            preferences.putInt(KEY_REPOSITORY_ID, mSelectRepository.getKey());
            flag.setWarehouseId(mSelectRepository.getKey());
        }
        if (mSelectArea != null) {
            preferences.putInt(KEY_AREA_ID, mSelectArea.getKey());
            flag.setWarehouseId(mSelectArea.getKey());
        }
        getEventBus().post(flag);
        finish();
    }

    public static class FilterFlag {
        private int mWarehouseId;
        private int mRepositoryId;
        private int mAreaId;

        public int getWarehouseId() {
            return mWarehouseId;
        }

        public void setWarehouseId(int warehouseId) {
            mWarehouseId = warehouseId;
        }

        public int getRepositoryId() {
            return mRepositoryId;
        }

        public void setRepositoryId(int repositoryId) {
            mRepositoryId = repositoryId;
        }

        public int getAreaId() {
            return mAreaId;
        }

        public void setAreaId(int areaId) {
            mAreaId = areaId;
        }
    }
}
