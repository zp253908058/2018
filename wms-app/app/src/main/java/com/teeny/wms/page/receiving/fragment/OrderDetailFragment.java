package com.teeny.wms.page.receiving.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseFragment;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.model.ReceivingItemEntity;
import com.teeny.wms.page.receiving.ReceivingAcceptanceOrderDetailActivity;
import com.teeny.wms.page.receiving.adapter.ReceivingAcceptanceAdapter;
import com.teeny.wms.page.receiving.helper.AcceptanceHelper;

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

public class OrderDetailFragment extends BaseFragment implements RecyclerViewTouchListener.OnItemClickListener {

    private static final String KEY_TYPE = "type";

    public static OrderDetailFragment newInstance(int type) {
        OrderDetailFragment fragment = new OrderDetailFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    private AcceptanceHelper mHelper;
    private int mType;
    private ReceivingAcceptanceAdapter mAdapter = new ReceivingAcceptanceAdapter(null);
    private EventBus mEventBus = EventBus.getDefault();

    public ReceivingAcceptanceAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(KEY_TYPE);
        mEventBus.register(this);
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
        ReceivingItemEntity entity = mAdapter.getItem(position);
        if (entity.getStatus() == 0) {
            ReceivingAcceptanceOrderDetailActivity.startActivity(getContext(), entity);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChanged(AcceptanceHelper helper) {
        mHelper = helper;
        mAdapter.setItems(helper.getDataByType(mType));
    }
}