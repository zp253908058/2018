package com.teeny.wms.page.receiving;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseFragmentPagerAdapter;
import com.teeny.wms.base.KeyValueMultipleChoiceAdapter;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.ReceivingService;
import com.teeny.wms.model.DocumentEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ReceivingEntity;
import com.teeny.wms.model.ReceivingItemEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.page.receiving.adapter.ReceivingAdapter;
import com.teeny.wms.page.receiving.fragment.OrderDetailFragment;
import com.teeny.wms.page.receiving.helper.ReceivingHelper;
import com.teeny.wms.page.common.adapter.KeyValueListAdapter;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.WindowUtils;
import com.teeny.wms.widget.KeyValueTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description: 收货验收
 *
 * @author zp
 * @version 1.0
 * @see ReceivingActivity
 * @since 2017/7/16
 */

public class ReceivingActivity extends ToolbarActivity implements BaseFragmentPagerAdapter.Callback, ViewPager.OnPageChangeListener {

    private static final String KEY_ENTITY = "entity";

    public static void startActivity(Context context, DocumentEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, ReceivingActivity.class);
        if (Validator.isNotNull(entity)) {
            intent.putExtra(KEY_ENTITY, entity);
        }
        context.startActivity(intent);
    }

    private static final int INVALID_ID = -1;

    private SparseArrayCompat<Fragment> mFragmentHolder = new SparseArrayCompat<>();
    private String[] mTitles;

    private AutoCompleteTextView mUnitTextView;
    private KeyValueListAdapter mUnitAdapter;
    private int mSelectUnitId = INVALID_ID;

    private EditText mGoodsScanTextView;

    private TextView mStatusTextView;
    private TextView mBuyerTextView;

    private ReceivingService mService;

    private ReceivingHelper mHelper;
    private DocumentEntity mDocumentEntity;

    private AlertDialog mOrderSelectDialog;
    private KeyValueMultipleChoiceAdapter mOrderAdapter;

    private ViewPager mViewPager;
    private KeyValueTextView mCountView;
    private Map<ReceivingAdapter, AdapterDataObserver> mAdapterDataObserverHolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving_acceptance_layout);
        registerEventBus();
        initView();
    }

    @Override
    protected void onDestroy() {
        mScannerHelper.unregisterReceiver(this);
        unregisterEventBus();
        for (Map.Entry<ReceivingAdapter, AdapterDataObserver> entry : mAdapterDataObserverHolder.entrySet()) {
            entry.getKey().unregisterAdapterDataObserver(entry.getValue());
        }
        super.onDestroy();
        mHelper.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order_filter, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.order_filter:
                List<KeyValueEntity> items = mHelper.getOrderList();
                if (Validator.isEmpty(items)) {
                    Toaster.showToast("该单位没有订单.");
                } else {
                    mOrderAdapter.setItems(items);
                    mOrderSelectDialog.show();
                    List<Integer> selectPositions = mHelper.getSelectOrderPosition();
                    ListView listView = (ListView) mOrderSelectDialog.findViewById(R.id.list_view);
                    if (listView == null) {
                        return true;
                    }
                    for (int position : selectPositions) {
                        listView.setItemChecked(position, true);
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        mAdapterDataObserverHolder = new HashMap<>();

        mTitles = getResources().getStringArray(R.array.status);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), this));
        mViewPager.addOnPageChangeListener(this);
        mCountView = (KeyValueTextView) findViewById(R.id.key_value_text_view);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mUnitTextView = (AutoCompleteTextView) findViewById(R.id.receiving_acceptance_unit_text_view);
        mUnitTextView.setOnFocusChangeListener(this::onUnitFocusChange);
        mUnitTextView.setOnItemClickListener(this::onUnitClick);
        mUnitAdapter = new KeyValueListAdapter(null);
        mUnitTextView.setAdapter(mUnitAdapter);
        mUnitTextView.setOnClickListener(v -> {
            if (!mUnitTextView.isPopupShowing()) {
                mUnitTextView.showDropDown();
            }
        });

        mGoodsScanTextView = (EditText) findViewById(R.id.receiving_acceptance_goods_filter);
        mGoodsScanTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int size = mFragmentHolder.size();
                for (int i = 0; i < size; i++) {
                    OrderDetailFragment fragment = (OrderDetailFragment) mFragmentHolder.get(mFragmentHolder.keyAt(i));
                    fragment.getAdapter().getFilter().filter(s);
                }
            }
        });


        mStatusTextView = (TextView) findViewById(R.id.receiving_acceptance_order_status);
        mBuyerTextView = (TextView) findViewById(R.id.receiving_acceptance_salesman);

        mService = NetServiceManager.getInstance().getService(ReceivingService.class);

        mHelper = new ReceivingHelper();
        mScannerHelper.openScanner(this, this::handleScannerResult);

        mOrderAdapter = new KeyValueMultipleChoiceAdapter(null);
        mOrderSelectDialog = DialogFactory.createMultipleChoiceDialog(this, getString(R.string.text_choose_order), mOrderAdapter, this::onOrderSelected);

        Intent intent = getIntent();
        if (intent.hasExtra(KEY_ENTITY)) {
            mDocumentEntity = getIntent().getParcelableExtra(KEY_ENTITY);
            obtainDataByNumber();
        } else {
            obtainUnits();
        }
    }

    private void onOrderSelected(DialogInterface dialog, int which) {
        ListView listView = (ListView) mOrderSelectDialog.findViewById(R.id.list_view);
        if (listView == null) {
            return;
        }
        long[] ids = listView.getCheckedItemIds();
        if (ids == null) {
            ids = new long[0];
        }
        mHelper.filterByOrder(ids);
    }

    private void obtainDataByNumber() {
        Flowable<ResponseEntity<List<ReceivingEntity>>> flowable = mService.getDetailOrderNo(mDocumentEntity.getNumber());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<ReceivingEntity>>(this) {
            @Override
            public void doNext(List<ReceivingEntity> data) {
                mHelper.setDataSource(data);
                mHelper.filterByOrder(new long[]{mDocumentEntity.getId()});
            }

            @Override
            public void doComplete() {
                mGoodsScanTextView.setText("");
                mStatusTextView.setText("验收中");
            }
        });
    }

    private void handleScannerResult(String result) {
        mGoodsScanTextView.setText(result);
        ReceivingItemEntity entity = mHelper.findByCode(result);
        if (entity == null) {
            Toaster.showToast("所选单据中未找到该商品.");
        } else {
            ReceivingOrderDetailActivity.startActivity(this, entity);
        }
    }

    private void obtainUnits() {
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = mService.getUnitList();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<KeyValueEntity>>(this) {

            @Override
            public void doNext(List<KeyValueEntity> data) {
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("获取单位为空.");
                } else {
                    mUnitAdapter.setItems(data);
                }
            }

            @Override
            public void doComplete() {

            }
        });
    }

    private void onUnitFocusChange(View view, boolean b) {
        if (!b) {
            mUnitTextView.dismissDropDown();
            return;
        }
        mUnitTextView.showDropDown();
    }

    private void onUnitClick(AdapterView<?> parent, View view, int position, long id) {
        KeyValueEntity entity = mUnitAdapter.getItem(position);
        if (mSelectUnitId != entity.getKey()) {
            mSelectUnitId = entity.getKey();
            mUnitTextView.setText(entity.getValue());
            mUnitTextView.setSelection(entity.getValue().length());
            obtainData();
            WindowUtils.hideInputSoft(view);
        }
    }

    private void obtainData() {
        Flowable<ResponseEntity<List<ReceivingEntity>>> flowable = mService.getDetail(mSelectUnitId);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<ReceivingEntity>>(this) {
            @Override
            public void doNext(List<ReceivingEntity> data) {
                mHelper.setDataSource(data);
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("该单位下没有订单.");
                }
            }

            @Override
            public void doComplete() {
                mGoodsScanTextView.setText("");
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFilter(ReceivingHelper helper) {
        mBuyerTextView.setText(mHelper.getBuyerNames());
        mStatusTextView.setText(mHelper.getStatus());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdate(CompleteFlag flag) {
        obtainData();
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
        OrderDetailFragment fragment = OrderDetailFragment.newInstance(position);
        AdapterDataObserver observer = new AdapterDataObserver(fragment);
        ReceivingAdapter adapter = fragment.getAdapter();
        adapter.registerAdapterDataObserver(observer);
        mAdapterDataObserverHolder.put(adapter, observer);
        return fragment;
    }

    private class AdapterDataObserver extends RecyclerView.AdapterDataObserver {
        private OrderDetailFragment mFragment;

        public AdapterDataObserver(OrderDetailFragment fragment) {
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        OrderDetailFragment fragment = (OrderDetailFragment) getItem(position);
        mCountView.setValue(String.valueOf(fragment.getAdapter().getItemCount()));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static final class CompleteFlag {
    }
}
