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
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.AllotListService;
import com.teeny.wms.datasouce.net.service.AllotOrderService;
import com.teeny.wms.model.AllotGoodsEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.page.allot.AllotOrderAddDetailActivity;
import com.teeny.wms.page.allot.adapter.AllotGoodsQueryAdapter;
import com.teeny.wms.page.allot.helper.AllotOrderHelper;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.log.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotGoodsQueryFragment
 * @since 2018/1/9
 */

public class AllotGoodsQueryFragment extends BaseFragment implements RecyclerViewTouchListener.OnItemClickListener {

    private static final Object TAG = new Object();

    public static AllotGoodsQueryFragment newInstance() {
        return new AllotGoodsQueryFragment();
    }

    private AllotGoodsQueryAdapter mAdapter;

    private AllotOrderHelper mHelper;
    private EventBus mEventBus;

    private AllotOrderService mService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
        mService = NetServiceManager.getInstance().getService(AllotOrderService.class);
        mAdapter = new AllotGoodsQueryAdapter(null);
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
        mAdapter.setItems(helper.getItems());
    }

    @Override
    public void onItemClick(View view, int position) {
        final AllotGoodsEntity entity = mAdapter.getItem(position);
        Flowable<ResponseEntity<AllotGoodsEntity>> flowable = mService.select(entity.getId());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<AllotGoodsEntity>(this) {
            @Override
            public void doNext(AllotGoodsEntity data) {
                mEventBus.post(new AllotGoodsSelectedFragment.SelectedFlag());
                AllotOrderAddDetailActivity.startActivity(getContext(), data);
            }

            @Override
            public void doComplete() {
                entity.setSelected(true);
                mAdapter.update(entity, position);
            }
        });

    }
}
