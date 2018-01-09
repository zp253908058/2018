package com.teeny.wms.page.main.fragment;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarFragment;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see MeFragment
 * @since 2017/7/15
 */

public class MeFragment extends ToolbarFragment {

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_warehouse_layout;
    }
}
