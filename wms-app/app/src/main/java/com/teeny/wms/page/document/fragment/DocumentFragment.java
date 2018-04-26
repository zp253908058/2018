package com.teeny.wms.page.document.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.teeny.wms.R;
import com.teeny.wms.base.BaseFragment;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.model.DocumentEntity;
import com.teeny.wms.page.document.QueryDocumentActivity;
import com.teeny.wms.page.receiving.ReceivingActivity;
import com.teeny.wms.page.allot.AllotListActivity;
import com.teeny.wms.page.document.adapter.DocumentAdapter;
import com.teeny.wms.page.document.controller.DocumentHelper;
import com.teeny.wms.page.review.ExWarehouseReviewActivity;
import com.teeny.wms.page.shelve.ShelveAndStorageActivity;
import com.teeny.wms.util.ObjectUtils;
import com.teeny.wms.util.log.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see DocumentFragment
 * @since 2017/7/18
 */

public class DocumentFragment extends BaseFragment implements RecyclerViewTouchListener.OnItemClickListener, TextWatcher {

    private static final String KEY_TYPE = "type";

    public static DocumentFragment newInstance(int type) {
        DocumentFragment fragment = new DocumentFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    private int mType;
    private DocumentAdapter mAdapter = new DocumentAdapter(null);
    private EventBus mEventBus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt(KEY_TYPE);
        mEventBus = EventBus.getDefault();
        mEventBus.register(this);
        mAdapter.setItems(DocumentHelper.getInstance().getDataByType(mType));
    }

    @Override
    public void onDestroy() {
        mEventBus.unregister(this);
        super.onDestroy();
    }

    @Override
    protected View onCreateHolderView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_document_layout, container, false);
    }

    @Override
    protected void onInitialize() {
        EditText filter = findView(R.id.document_filter_view);
        filter.removeTextChangedListener(this);
        filter.addTextChangedListener(this);
        RecyclerView recyclerView = findView(R.id.recycler_view);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        VerticalDecoration decoration = new VerticalDecoration(this.getContext());
        decoration.setNeedDraw(false);
        decoration.setHeight(Objects.requireNonNull(this.getContext()).getResources().getDimensionPixelSize(R.dimen.dp_16));
        recyclerView.addItemDecoration(decoration);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this.getContext(), this));
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onDataChange(DocumentHelper helper) {
        mAdapter.setItems(helper.getDataByType(mType));
    }

    @Override
    public void onItemClick(View view, int position) {
        DocumentEntity entity = mAdapter.getItem(position);
        int type = entity.getType();
        switch (type) {
            case 1:
                ReceivingActivity.startActivity(getContext(), entity);
                break;
            case 2:
                ShelveAndStorageActivity.startActivity(getContext(), entity);
                break;
            case 3:
                AllotListActivity.startActivity(getContext(), entity);
                break;
            case 4:
                ExWarehouseReviewActivity.startActivity(getContext(), entity);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mAdapter.getFilter().filter(s);
    }
}
