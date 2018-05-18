package com.teeny.wms.page.allot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.AllotListService;
import com.teeny.wms.model.AllocationEntity;
import com.teeny.wms.model.AllotListEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.AllotListRequestEntity;
import com.teeny.wms.page.common.activity.AllocationEditActivity;
import com.teeny.wms.page.common.adapter.AllocationAdapter;
import com.teeny.wms.page.document.controller.DocumentHelper;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.CollectionsUtils;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotListEditActivity
 * @since 2017/8/24
 */

public class AllotListEditActivity extends ToolbarActivity implements DialogInterface.OnClickListener {

    private static final String KEY_DATA = "data";

    public static void startActivity(Context context, AllotListEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, AllotListEditActivity.class);
        intent.putExtra(KEY_DATA, entity);
        context.startActivity(intent);
    }

    private static final int INVALID_POSITION = -1;

    private AllocationAdapter mAdapter;
    private int mClickPosition = INVALID_POSITION;
    private int mDeletePosition = INVALID_POSITION;
    private AlertDialog mDeleteDialog;

    private AllotListEntity mEntity;
    private AllotListService mService;

    private KeyValueTextView mAmountView;
    private float mAmount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allot_list_edit_layout);

        registerEventBus();
        initView();
    }

    @Override
    protected void onDestroy() {
        unregisterEventBus();
        super.onDestroy();
    }

    private void initView() {
        mEntity = getIntent().getParcelableExtra(KEY_DATA);

        KeyValueTextView nameView = (KeyValueTextView) findViewById(R.id.allot_list_edit_name);
        KeyValueTextView numberView = (KeyValueTextView) findViewById(R.id.allot_list_edit_number);
        mAmountView = (KeyValueTextView) findViewById(R.id.allot_list_edit_amount);
        KeyValueTextView lotView = (KeyValueTextView) findViewById(R.id.allot_list_edit_lot_number);
        KeyValueTextView specificationView = (KeyValueTextView) findViewById(R.id.allot_list_edit_specification);
        KeyValueTextView validityDate = (KeyValueTextView) findViewById(R.id.allot_list_edit_validity_date);
        KeyValueTextView unitView = (KeyValueTextView) findViewById(R.id.allot_list_edit_unit);
        KeyValueTextView manufacturerView = (KeyValueTextView) findViewById(R.id.allot_list_edit_manufacturer);

        nameView.setValue(mEntity.getGoodsName());
        numberView.setValue(mEntity.getNumber());
        specificationView.setValue(mEntity.getSpecification());
        lotView.setValue(mEntity.getLotNo());
        unitView.setValue(mEntity.getUnit());
        manufacturerView.setValue(mEntity.getManufacturer());
        validityDate.setValue(mEntity.getValidateDate());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new AllocationAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        VerticalDecoration decoration = new VerticalDecoration(this.getContext());
        decoration.setHeight(this.getContext().getResources().getDimensionPixelSize(R.dimen.dp_16));
        recyclerView.addItemDecoration(decoration);
        RecyclerViewTouchListener listener = new RecyclerViewTouchListener(this, this::onItemClick);
        listener.setOnItemLongClickListener(this::onItemLongClick);
        recyclerView.addOnItemTouchListener(listener);

        mDeleteDialog = DialogFactory.createAlertDialog(this, getString(R.string.prompt_delete_confirm), this);
        mDeleteDialog.setOnDismissListener(dialog -> mDeletePosition = INVALID_POSITION);

        mService = NetServiceManager.getInstance().getService(AllotListService.class);

        mAmountView.setValue(String.valueOf(mAmount));
        obtainData();
    }

    private void onItemClick(View view, int position) {
        mClickPosition = position;
        AllocationEditActivity.startActivity(this, mEntity.getGoodsId(), mAdapter.getItem(position));
    }

    public boolean onItemLongClick(View view, int position) {
        mDeletePosition = position;
        mDeleteDialog.show();
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void allocationUpdate(AllocationEntity entity) {
        if (mClickPosition == INVALID_POSITION) {
            mAdapter.append(entity);
        } else {
            mAdapter.update(entity, mClickPosition);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void obtainData() {
        Flowable<ResponseEntity<List<AllocationEntity>>> flowable = mService.getLocations(mEntity.getOriginalId());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<AllocationEntity>>(this) {
            @Override
            public void doNext(List<AllocationEntity> data) {
                mAdapter.setItems(data);
                if (Validator.isNotEmpty(data)) {
                    for (AllocationEntity entity : data) {
                        mAmount += entity.getAmount();
                    }
                }
                mAmountView.setValue(String.valueOf(mAmount));
            }

            @Override
            public void doComplete() {

            }
        });
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
                mClickPosition = INVALID_POSITION;
                AllocationEditActivity.startActivity(this, mEntity.getGoodsId());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        List<AllocationEntity> result = mAdapter.getItems();
        if (Validator.isEmpty(result)) {
            Toaster.showToast("请添加货位.");
            return;
        }
        if (CollectionsUtils.sizeOf(result) == 1) {
            AllocationEntity entity = result.get(0);
            if (entity.getAmount() != mAmount) {
                Toaster.showToast("数量不一致或不为0.");
            }
        } else {
            float amount = 0;
            for (AllocationEntity entity : result) {
                amount += entity.getAmount();
            }
            if (amount != mAmount) {
                Toaster.showToast("数量不一致,请保证数量一致.");
                return;
            }
        }
        AllotListRequestEntity entity = new AllotListRequestEntity();
        entity.setId(mEntity.getOriginalId());
        entity.setLocations(result);
        entity.setClassType(mEntity.getClassType());
        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.update(entity);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                getEventBus().post(new EditFlag());
            }

            @Override
            public void doComplete() {
                DocumentHelper.getInstance().notifyDocumentChanged();
                finish();
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mDeletePosition != INVALID_POSITION) {
            mAdapter.remove(mDeletePosition);
        }
    }

    public static final class EditFlag {
    }
}
