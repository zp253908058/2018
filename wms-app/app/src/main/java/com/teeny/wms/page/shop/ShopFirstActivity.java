package com.teeny.wms.page.shop;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.teeny.wms.page.common.activity.InventoryActivity;
import com.teeny.wms.page.common.fragment.InventoryHeaderFragment;
import com.teeny.wms.page.shop.fragment.ShopHeaderFragment;
import com.teeny.wms.page.shop.fragment.ShopInventoryFragment;

/**
 * Class description: 门店初盘
 *
 * @author zp
 * @version 1.0
 * @see ShopFirstActivity
 * @since 2017/7/16
 */

public class ShopFirstActivity extends InventoryActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, ShopFirstActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void startAdd(Context context, int key, String locationCode) {
        ShopFirstAddActivity.startActivity(context, key, locationCode);
    }

    @Override
    protected InventoryHeaderFragment createHeaderFragment() {
        return ShopHeaderFragment.newInstance();
    }

    @Override
    protected Fragment createFragment(int position) {
        return ShopInventoryFragment.newInstance(position);
    }
}
