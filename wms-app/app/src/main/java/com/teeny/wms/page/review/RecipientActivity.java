package com.teeny.wms.page.review;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.ReviewService;
import com.teeny.wms.model.RecipientEntity;
import com.teeny.wms.model.ResponseEntity;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see RecipientActivity
 * @since 2017/9/20
 */

public class RecipientActivity extends ToolbarActivity implements RecyclerViewTouchListener.OnItemClickListener, TextWatcher {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RecipientActivity.class);
        context.startActivity(intent);
    }

    private RecipientAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_layout);

        initView();
    }

    private void initView() {
        EditText filter = (EditText) findViewById(R.id.recipient_filter_view);
        filter.removeTextChangedListener(this);
        filter.addTextChangedListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new RecipientAdapter(null);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        VerticalDecoration decoration = new VerticalDecoration(this.getContext());
        decoration.setNeedDraw(false);
        decoration.setHeight(2);
        recyclerView.addItemDecoration(decoration);
        recyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(this.getContext(), this));

        obtainData();
    }

    private void obtainData() {
        ReviewService service = NetServiceManager.getInstance().getService(ReviewService.class);
        Flowable<ResponseEntity<List<RecipientEntity>>> flowable = service.getRecipient();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<RecipientEntity>>(this) {
            @Override
            public void doNext(List<RecipientEntity> data) {
                mAdapter.setItems(data);
            }

            @Override
            public void doComplete() {

            }
        });
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

    @Override
    public void onItemClick(View view, int position) {
        getEventBus().post(mAdapter.getItem(position));
        finish();
    }
}
