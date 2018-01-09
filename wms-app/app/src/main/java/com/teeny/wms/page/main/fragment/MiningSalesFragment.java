package com.teeny.wms.page.main.fragment;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarFragment;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see MiningSalesFragment
 * @since 2017/7/15
 */

public class MiningSalesFragment extends ToolbarFragment {

    public static MiningSalesFragment newInstance() {
        return new MiningSalesFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_warehouse_layout;
    }
}
