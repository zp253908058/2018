package com.teeny.wms.page.shop;

import android.app.Activity;
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
import com.teeny.wms.datasouce.net.service.ShopService;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.InventoryGoodsEntity;
import com.teeny.wms.model.LotEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.ShopInventoryRequestEntity;
import com.teeny.wms.page.common.activity.LotEditActivity;
import com.teeny.wms.page.common.adapter.LotAdapter;
import com.teeny.wms.page.common.fragment.InventoryHeaderFragment;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopFirstEditActivity
 * @since 2017/8/22
 */

public class ShopFirstEditActivity extends ToolbarActivity implements RecyclerViewTouchListener.OnItemClickListener, RecyclerViewTouchListener.OnItemLongClickListener, DialogInterface.OnClickListener {

    private static final String KEY_DATA = "data";

    public static void startActivity(Activity context, InventoryGoodsEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, ShopFirstEditActivity.class);
        intent.putExtra(KEY_DATA, entity);
        context.startActivity(intent);
    }

    private static final int REQUEST_CODE_EDIT = 0x01;
    private static final int INVALID_POSITION = -1;

    private LotAdapter mAdapter;
    private int mClickPosition = INVALID_POSITION;
    private int mDeletePosition = INVALID_POSITION;
    private AlertDialog mDeleteDialog;

    private InventoryGoodsEntity mEntity;
    private ShopService mService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_first_edit_layout);

        initView();
    }

    private void initView() {
        mEntity = getIntent().getParcelableExtra(KEY_DATA);

        KeyValueTextView nameView = (KeyValueTextView) findViewById(R.id.shop_first_edit_name);
        KeyValueTextView codeView = (KeyValueTextView) findViewById(R.id.shop_first_edit_code);
        KeyValueTextView specificationView = (KeyValueTextView) findViewById(R.id.shop_first_edit_specification);
        KeyValueTextView allocationView = (KeyValueTextView) findViewById(R.id.shop_first_edit_allocation);
        KeyValueTextView unitView = (KeyValueTextView) findViewById(R.id.shop_first_edit_unit);
        KeyValueTextView manufacturerView = (KeyValueTextView) findViewById(R.id.shop_first_edit_manufacturer);

        nameView.setValue(mEntity.getGoodsName());
        codeView.setValue(mEntity.getGoodsCode());
        specificationView.setValue(mEntity.getSpecification());
        allocationView.setValue(mEntity.getLocation());
        unitView.setValue(mEntity.getUnit());
        manufacturerView.setValue(mEntity.getManufacturer());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new LotAdapter(null);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        VerticalDecoration decoration = new VerticalDecoration(this.getContext());
        decoration.setHeight(this.getContext().getResources().getDimensionPixelSize(R.dimen.dp_16));
        recyclerView.addItemDecoration(decoration);
        RecyclerViewTouchListener listener = new RecyclerViewTouchListener(this, this);
        listener.setOnItemLongClickListener(this);
        recyclerView.addOnItemTouchListener(listener);

        mDeleteDialog = DialogFactory.createAlertDialog(this, getString(R.string.prompt_delete_confirm), this);
        mDeleteDialog.setOnDismissListener(dialog -> mDeletePosition = INVALID_POSITION);

        mService = NetServiceManager.getInstance().getService(ShopService.class);
        obtainData();
    }

    private void obtainData() {
        Flowable<ResponseEntity<List<LotEntity>>> flowable = mService.getLotList(mEntity.getOriginalId());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<LotEntity>>(this) {
            @Override
            public void doNext(List<LotEntity> data) {
                mAdapter.setItems(data);
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
                LotEditActivity.startActivityForResult(this, REQUEST_CODE_EDIT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        if (mAdapter.getItemCount() <= 0) {
            return;
        }
        ShopInventoryRequestEntity entity = new ShopInventoryRequestEntity();
        entity.setId(mEntity.getOriginalId());
        entity.setParam(mAdapter.getItems());
        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.edit(entity);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                getEventBus().post(new InventoryHeaderFragment.DataChangedObserver());
            }

            @Override
            public void doComplete() {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        mClickPosition = position;
        LotEditActivity.startActivityForResult(this, REQUEST_CODE_EDIT, mAdapter.getItem(position));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            mClickPosition = INVALID_POSITION;
            return;
        }
        if (requestCode == REQUEST_CODE_EDIT) {
            LotEntity entity = data.getParcelableExtra(LotEditActivity.KEY_DATA);
            if (mClickPosition == INVALID_POSITION) {
                mAdapter.append(entity);
            } else {
                mAdapter.update(entity, mClickPosition);
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        mDeletePosition = position;
        mDeleteDialog.show();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mDeletePosition != INVALID_POSITION) {
            mAdapter.remove(mDeletePosition);
        }
    }
}
