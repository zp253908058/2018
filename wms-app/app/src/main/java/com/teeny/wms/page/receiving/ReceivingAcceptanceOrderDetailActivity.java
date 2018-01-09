package com.teeny.wms.page.receiving;

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
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.ReceivingService;
import com.teeny.wms.model.ReceivingLotEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ReceivingItemEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.ReceivingRequestEntity;
import com.teeny.wms.page.receiving.adapter.AcceptanceLotAdapter;
import com.teeny.wms.page.document.controller.DocumentHelper;
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
 * Class description: 验收单明细
 *
 * @author zp
 * @version 1.0
 * @see ReceivingAcceptanceOrderDetailActivity
 * @since 2017/7/20
 */

public class ReceivingAcceptanceOrderDetailActivity extends ToolbarActivity implements DialogInterface.OnClickListener {

    private static final String KEY_ENTITY = "entity";
    private static final int INVALID_POSITION = -1;

    public static void startActivity(Context context, ReceivingItemEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, ReceivingAcceptanceOrderDetailActivity.class);
        intent.putExtra(KEY_ENTITY, entity);
        context.startActivity(intent);
    }

    private ReceivingItemEntity mEntity;
    private AcceptanceLotAdapter mAdapter;

    private ReceivingService mService;

    private int mSelectPosition = INVALID_POSITION;
    private int mDeletePosition = INVALID_POSITION;
    private AlertDialog mDeleteDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving_acceptance_order_detail_layout);
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
                AcceptanceLotAddActivity.startActivity(this, mEntity.getRate(), mEntity.getZhUnit(), mEntity.getLhUnit());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLotAdd(ReceivingLotEntity entity) {
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

        TextView nameView = (TextView) findViewById(R.id.receiving_acceptance_order_detail_name);
        TextView specificationView = (TextView) findViewById(R.id.receiving_acceptance_order_detail_specification);
        TextView manufacturerView = (TextView) findViewById(R.id.receiving_acceptance_order_detail_manufacturer);
        TextView makeAreaView = (TextView) findViewById(R.id.receiving_acceptance_order_detail_make_area);

        nameView.setText(mEntity.getGoodsName());
        specificationView.setText(mEntity.getSpecification());
        manufacturerView.setText(mEntity.getManufacturer());
        makeAreaView.setText(mEntity.getProduceArea());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new AcceptanceLotAdapter(new ArrayList<>());
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

        mService = NetServiceManager.getInstance().getService(ReceivingService.class);

        getLotList();
    }

    private void getLotList() {
        Flowable<ResponseEntity<List<ReceivingLotEntity>>> flowable = mService.getLotList(mEntity.getOriginalId());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<ReceivingLotEntity>>(this) {
            @Override
            public void doNext(List<ReceivingLotEntity> data) {
                mAdapter.setItems(data);
            }

            @Override
            public void doComplete() {

            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.receiving_acceptance_order_detail_complete:
                complete();
                break;
        }
    }

    private void complete() {
        List<ReceivingLotEntity> result = mAdapter.getItems();
        if (Validator.isEmpty(result)) {
            Toaster.showToast("请添加批号.");
            return;
        }
        ReceivingRequestEntity entity = new ReceivingRequestEntity();
        entity.setId(mEntity.getOriginalId());
        entity.setParam(result);
        entity.setSmbId(mEntity.getId());
        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.complete(entity);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                Toaster.showToast("已完成.");
            }

            @Override
            public void doComplete() {
                ReceivingAcceptanceActivity.CompleteFlag flag = new ReceivingAcceptanceActivity.CompleteFlag();
                getEventBus().post(flag);
                DocumentHelper.getInstance().notifyDocumentChanged();
                finish();
            }
        });
    }


    public void onItemClick(View view, int position) {
        mSelectPosition = position;
        ReceivingLotEntity entity = mAdapter.getItem(position);
        AcceptanceLotAddActivity.startActivity(this, entity);
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
