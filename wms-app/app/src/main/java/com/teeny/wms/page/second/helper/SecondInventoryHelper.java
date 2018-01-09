package com.teeny.wms.page.second.helper;

import android.support.v4.util.SparseArrayCompat;

import com.teeny.wms.model.SecondInventoryGoodsEntity;
import com.teeny.wms.page.common.helper.InventoryHelper;
import com.teeny.wms.util.ObjectUtils;
import com.teeny.wms.util.Validator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SecondInventoryHelper
 * @since 2017/8/20
 */

public class SecondInventoryHelper extends InventoryHelper{

    @Override
    public void notifyChanged() {
        post(this);
    }
}
