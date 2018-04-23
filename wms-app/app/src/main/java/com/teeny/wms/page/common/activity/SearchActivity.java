package com.teeny.wms.page.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.FilterRecyclerAdapter;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.WindowUtils;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SearchActivity
 * @since 2018/4/21
 */
public abstract class SearchActivity<T> extends ToolbarActivity implements TextWatcher, TextView.OnEditorActionListener {

    private EditText mSearchTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);
        initView();
    }

    protected void initView() {
        mSearchTextView = findViewById(R.id.search);

        int type = getType();
        switch (type) {
            case 1:
                mSearchTextView.addTextChangedListener(this);
                break;
            case 2:
                mSearchTextView.setOnEditorActionListener(this);
                mScannerHelper.openScanner(this, this::handleResult);
                break;
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        FilterRecyclerAdapter<T> adapter = getAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setBackground(ResourcesCompat.getDrawable(getResources(), R.color.white, null));
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        manager.setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(manager);
        VerticalDecoration decoration = new VerticalDecoration(this.getContext());
        decoration.setHeight(this.getContext().getResources().getDimensionPixelSize(R.dimen.dp_1));
        decoration.setNeedDraw(true);
        recyclerView.addItemDecoration(decoration);
        RecyclerViewTouchListener listener = new RecyclerViewTouchListener(this, this::onItemClick);
        listener.setOnItemLongClickListener(this::onItemLongClick);
        recyclerView.addOnItemTouchListener(listener);

        if (getType() == 1) {
            onObtainData();
        }
    }

    private void handleResult(String result) {
        mSearchTextView.setText(result);
        onSearch(result);
    }

    @Override
    protected void onDestroy() {
        if (getType() == 2) {
            mScannerHelper.unregisterReceiver(this);
        }
        super.onDestroy();
    }

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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            //执行搜索的代码
            onSearch(mSearchTextView.getText().toString());
            WindowUtils.hideInputSoft(v);
            return true;
        }
        return false;
    }

    protected void onItemClick(View view, int i) {
    }

    protected void onItemLongClick(View view, int i) {
    }

    protected void setHint(CharSequence hint) {
        mSearchTextView.setHint(hint);
    }

    public void setHint(@StringRes int resId) {
        mSearchTextView.setHint(resId);
    }

    protected abstract FilterRecyclerAdapter<T> getAdapter();

    /**
     * @return 1过滤    2搜索
     */
    protected abstract int getType();

    protected void onSearch(String condition) {

    }

    protected void onObtainData() {

    }
}
