package com.teeny.wms.page.allot.fragment;

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
import com.teeny.wms.page.allot.AllotOrderAddDetailActivity;
import com.teeny.wms.page.allot.adapter.AllotGoodsSelectedAdapter;
import com.teeny.wms.page.allot.helper.AllotOrderHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotGoodsSelectedFragment
 * @since 2018/1/9
 */

public class AllotGoodsSelectedFragment extends BaseFragment implements RecyclerViewTouchListener.OnItemClickListener {
    private static final Object TAG = new Object();

    public static AllotGoodsSelectedFragment newInstance() {
        return new AllotGoodsSelectedFragment();
    }

    private AllotGoodsSelectedAdapter mAdapter;

    private AllotOrderHelper mHelper;
    private EventBus mEventBus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventBus = EventBus.getDefault();
        mAdapter = new AllotGoodsSelectedAdapter(null);
    }

    @Override
    public void onDestroy() {
        mEventBus.unregister(this);
        super.onDestroy();
    }

    @Override
    protected View onCreateHolderView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_recycler_view, container, false);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        RecyclerView recyclerView = findView(R.id.recycler_view);
        if (recyclerView.getTag() == null) {
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            VerticalDecoration decoration = new VerticalDecoration(this.getContext());
            decoration.setHeight(this.getContext().getResources().getDimensionPixelSize(R.dimen.dp_16));
            recyclerView.addItemDecoration(decoration);
            recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this.getContext(), this));
            recyclerView.setTag(TAG);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChange(AllotOrderHelper helper) {
        mHelper = helper;
        mAdapter.setItems(helper.getSelectedItems());
    }

    @Override
    public void onItemClick(View view, int position) {
        AllotOrderAddDetailActivity.startActivity(getContext(), mAdapter.getItem(position));
    }
}
