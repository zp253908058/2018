package com.teeny.wms.page.main;

import android.app.Activity;
import android.content.Intent;

import com.teeny.wms.base.ToolbarActivity;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see WarehouseSwitchActivity
 * @since 2017/7/15
 */

public class WarehouseSwitchActivity extends ToolbarActivity {

    public static void startActivityForResult(Activity context, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(context, WarehouseSwitchActivity.class);
        context.startActivityForResult(intent, requestCode);
    }
}
