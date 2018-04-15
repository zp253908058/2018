package com.teeny.wms.page.second.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseFragment;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.SecondInventoryService;
import com.teeny.wms.model.InventoryGoodsEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.SecondInventoryGoodsEntity;
import com.teeny.wms.page.second.SecondInventoryEditActivity;
import com.teeny.wms.page.second.adapter.SecondInventoryGoodsAdapter;
import com.teeny.wms.page.second.helper.SecondInventoryHelper;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SecondInventoryFragment
 * @since 2017/8/20
 */

public class SecondInventoryFragment extends BaseFragment implements RecyclerViewTouchListener.OnItemClickListener, AdapterView.OnItemClickListener {

    private static final String KEY_TYPE = "type";
    private static final Object TAG = new Object();
    private static final int INVALID_POSITION = -1;

    public static SecondInventoryFragment newInstance(int type) {
        SecondInventoryFragment fragment = new SecondInventoryFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    private int mType;
    private SecondInventoryGoodsAdapter mAdapter = new SecondInventoryGoodsAdapter(null);
    private EventBus mEventBus;
    private AlertDialog mOptionDialog;
    private int mSelectPosition = INVALID_POSITION;
    private SecondInventoryHelper mHelper;

    public SecondInventoryGoodsAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(KEY_TYPE);
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
        switch (mType) {
            case 0:
                mOptionDialog = DialogFactory.createOptionMenuDialog(this.getContext(), R.array.pd_option_1, this);
                break;
            case 1:
                mOptionDialog = DialogFactory.createOptionMenuDialog(this.getContext(), R.array.pd_option_2, this);
                break;
        }
        if (mOptionDialog != null) {
            mOptionDialog.setOnDismissListener(this::resetPosition);
        }
    }

    private void resetPosition(DialogInterface dialog) {
        mSelectPosition = INVALID_POSITION;
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
    public void onDataChange(SecondInventoryHelper helper) {
        mHelper = helper;
        mAdapter.setItems(helper.getDataByType(mType));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onFilter(Editable editable) {
        mAdapter.getFilter().filter(editable);
    }

    @Override
    public void onItemClick(View view, int position) {
        mOptionDialog.show();
        mSelectPosition = position;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mOptionDialog.dismiss();
        InventoryGoodsEntity entity = mAdapter.getItem(mSelectPosition);
        switch (mType) {
            case 0:
                if (position == 0) {
                    complete();
                    return;
                }
            case 1:
            default:
                SecondInventoryEditActivity.startActivity(getActivity(), entity);
                break;
        }
    }

    private void complete() {
        SecondInventoryService service = NetServiceManager.getInstance().getService(SecondInventoryService.class);
        Flowable<ResponseEntity<Integer>> flowable = service.single(mAdapter.getItem(mSelectPosition).getOriginalId());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<Integer>(this) {
            @Override
            public void doNext(Integer id) {
                mHelper.reverseStatus(id);
            }

            @Override
            public void doComplete() {
                Toaster.showToast("已完成.");
            }
        });
    }
}
