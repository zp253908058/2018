package com.teeny.wms.page.allot;

import android.app.Activity;
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
import com.teeny.wms.datasouce.net.service.AllotListService;
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
 * @see AllotListFilterActivity
 * @since 2017/8/24
 */

public class AllotListFilterActivity extends ToolbarActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AllotListFilterActivity.class);
        context.startActivity(intent);
    }

    public static final String KEY_WAREHOUSE_ID = "warehouse_id";
    public static final String KEY_REPOSITORY_ID = "repository_id";

    private Spinner mExportWarehouse;     //调出仓库
    private KeyValueEntity mSelectWarehouse;
    private Spinner mExportRepository;    //调出库区
    private KeyValueEntity mSelectRepository;

    private AllotListService mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allot_list_filter_layout);

        initView();
    }

    private void initView() {
        mExportWarehouse = (Spinner) findViewById(R.id.allot_list_filter_export_warehouse);
        mExportWarehouse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectWarehouse = (KeyValueEntity) parent.getAdapter().getItem(position);
                obtainExRepository(mSelectWarehouse.getKey());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mExportRepository = (Spinner) findViewById(R.id.allot_list_filter_export_repository);
        mExportRepository.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectRepository = (KeyValueEntity) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mService = NetServiceManager.getInstance().getService(AllotListService.class);
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
                ArrayAdapter<KeyValueEntity> exportAdapter = new ArrayAdapter<>(AllotListFilterActivity.this, android.R.layout.simple_spinner_item, items);
                exportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mExportWarehouse.setAdapter(exportAdapter);
                mExportWarehouse.setSelection(0, true);
            }

            @Override
            public void doComplete() {

            }
        });
    }

    /**
     * @param id 仓库id
     */
    private void obtainExRepository(int id) {
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = mService.getSaList(id);
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
                ArrayAdapter<KeyValueEntity> exportAdapter = new ArrayAdapter<>(AllotListFilterActivity.this, android.R.layout.simple_spinner_item, items);
                exportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mExportRepository.setAdapter(exportAdapter);
                mExportRepository.setSelection(0, true);
            }

            @Override
            public void doComplete() {

            }
        });
    }

    public void onClick(View view) {
        Preferences preferences = SharedPreferencesManager.getInstance().getAllotFilterPreferences();
        if (mSelectWarehouse != null) {
            preferences.putInt(KEY_WAREHOUSE_ID, mSelectWarehouse.getKey());
            Logger.e(mSelectWarehouse.toString());
        }
        if (mSelectRepository != null) {
            preferences.putInt(KEY_REPOSITORY_ID, mSelectRepository.getKey());
            Logger.e(mSelectRepository.toString());
        }
        getEventBus().post(new FilterFlag());
        finish();
    }

    public static class FilterFlag {
    }
}
