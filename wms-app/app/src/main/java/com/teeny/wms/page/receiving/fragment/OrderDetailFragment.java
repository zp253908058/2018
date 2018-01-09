package com.teeny.wms.page.receiving.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseFragment;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.model.ReceivingItemEntity;
import com.teeny.wms.model.ShelveEntity;
import com.teeny.wms.page.barcode.BarcodeAddActivity;
import com.teeny.wms.page.receiving.ReceivingOrderDetailActivity;
import com.teeny.wms.page.receiving.adapter.ReceivingAdapter;
import com.teeny.wms.page.receiving.helper.ReceivingHelper;
import com.teeny.wms.page.shelve.ShelveAndStorageEditActivity;
import com.teeny.wms.pop.DialogFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see OrderDetailFragment
 * @since 2017/7/20
 */

public class OrderDetailFragment extends BaseFragment implements RecyclerViewTouchListener.OnItemClickListener, AdapterView.OnItemClickListener {

    private static final String KEY_TYPE = "type";

    public static OrderDetailFragment newInstance(int type) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    private ReceivingHelper mHelper;
    private int mType;
    private ReceivingAdapter mAdapter = new ReceivingAdapter(null);
    private EventBus mEventBus = EventBus.getDefault();

    private AlertDialog mOption1Dialog;
    private AlertDialog mOption2Dialog;
    private AlertDialog mShowDialog;
    private int mSelectPosition = -1;

    public ReceivingAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(KEY_TYPE);
        mEventBus.register(this);

        mOption1Dialog = DialogFactory.createOptionMenuDialog(this.getContext(), R.array.option_3, this);
        mOption2Dialog = DialogFactory.createOptionMenuDialog(this.getContext(), R.array.option_4, this);
    }

    @Override
    protected View onCreateHolderView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_recycler_view, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mEventBus.unregister(this);
    }

    @Override
    protected void onInitialize() {
        RecyclerView recyclerView = findView(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        VerticalDecoration decoration = new VerticalDecoration(this.getContext());
        decoration.setHeight(this.getContext().getResources().getDimensionPixelSize(R.dimen.dp_16));
        recyclerView.addItemDecoration(decoration);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this.getContext(), this));
    }

    @Override
    public void onItemClick(View view, int position) {
//        ReceivingItemEntity entity = mAdapter.getItem(position);
//        if (entity.getStatus() == 0) {
//            ReceivingOrderDetailActivity.startActivity(getContext(), entity);
//        }

        ReceivingItemEntity entity = mAdapter.getItem(position);
        if (entity.getStatus() == 0) {
            mOption1Dialog.show();
            mShowDialog = mOption1Dialog;
        } else {
            mOption2Dialog.show();
            mShowDialog = mOption2Dialog;
        }
        mSelectPosition = position;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mShowDialog.dismiss();
        ReceivingItemEntity entity = mAdapter.getItem(mSelectPosition);
        switch (entity.getStatus()) {
            case 0:
            default:
                switch (position) {
                    case 1:
                        BarcodeAddActivity.startActivity(getContext(), entity.getGoodsId(), entity.getBarcode());
                        break;
                    case 0:
                    default:
                        ReceivingOrderDetailActivity.startActivity(getActivity(), entity);
                        break;
                }
                break;
            case 1:
                BarcodeAddActivity.startActivity(getContext(), entity.getGoodsId(), entity.getBarcode());
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChanged(ReceivingHelper helper) {
        mHelper = helper;
        mAdapter.setItems(helper.getDataByType(mType));
    }
}