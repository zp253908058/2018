package com.teeny.wms.page.allot;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.AllotOrderService;
import com.teeny.wms.datasouce.net.service.ReceivingService;
import com.teeny.wms.model.AllotGoodsEntity;
import com.teeny.wms.model.AllotLocationEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ReceivingItemEntity;
import com.teeny.wms.model.ReceivingLotEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.AllotLocationRequestEntity;
import com.teeny.wms.model.request.ReceivingRequestEntity;
import com.teeny.wms.page.allot.adapter.AllotLocationAdapter;
import com.teeny.wms.page.allot.fragment.AllotGoodsSelectedFragment;
import com.teeny.wms.page.document.controller.DocumentHelper;
import com.teeny.wms.page.receiving.ReceivingActivity;
import com.teeny.wms.page.receiving.ReceivingLotAddActivity;
import com.teeny.wms.page.receiving.adapter.ReceivingLotAdapter;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;

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
 * @see AllotOrderAddDetailActivity
 * @since 2018/1/9
 */

public class AllotOrderAddDetailActivity extends ToolbarActivity implements DialogInterface.OnClickListener {

    private static final String KEY_ENTITY = "entity";
    private static final int INVALID_POSITION = -1;

    public static void startActivity(Context context, AllotGoodsEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, AllotOrderAddDetailActivity.class);
        intent.putExtra(KEY_ENTITY, entity);
        context.startActivity(intent);
    }

    private AllotGoodsEntity mEntity;
    private AllotLocationAdapter mAdapter;

    private AllotOrderService mService;

    private int mSelectPosition = INVALID_POSITION;
    private int mDeletePosition = INVALID_POSITION;
    private AlertDialog mDeleteDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_allot_order_add_detail_layout);
        initView();
        registerEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                mSelectPosition = INVALID_POSITION;
                AllotOrderLocationAddActivity.startActivity(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationAdd(AllotLocationEntity entity) {
        if (mSelectPosition != INVALID_POSITION) {
            mAdapter.update(entity, mSelectPosition);
        } else {
            mAdapter.append(entity);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initView() {
        Intent intent = getIntent();
        mEntity = intent.getParcelableExtra(KEY_ENTITY);

        TextView name = (TextView) findViewById(R.id.allot_order_add_detail_name);
        TextView specification = (TextView) findViewById(R.id.allot_order_add_detail_specification);
        TextView manufacturer = (TextView) findViewById(R.id.allot_order_add_detail_manufacturer);
        TextView unit = (TextView) findViewById(R.id.allot_order_add_detail_unit);
        TextView lot = (TextView) findViewById(R.id.allot_order_add_detail_lot);
        TextView validity = (TextView) findViewById(R.id.allot_order_add_detail_validity);
        TextView currentLocation = (TextView) findViewById(R.id.allot_order_add_detail_current_location);
        //当前库存
        TextView currentInventory = (TextView) findViewById(R.id.allot_order_add_detail_current_inventory);

        name.setText(mEntity.getGoodsName());
        specification.setText(mEntity.getSpecification());
        manufacturer.setText(mEntity.getManufacturer());
        unit.setText(mEntity.getUnit());
        lot.setText(mEntity.getLotNo());
        validity.setText(mEntity.getValidityDate());
        currentLocation.setText(mEntity.getLocationCode());
        currentInventory.setText(String.valueOf(mEntity.getCurrentInventory()));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new AllotLocationAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        VerticalDecoration decoration = new VerticalDecoration(this.getContext());
        decoration.setHeight(this.getContext().getResources().getDimensionPixelSize(R.dimen.dp_16));
        decoration.setNeedDraw(false);
        recyclerView.addItemDecoration(decoration);
        RecyclerViewTouchListener listener = new RecyclerViewTouchListener(this, this::onItemClick);
        listener.setOnItemLongClickListener(this::onItemLongClick);
        recyclerView.addOnItemTouchListener(listener);

        mDeleteDialog = DialogFactory.createAlertDialog(this, getString(R.string.prompt_delete_confirm), this);
        mDeleteDialog.setOnDismissListener(dialog -> mDeletePosition = INVALID_POSITION);

        mService = NetServiceManager.getInstance().getService(AllotOrderService.class);


        getLocationList();
    }


    public void onClick(View view) {
        complete();
    }

    public void getLocationList() {
        Flowable<ResponseEntity<List<AllotLocationEntity>>> flowable = mService.getLocationList(mEntity.getDetailId());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<AllotLocationEntity>>(this) {
            @Override
            public void doNext(List<AllotLocationEntity> data) {
                mAdapter.setItems(data);
            }

            @Override
            public void doComplete() {

            }
        });
    }

    private void complete() {
        List<AllotLocationEntity> result = mAdapter.getItems();
        if (Validator.isEmpty(result)) {
            Toaster.showToast("请添加调入货位及数量.");
            return;
        }
        float amount = 0;
        for (AllotLocationEntity entity : result) {
            amount += entity.getAmount();
        }
        if (amount > mEntity.getAmount()) {
            Toaster.showToast("当前数量不能大于库存数量.");
            return;
        }
        AllotLocationRequestEntity entity = new AllotLocationRequestEntity();
        entity.setId(mEntity.getDetailId());
        entity.setBillId(mEntity.getBillId());
        entity.setParam(result);
        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.complete(entity);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                Toaster.showToast("已完成.");
            }

            @Override
            public void doComplete() {
                getEventBus().post(new AllotGoodsSelectedFragment.CompleteFlag());
                finish();
            }
        });
    }


    public void onItemClick(View view, int position) {
        mSelectPosition = position;
        AllotLocationEntity entity = mAdapter.getItem(position);
        AllotOrderLocationAddActivity.startActivity(this, entity);
    }

    public boolean onItemLongClick(View view, int position) {
        mDeletePosition = position;
        mDeleteDialog.show();
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mDeletePosition != INVALID_POSITION) {
            mAdapter.remove(mDeletePosition);
        }
    }
}
