package com.teeny.wms.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.teeny.wms.R;
import com.teeny.wms.util.WindowUtils;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ToolbarFragment
 * @since 2017/7/15
 */

public abstract class ToolbarFragment extends BaseFragment {
    @Override
    protected View onCreateHolderView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.activity_base_toolbar_layout, container, false);
        FrameLayout contentLayout = (FrameLayout) view.findViewById(R.id.base_toolbar_content_layout);
        contentLayout.setOnClickListener(WindowUtils::hideInputSoft);
        inflater.inflate(getContentLayoutId(), contentLayout);
        return view;
    }

    @LayoutRes
    protected abstract int getContentLayoutId();

    protected Toolbar getToolbar() {
        return findView(R.id.toolbar);
    }
}
