package com.teeny.wms.page.shelve.fragment;

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
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.ShelveService;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.ShelveEntity;
import com.teeny.wms.page.barcode.BarcodeAddActivity;
import com.teeny.wms.page.document.controller.DocumentHelper;
import com.teeny.wms.page.shelve.ShelveAndStorageEditActivity;
import com.teeny.wms.page.shelve.adapter.ShelveAdapter;
import com.teeny.wms.page.shelve.helper.ShelveHelper;
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
 * @see ShelveAndStorageFragment
 * @since 2017/7/25
 */

public class ShelveAndStorageFragment extends BaseFragment implements RecyclerViewTouchListener.OnItemClickListener, AdapterView.OnItemClickListener {

    private static final String KEY_TYPE = "type";

    public static ShelveAndStorageFragment newInstance(int type) {
        ShelveAndStorageFragment fragment = new ShelveAndStorageFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    private int mType;
    private ShelveAdapter mAdapter = new ShelveAdapter(null);
    private EventBus mEventBus;
    private AlertDialog mOption1Dialog;
    private AlertDialog mOption2Dialog;
    private AlertDialog mShowDialog;
    private int mSelectPosition = -1;
    private ShelveHelper mHelper;

    public ShelveAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(KEY_TYPE);
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);

        mOption1Dialog = DialogFactory.createOptionMenuDialog(this.getContext(), R.array.option_1, this);
        mOption2Dialog = DialogFactory.createOptionMenuDialog(this.getContext(), R.array.option_2, this);
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
        RecyclerView recyclerView = findView(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        VerticalDecoration decoration = new VerticalDecoration(this.getContext());
        decoration.setHeight(this.getContext().getResources().getDimensionPixelSize(R.dimen.dp_16));
        recyclerView.addItemDecoration(decoration);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this.getContext(), this));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChange(ShelveHelper helper) {
        mHelper = helper;
        mAdapter.setItems(helper.getDataByType(mType));
    }

    @Override
    public void onItemClick(View view, int position) {
        ShelveEntity entity = mAdapter.getItem(position);
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
        ShelveEntity entity = mAdapter.getItem(mSelectPosition);
        switch (entity.getStatus()) {
            case 0:
                switch (position) {
                    case 0:
                        complete();
                        break;
                    case 2:
                        BarcodeAddActivity.startActivity(getContext(), entity.getGoodsId(), entity.getGoodsCode());
                        break;
                    case 1:
                    default:
                        ShelveAndStorageEditActivity.startActivity(getActivity(), entity);
                        break;
                }
                break;
            case 1:
            default:
                switch (position) {
                    case 1:
                        BarcodeAddActivity.startActivity(getContext(),entity.getGoodsId(), entity.getGoodsCode());
                        break;
                    case 0:
                    default:
                        ShelveAndStorageEditActivity.startActivity(getActivity(), entity);
                        break;
                }
                break;
        }
    }

    private void complete() {
        ShelveService service = NetServiceManager.getInstance().getService(ShelveService.class);
        Flowable<ResponseEntity<EmptyEntity>> flowable = service.single(mAdapter.getItem(mSelectPosition).getOriginalId());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                mHelper.reverseStatus(mAdapter.getItem(mSelectPosition).getOriginalId());
            }

            @Override
            public void doComplete() {
                Toaster.showToast("已完成.");
                DocumentHelper.getInstance().notifyDocumentChanged();
            }
        });
    }
}