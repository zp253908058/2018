package com.teeny.wms.page.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SimpleAdapter
 * @since 2017/10/12
 */

public class SimpleAdapter<T> extends ArrayAdapter<T> {
    public SimpleAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }
}
