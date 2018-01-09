package com.teeny.wms.page.shelve;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseFragmentPagerAdapter;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.ShelveService;
import com.teeny.wms.model.DocumentEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.ShelveEntity;
import com.teeny.wms.page.common.adapter.KeyValueListAdapter;
import com.teeny.wms.page.document.controller.DocumentHelper;
import com.teeny.wms.page.shelve.adapter.ShelveAdapter;
import com.teeny.wms.page.shelve.fragment.ShelveAndStorageFragment;
import com.teeny.wms.page.shelve.helper.ShelveHelper;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.WindowUtils;
import com.teeny.wms.widget.KeyValueTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description: 上架入库
 *
 * @author zp
 * @version 1.0
 * @see ShelveAndStorageActivity
 * @since 2017/7/16
 */

public class ShelveAndStorageActivity extends ToolbarActivity implements BaseFragmentPagerAdapter.Callback, ViewPager.OnPageChangeListener {

    private static final String KEY_ENTITY = "entity";

    public static void startActivity(Context context, DocumentEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, ShelveAndStorageActivity.class);
        if (Validator.isNotNull(entity)) {
            intent.putExtra(KEY_ENTITY, entity);
        }
        context.startActivity(intent);
    }

    private static final int INVALID_ID = -1;


    private SparseArrayCompat<Fragment> mFragmentHolder = new SparseArrayCompat<>();
    private String[] mTitles;

    private ArrayAdapter<KeyValueEntity> mAdapter;

    private AutoCompleteTextView mOrderTextView;
    private KeyValueListAdapter mOrderAdapter;
    private int mSelectOrderId = INVALID_ID;

    private EditText mLocationTextView;
    private EditText mGoodsTextView;

    private EditText mFocusView;

    private ShelveService mService;
    private ShelveHelper mHelper;
    private DocumentEntity mDocumentEntity;

    private AlertDialog mSubmitDialog;

    private ViewPager mViewPager;
    private KeyValueTextView mCountView;
    private Map<ShelveAdapter, AdapterDataObserver> mAdapterDataObserverHolder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelve_and_storage_layout);
        mScannerHelper.openScanner(this, this::handleScannerResult);
        registerEventBus();
        initView();
    }

    private void handleScannerResult(String result) {
        if (mFocusView != null) {
            mFocusView.setText(result);
            int id = mFocusView.getId();
            switch (id) {
                case R.id.shelve_and_storage_order_number:
                    obtainData(result);
                    break;
            }
        } else {
            Toaster.showToast("当前没有焦点.");
        }
    }

    @Override
    protected void onDestroy() {
        mScannerHelper.unregisterReceiver(this);
        unregisterEventBus();
        for (Map.Entry<ShelveAdapter, AdapterDataObserver> entry : mAdapterDataObserverHolder.entrySet()) {
            entry.getKey().unregisterAdapterDataObserver(entry.getValue());
        }
        super.onDestroy();
    }

    private void initView() {

        mAdapterDataObserverHolder = new HashMap<>();

        mTitles = getResources().getStringArray(R.array.shelve_status);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), this));
        mViewPager.addOnPageChangeListener(this);
        mCountView = (KeyValueTextView) findViewById(R.id.key_value_text_view);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);

        mHelper = new ShelveHelper();
        mViewPager.setCurrentItem(1);

        Spinner spinner = (Spinner) findViewById(R.id.shelve_and_storage_sa);
        List<KeyValueEntity> list = new ArrayList<>();
        list.add(new KeyValueEntity(0, "选择库区(全部)"));
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                KeyValueEntity entity = mAdapter.getItem(pos);
                if (entity != null) {
                    obtainOrders(entity.getKey());
                }
                if (mOrderAdapter.getCount() > 0) {
                    mOrderAdapter.removes();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mOrderTextView = (AutoCompleteTextView) findViewById(R.id.shelve_and_storage_order_number);
        mOrderTextView.setOnItemClickListener(this::onOrderNoClick);
        mOrderAdapter = new KeyValueListAdapter(null);
        mOrderTextView.setAdapter(mOrderAdapter);
        mOrderTextView.setOnClickListener(v -> {
            if (!mOrderTextView.isPopupShowing()) {
                mOrderTextView.showDropDown();
            }
        });
        mOrderTextView.setOnFocusChangeListener(this::onFocusChange);

        mLocationTextView = (EditText) findViewById(R.id.shelve_and_storage_goods_allocation);
        mLocationTextView.setOnFocusChangeListener(this::onFocusChange);
        mLocationTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mHelper.filterByLocationAndGoodsCode(s.toString(), mGoodsTextView.getText().toString());
            }
        });
        mGoodsTextView = (EditText) findViewById(R.id.shelve_and_storage_goods);
        mGoodsTextView.setOnFocusChangeListener(this::onFocusChange);
        mGoodsTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mHelper.filterByLocationAndGoodsCode(mLocationTextView.getText().toString(), s.toString());
            }
        });

        mService = NetServiceManager.getInstance().getService(ShelveService.class);

        mSubmitDialog = DialogFactory.createAlertDialog(this, getString(R.string.prompt_complete_all), this::onSubmit);

        Intent intent = getIntent();
        if (intent.hasExtra(KEY_ENTITY)) {
            mDocumentEntity = getIntent().getParcelableExtra(KEY_ENTITY);
        }

        obtainSa();
    }

    /**
     * 加载库区
     */
    private void obtainSa() {
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = mService.getSaList();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<KeyValueEntity>>(this) {
            @Override
            public void doNext(List<KeyValueEntity> data) {
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("获取到库区为空.");
                } else {
                    mAdapter.addAll(data);
                }
            }

            @Override
            public void doComplete() {

            }
        });
    }

    /**
     * @param id 库区id
     */
    private void obtainOrders(int id) {
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = mService.getOrderNoList(id);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<KeyValueEntity>>(this) {
            @Override
            public void doNext(List<KeyValueEntity> data) {
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("获取到未完成单号为空.");
                } else {
                    mOrderAdapter.setItems(data);
                }
            }

            @Override
            public void doComplete() {
                if (mDocumentEntity != null) {
                    mOrderTextView.setText(mDocumentEntity.getNumber());
                    obtainData(mDocumentEntity.getNumber());
                }
            }
        });
    }

    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.shelve_and_storage_order_number) {
            if (!hasFocus) {
                mOrderTextView.dismissDropDown();
                return;
            }
            mOrderTextView.showDropDown();
        }
        if (hasFocus) {
            this.mFocusView = (EditText) v;
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.shelve_and_storage_putaway:
                complete();
                break;
        }
    }

    private void onOrderNoClick(AdapterView<?> parent, View view, int position, long id) {
        KeyValueEntity entity = mOrderAdapter.getItem(position);
        if (mSelectOrderId != entity.getKey()) {
            mSelectOrderId = entity.getKey();
            mOrderTextView.setSelection(entity.getValue().length());
            obtainData(entity.getValue());
            WindowUtils.hideInputSoft(view);
        }
    }


    private void obtainData(String code) {
        Flowable<ResponseEntity<List<ShelveEntity>>> flowable = mService.getGoodsDetailList(code);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<ShelveEntity>>(this) {
            @Override
            public void doNext(List<ShelveEntity> data) {
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("获取到商品为空.");
                }
                mHelper.setDataSource(data);
            }

            @Override
            public void doComplete() {
                mGoodsTextView.setText("");
                mLocationTextView.setText("");
            }
        });
    }

    private void complete() {
        List<Integer> ids = mHelper.getAchievableIds();
        if (Validator.isEmpty(ids)) {
            Toaster.showToast("没有可以完成的商品.");
            return;
        }
        if (ids.size() >= 30) {
            Toaster.showToast("当前完成条数过多,请到PC端操作.");
            return;
        }
        if (ids.size() > 1) {
            mSubmitDialog.show();
        } else {
            submit();
        }
    }

    private void onSubmit(DialogInterface dialog, int which) {
        submit();
    }

    private void submit() {
        List<Integer> ids = mHelper.getAchievableIds();
        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.all(ids);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                Toaster.showToast("全部完成操作成功.");
            }

            @Override
            public void doComplete() {
                mHelper.reverseAllStatus();
                DocumentHelper.getInstance().notifyDocumentChanged();
                mLocationTextView.setText("");
                mGoodsTextView.setText("");
            }
        });
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragmentHolder.get(position);
        if (Validator.isNull(fragment)) {
            fragment = createFragment(position);
            mFragmentHolder.put(position, fragment);
        }
        return fragment;
    }

    private Fragment createFragment(int position) {
        ShelveAndStorageFragment fragment = ShelveAndStorageFragment.newInstance(position);
        AdapterDataObserver observer = new AdapterDataObserver(fragment);
        ShelveAdapter adapter = fragment.getAdapter();
        adapter.registerAdapterDataObserver(observer);
        mAdapterDataObserverHolder.put(adapter, observer);
        return fragment;
    }

    private class AdapterDataObserver extends RecyclerView.AdapterDataObserver {
        private ShelveAndStorageFragment mFragment;

        public AdapterDataObserver(ShelveAndStorageFragment fragment) {
            mFragment = fragment;
        }

        @Override
        public void onChanged() {
            if (mFragment == getItem(mViewPager.getCurrentItem())) {
                mCountView.setValue(String.valueOf(mFragment.getAdapter().getItemCount()));
            }
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdate(CompleteFlag flag) {
        obtainData(mOrderTextView.getText().toString());
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ShelveAndStorageFragment fragment = (ShelveAndStorageFragment) getItem(position);
        mCountView.setValue(String.valueOf(fragment.getAdapter().getItemCount()));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static final class CompleteFlag {

    }
}
