package com.teeny.wms.page.common.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.teeny.wms.R;
import com.teeny.wms.app.ScannerHelper;
import com.teeny.wms.base.BaseFragment;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.InventoryService;
import com.teeny.wms.model.AreaEntity;
import com.teeny.wms.model.InventoryCountEntity;
import com.teeny.wms.model.InventoryGoodsEntity;
import com.teeny.wms.model.InventoryGoodsWrapperEntity;
import com.teeny.wms.model.InventoryInitializeEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.RepositoryEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.page.common.adapter.SimpleAdapter;
import com.teeny.wms.page.common.helper.InventoryHelper;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.ObjectUtils;
import com.teeny.wms.util.Validator;
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
 * @see InventoryHeaderFragment
 * @since 2017/12/26
 */

public abstract class InventoryHeaderFragment extends BaseFragment {

    private Spinner mSpinner;

    private AutoCompleteTextView mRepositoryTextView;    //库区
    private SimpleAdapter<RepositoryEntity> mRepositoryAdapter;

    private AutoCompleteTextView mAreaTextView;          //区域
    private SimpleAdapter<AreaEntity> mAreaAdapter;

    private EditText mLocationTextView;
    private EditText mGoodsTextView;
    private EditText mFocusView;
    private ScannerHelper mScannerHelper;

    private InventoryService mService;

    private RepositoryEntity mSelectedRepository;
    private AreaEntity mSelectedArea;

    public abstract InventoryHelper getHelper();

