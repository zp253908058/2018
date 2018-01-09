package com.teeny.wms.page.second.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teeny.wms.page.common.fragment.InventoryHeaderFragment;
import com.teeny.wms.page.common.helper.InventoryHelper;
import com.teeny.wms.page.second.helper.SecondInventoryHelper;
import com.teeny.wms.page.warehouse.fragment.WarehouseHeaderFragment;
import com.teeny.wms.page.warehouse.helper.WarehouseFirstHelper;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SecondInventoryHeaderFragment
 * @since 2017/12/28
 */

public class SecondInventoryHeaderFragment extends InventoryHeaderFragment {
    public static SecondInventoryHeaderFragment newInstance() {
        return new SecondInventoryHeaderFragment();
    }

    private SecondInventoryHelper mHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getPdType() {
        return 2;
    }

    @Override
    public InventoryHelper getHelper() {
        if (mHelper == null) {
            mHelper = new SecondInventoryHelper();
        }
        return mHelper;
    }
}
