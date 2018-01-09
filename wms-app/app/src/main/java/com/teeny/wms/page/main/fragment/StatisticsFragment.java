package com.teeny.wms.page.main.fragment;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarFragment;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see StatisticsFragment
 * @since 2017/7/15
 */

public class StatisticsFragment extends ToolbarFragment {

    public static StatisticsFragment newInstance() {
        return new StatisticsFragment();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_warehouse_layout;
    }
}
