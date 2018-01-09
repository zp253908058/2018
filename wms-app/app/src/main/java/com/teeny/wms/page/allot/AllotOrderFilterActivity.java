package com.teeny.wms.page.allot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarActivity;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotOrderFilterActivity
 * @since 2018/1/9
 */

public class AllotOrderFilterActivity extends ToolbarActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, AllotOrderFilterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_allot_order_filter_layout);
    }
}
