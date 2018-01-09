package com.teeny.wms.page.allot;

import android.content.Context;
import android.content.Intent;

import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.model.AllotGoodsEntity;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotOrderAddDetailActivity
 * @since 2018/1/9
 */

public class AllotOrderAddDetailActivity extends ToolbarActivity {

    public static void startActivity(Context context, AllotGoodsEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, AllotOrderAddDetailActivity.class);
        context.startActivity(intent);
    }
}
