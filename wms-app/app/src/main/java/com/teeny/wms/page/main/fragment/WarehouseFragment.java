package com.teeny.wms.page.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseFragment;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.decoration.GridDecoration;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.HomeService;
import com.teeny.wms.model.FunctionEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.page.allot.AllotListActivity;
import com.teeny.wms.page.allot.AllotOrderAddActivity;
import com.teeny.wms.page.barcode.BarcodeCollectActivity;
import com.teeny.wms.page.delivery.activity.ShopDeliveryActivity;
import com.teeny.wms.page.document.QueryDocumentActivity;
import com.teeny.wms.page.document.controller.DocumentHelper;
import com.teeny.wms.page.history.activity.HistoryGoodsActivity;
import com.teeny.wms.page.main.adapter.FunctionAdapter;
import com.teeny.wms.page.picking.activity.OutputPickingActivity;
import com.teeny.wms.page.receiving.ReceivingActivity;
import com.teeny.wms.page.review.ExWarehouseReviewActivity;
import com.teeny.wms.page.second.SecondInventoryActivity;
import com.teeny.wms.page.shelve.ShelveAndStorageActivity;
import com.teeny.wms.page.shop.ShopFirstActivity;
import com.teeny.wms.page.sku.SKUCheckActivity;
import com.teeny.wms.page.warehouse.WarehouseFirstActivity;
import com.teeny.wms.view.ProgressView;
import com.teeny.wms.widget.BadgeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description: 仓库界面
 *
 * @author zp
 * @version 1.0
 * @see WarehouseFragment
 * @since 2017/7/15
 */

public class WarehouseFragment extends BaseFragment implements View.OnClickListener, RecyclerViewTouchListener.OnItemClickListener, ProgressView {
    private static final String TAG = WarehouseFragment.class.getSimpleName();

    private static final int INDEX_COUNT = 13;

    public static WarehouseFragment newInstance() {
        return new WarehouseFragment();
    }

    private BadgeView mReceivingView;
    private BadgeView mPutawayView;
    private BadgeView mAllotView;
    private BadgeView mReviewView;
    private EventBus mEventBus = EventBus.getDefault();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventBus.register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEventBus.unregister(this);
    }

    @Override
    protected View onCreateHolderView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_warehouse_layout, container, false);
    }

    @Override
    protected void onInitialize() {
        mReceivingView = findView(R.id.warehouse_receiving_list);
        mPutawayView = findView(R.id.warehouse_putaway_list);
        mAllotView = findView(R.id.warehouse_allot_list);
        mReviewView = findView(R.id.warehouse_review_list);

        RecyclerView recyclerView = findView(R.id.recycler_view);
        FunctionAdapter adapter = new FunctionAdapter(createWarehouseData());
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this.getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        GridDecoration decoration = new GridDecoration(this.getContext(), getResources().getDimensionPixelSize(R.dimen.dp_1));
        decoration.setNeedDraw(false);
        recyclerView.addItemDecoration(decoration);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(getContext(), this));

        findView(R.id.warehouse_receiving_list).setOnClickListener(this);
        findView(R.id.warehouse_putaway_list).setOnClickListener(this);
        findView(R.id.warehouse_allot_list).setOnClickListener(this);
        findView(R.id.warehouse_review_list).setOnClickListener(this);

        DocumentHelper.getInstance().notifyDocumentChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDocumentUpdate(DocumentHelper helper) {
        mReceivingView.setBadge(helper.getAcceptanceCount());
        mPutawayView.setBadge(helper.getPutawayCount());
        mAllotView.setBadge(helper.getAllotCount());
        mReviewView.setBadge(helper.getReviewCount());
    }

    public List<FunctionEntity> createWarehouseData() {
        List<FunctionEntity> list = new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.function_array);
        for (int i = 0; i < INDEX_COUNT; i++) {
            FunctionEntity entity = new FunctionEntity();
            entity.setTitle(titles[i]);
            switch (i) {
                case 0:
                    entity.setIcon(R.mipmap.icon_receiving_acceptance);
                    break;
                case 1:
                    entity.setIcon(R.mipmap.icon_shelve_and_storage);
                    break;
                case 2:
                    entity.setIcon(R.mipmap.icon_allot_list);
                    break;
                case 3:
                    entity.setIcon(R.mipmap.icon_ex_warehouse_review);
                    break;
                case 4:
                    entity.setIcon(R.mipmap.icon_query_document);
                    break;
                case 5:
                    entity.setIcon(R.mipmap.icon_sku_check);
                    break;
//                case 6:
//                    entity.setIcon(R.mipmap.icon_shop_first);
//                    break;
                case 6:
                    entity.setIcon(R.mipmap.icon_warehouse_first);
                    break;
                case 7:
                    entity.setIcon(R.mipmap.icon_second_count);
                    break;
                case 8:
                    entity.setIcon(R.mipmap.icon_barcode_collect);
                    break;
                case 9:
                    entity.setIcon(R.mipmap.icon_allot_add);
                    break;
                case 10:
                    entity.setIcon(R.mipmap.icon_shop_receiving);
                    break;
                case 11:
                    entity.setIcon(R.mipmap.icon_output_picking);
                    break;
                case 12:
                    entity.setIcon(R.mipmap.icon_output_picking);
                    break;

            }
            list.add(entity);
        }
        return list;
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0:
                ReceivingActivity.startActivity(getContext(), null);
                break;
            case 1:
                ShelveAndStorageActivity.startActivity(getContext(), null);
                break;
            case 2:
                AllotListActivity.startActivity(getContext(), null);
                break;
            case 3:
                ExWarehouseReviewActivity.startActivity(getContext(), null);
                break;
            case 4:
                QueryDocumentActivity.startActivity(getContext(), 0);
                break;
            case 5:
                SKUCheckActivity.startActivity(getContext());
                break;
//            case 6:
//                ShopFirstActivity.startActivity(getContext());
//                break;
            case 6:
                WarehouseFirstActivity.startActivity(getContext());
                break;
            case 7:
                SecondInventoryActivity.startActivity(getContext());
                break;
            case 8:
                BarcodeCollectActivity.startActivity(getContext());
                break;
            case 9:
                AllotOrderAddActivity.startActivity(getContext());
                break;
            case 10:
                ShopDeliveryActivity.startActivity(getContext());
                break;
            case 11:
                OutputPickingActivity.startActivity(getContext());
                break;
            case 12:
                HistoryGoodsActivity.startActivity(getContext());
                break;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int type = 0;
        switch (id) {
            case R.id.warehouse_receiving_list:
                type = 1;
                break;
            case R.id.warehouse_putaway_list:
                type = 2;
                break;
            case R.id.warehouse_allot_list:
                type = 3;
                break;
            case R.id.warehouse_review_list:
                type = 4;
                break;
        }
        QueryDocumentActivity.startActivity(getContext(), type);
    }
}
