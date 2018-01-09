package com.teeny.wms.page.warehouse.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teeny.wms.page.common.fragment.InventoryHeaderFragment;
import com.teeny.wms.page.common.helper.InventoryHelper;
import com.teeny.wms.page.shop.fragment.ShopHeaderFragment;
import com.teeny.wms.page.shop.helper.ShopFirstHelper;
import com.teeny.wms.page.warehouse.helper.WarehouseFirstHelper;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see WarehouseHeaderFragment
 * @since 2017/12/28
 */

public class WarehouseHeaderFragment extends InventoryHeaderFragment {

    public static WarehouseHeaderFragment newInstance() {
        return new WarehouseHeaderFragment();
    }

    private WarehouseFirstHelper mHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getPdType() {
        return 4;
    }

    @Override
    public InventoryHelper getHelper() {
        if (mHelper == null) {
            mHelper = new WarehouseFirstHelper();
        }
        return mHelper;
    }
}