    private EventBus mEventBus = EventBus.getDefault();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerHelper = ScannerHelper.getInstance();
        mScannerHelper.openScanner(getContext(), this::handleResult);
        mService = NetServiceManager.getInstance().getService(InventoryService.class);
        mEventBus.register(this);
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
        mEventBus.unregister(this);
    }

    @Override
    protected View onCreateHolderView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_inventory_header_layout, container, false);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        mSpinner = findView(R.id.inventory_header_type);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                obtainRepositoryAndArea();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mRepositoryTextView = findView(R.id.inventory_header_repository);
        mRepositoryTextView.setOnFocusChangeListener(this::onRepositoryFocusChange);
        mRepositoryTextView.setOnItemClickListener((parent, view, position, id) -> {
            RepositoryEntity repositoryEntity = mRepositoryAdapter.getItem(position);
            if (mSelectedRepository == repositoryEntity) {
                return;
            }
            mSelectedRepository = repositoryEntity;
            if (mSelectedRepository == null) {
                return;
            }
            List<AreaEntity> areas = mSelectedRepository.getAreas();
            if (Validator.isNotEmpty(areas)) {
                mAreaAdapter = new SimpleAdapter<>(getContext());
                mAreaAdapter.addAll(areas);
                mAreaTextView.setAdapter(mAreaAdapter);
            }
            onRepositoryOrAreaSelected();
        });
        mRepositoryTextView.addTextChangedListener(new TextWatcher() {
            private boolean mIsClear;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mIsClear = s.length() != 0 && count != 0;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mIsClear) {
                    getHelper().clear();
                }
                mAreaTextView.setText("");
                mAreaTextView.setAdapter(new SimpleAdapter<>(getContext()));
                mSelectedArea = null;
                mLocationTextView.setText("");
                mGoodsTextView.setText("");
                if (mSelectedRepository == null || !mSelectedRepository.getName().equals(s.toString())) {
                    mSelectedRepository = null;
                }
            }
        });
        mRepositoryAdapter = new SimpleAdapter<>(getContext());
        mRepositoryTextView.setAdapter(mRepositoryAdapter);
        mRepositoryTextView.setOnClickListener(v -> {
            if (!mRepositoryTextView.isPopupShowing()) {
                mRepositoryTextView.showDropDown();
            }
        });
        mAreaTextView = findView(R.id.inventory_header_area);
        mAreaTextView.setOnFocusChangeListener(this::onAreaFocusChange);
        mAreaTextView.setOnItemClickListener((parent, view, position, id) -> {
            AreaEntity areaEntity = mAreaAdapter.getItem(position);
            if (mSelectedArea == areaEntity) {
                return;
            }
            mSelectedArea = areaEntity;
            onRepositoryOrAreaSelected();
        });
        mAreaTextView.addTextChangedListener(new TextWatcher() {
            private boolean mIsClear;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mIsClear = s.length() != 0 && count != 0;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mIsClear) {
                    getHelper().clear();
                }
                mLocationTextView.setText("");
                mGoodsTextView.setText("");
                if (mSelectedArea == null || !mSelectedArea.getName().equals(s.toString())) {
                    mSelectedArea = null;
                }
            }
        });
        mAreaAdapter = new SimpleAdapter<>(getContext());
        mAreaTextView.setAdapter(mAreaAdapter);
        mAreaTextView.setOnClickListener(v -> {
            if (!mAreaTextView.isPopupShowing()) {
                mAreaTextView.showDropDown();
            }
        });

        mLocationTextView = findView(R.id.inventory_header_allocation);
        mLocationTextView.setOnFocusChangeListener(this::onFocusChanged);
        mLocationTextView.addTextChangedListener(new TextWatcher() {
            private boolean mIsClear;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mIsClear = s.length() != 0 && count != 0;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int type = getPdType();
                if (type == 2) {
                    mEventBus.post(s);
                    return;
                }
                if (Validator.isEmpty(s)) {
                    if (mIsClear) {
                        getHelper().clear();
                    }
                } else {
                    obtainDetailData();
                }
            }
        });
        mGoodsTextView = findView(R.id.inventory_header_goods);
        mGoodsTextView.setOnFocusChangeListener(this::onFocusChanged);
        mGoodsTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getHelper().setGoodsCode(s.toString());
            }
        });

        obtainPdType();
    }

    private void onRepositoryFocusChange(View view, boolean b) {
        if (!b) {
            mRepositoryTextView.dismissDropDown();
            return;
        }
        mRepositoryTextView.showDropDown();
    }

    private void onAreaFocusChange(View view, boolean b) {
        if (!b) {
            mAreaTextView.dismissDropDown();
            return;
        }
        mAreaTextView.showDropDown();
    }

    private void onFocusChanged(View view, boolean hasFocus) {
        if (hasFocus) {
            mFocusView = (EditText) view;
        }
    }

    private void onRepositoryOrAreaSelected(){
        if (getPdType() != 2) {
            count();
        } else {
            obtainDetailData();
        }
    }

    private void obtainPdType() {
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = mService.getPdType(getPdType());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<KeyValueEntity>>(this) {

            @Override
            public void doNext(List<KeyValueEntity> data) {
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("获取到盘点类型为空.");
                }
                ArrayAdapter<KeyValueEntity> exportAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, data);
                exportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(exportAdapter);
            }

            @Override
            public void doComplete() {
                mSpinner.setSelection(0, true);
            }
        });
    }

    /**
     * @return 1门店盘点 2复盘 4 仓库盘点
     */
    protected abstract int getPdType();

    private void obtainRepositoryAndArea() {
        Object item = mSpinner.getSelectedItem();
        if (item == null) {
            return;
        }
        KeyValueEntity entity = (KeyValueEntity) item;
        Flowable<ResponseEntity<InventoryInitializeEntity>> flowable = mService.initialize(entity.getKey(), getPdType());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<InventoryInitializeEntity>(this) {
            @Override
            public void doNext(InventoryInitializeEntity data) {
                mRepositoryAdapter.clear();
                mRepositoryAdapter.addAll(data.getRepositoryList());
                getHelper().setNumber(data.getFinishedNumber(), data.getUnfinishedNumber());
            }

            @Override
            public void doComplete() {
                mRepositoryTextView.setText("");
                mAreaTextView.setText("");
                mLocationTextView.setText("");
                mGoodsTextView.setText("");
                if (getPdType() == 2) {
                    obtainDetailData();
                }
            }
        });
    }

    /**
     * 统计数量
     */
    private void count() {
        Object item = mSpinner.getSelectedItem();
        if (item == null) {
            return;
        }
        KeyValueEntity entity = (KeyValueEntity) item;
        int selectedRepositoryId = mSelectedRepository != null ? mSelectedRepository.getId() : 0;
        int selectedAreaId = mSelectedArea != null ? mSelectedArea.getId() : 0;
        Flowable<ResponseEntity<InventoryCountEntity>> flowable = mService.count(entity.getKey(), selectedRepositoryId, selectedAreaId, getPdType());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<InventoryCountEntity>(this) {
            @Override
            public void doNext(InventoryCountEntity data) {
                if (data == null) {
                    Logger.e("data == null");
                    Toaster.showToast("统计数量失败!");
                    return;
                }
                getHelper().setNumber(data.getFinishedNumber(), data.getUnfinishedNumber());
            }

            @Override
            public void doComplete() {

            }
        });
    }

    private void obtainDetailData() {
        int selectedRepositoryId = mSelectedRepository != null ? mSelectedRepository.getId() : 0;
        int selectedAreaId = mSelectedArea != null ? mSelectedArea.getId() : 0;
        String code = mLocationTextView.getText().toString();
        int type = getPdType();
        if (type != 2 && Validator.isEmpty(code)) {
            return;
        }
        Object item = mSpinner.getSelectedItem();
        if (item == null) {
            return;
        }
        KeyValueEntity entity = (KeyValueEntity) item;
        Flowable<ResponseEntity<InventoryGoodsWrapperEntity>> flowable = mService.getList(entity.getKey(), selectedRepositoryId, selectedAreaId, code, getPdType());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<InventoryGoodsWrapperEntity>(this) {
            @Override
            public void doNext(InventoryGoodsWrapperEntity data) {
                getHelper().setDataList(data.getList());
                getHelper().setNumber(data.getFinishedNumber(), data.getUnfinishedNumber());
                if (Validator.isEmpty(data.getList())) {
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
        obtainDetailData();
    }

    public static final class DataChangedObserver {
    }


    public Spinner getSpinner() {
        return mSpinner;
    }

    public String getLocationCode() {
        return mLocationTextView != null ? mLocationTextView.getText().toString() : "";
    }
}
