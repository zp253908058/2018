package com.teeny.wms.page.second;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.teeny.wms.page.common.activity.InventoryActivity;
import com.teeny.wms.page.common.fragment.InventoryHeaderFragment;
import com.teeny.wms.page.second.fragment.SecondInventoryFragment;
import com.teeny.wms.page.second.fragment.SecondInventoryHeaderFragment;

/**
 * Class description: 复盘
 *
 * @author zp
 * @version 1.0
 * @see SecondInventoryActivity
 * @since 2017/7/16
 */

public class SecondInventoryActivity extends InventoryActivity {
    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, SecondInventoryActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void startAdd(Context context, int key, String locationCode) {
        SecondInventoryAddActivity.startActivity(context, key, locationCode);
    }

    @Override
    protected InventoryHeaderFragment createHeaderFragment() {
        return SecondInventoryHeaderFragment.newInstance();
    }

    @Override
    protected Fragment createFragment(int position) {
        return SecondInventoryFragment.newInstance(position);
    }
}
