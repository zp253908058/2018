package com.teeny.wms.page.allot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.page.barcode.BarcodeCollectActivity;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotOrderAddActivity
 * @since 2018/1/4
 */

public class AllotOrderAddActivity extends ToolbarActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AllotOrderAddActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}