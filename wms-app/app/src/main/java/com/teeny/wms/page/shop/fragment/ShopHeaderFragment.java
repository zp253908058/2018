package com.teeny.wms.page.shop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.service.ShopService;
import com.teeny.wms.page.common.fragment.InventoryHeaderFragment;
import com.teeny.wms.page.common.helper.InventoryHelper;
import com.teeny.wms.page.shop.helper.ShopFirstHelper;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopHeaderFragment
 * @since 2017/12/26
 */

public class ShopHeaderFragment extends InventoryHeaderFragment {

    public static ShopHeaderFragment newInstance() {
        return new ShopHeaderFragment();
    }

    private ShopFirstHelper mHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getPdType() {
        return 1;
    }

    @Override
    public InventoryHelper getHelper() {
        if (mHelper == null) {
            mHelper = new ShopFirstHelper();
        }
        return mHelper;
    }
}
