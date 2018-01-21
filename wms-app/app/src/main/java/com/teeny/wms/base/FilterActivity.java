package com.teeny.wms.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.teeny.wms.R;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.page.picking.adapter.OutputPickingAdapter;

import java.util.ArrayList;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see FilterActivity
 * @since 2018/1/21
 */

public abstract class FilterActivity<T> extends ToolbarActivity {

    private EditText mFilterView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_layout);

        initView();
    }

    protected abstract RecyclerFilterAdapter<T> getAdapter();

    private void initView() {
        mFilterView = (EditText) findViewById(R.id.filter_condition);
        mFilterView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getAdapter().getFilter().filter(s);
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(getAdapter());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(manager);
        VerticalDecoration decoration = new VerticalDecoration(this.getContext());
        decoration.setHeight(this.getContext().getResources().getDimensionPixelSize(R.dimen.dp_16));
        decoration.setNeedDraw(false);
        recyclerView.addItemDecoration(decoration);
        RecyclerViewTouchListener listener = new RecyclerViewTouchListener(this, this::onItemClick);
        listener.setOnItemLongClickListener(this::onItemLongClick);
        recyclerView.addOnItemTouchListener(listener);
    }

    protected void onItemClick(View view, int i) {
    }

    protected void onItemLongClick(View view, int i) {
    }

    protected void setHint(CharSequence hint) {
        mFilterView.setHint(hint);
    }

    public void setHint(@StringRes int resId) {
        mFilterView.setHint(resId);
    }
}
