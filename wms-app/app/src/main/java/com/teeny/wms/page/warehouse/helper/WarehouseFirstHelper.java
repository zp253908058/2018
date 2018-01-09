package com.teeny.wms.page.warehouse.helper;

import android.support.v4.util.SparseArrayCompat;

import com.teeny.wms.model.WarehouseGoodsEntity;
import com.teeny.wms.page.common.helper.InventoryHelper;
import com.teeny.wms.util.ObjectUtils;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.log.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see WarehouseFirstHelper
 * @since 2017/8/23
 */

public class WarehouseFirstHelper extends InventoryHelper {

    @Override
    public void notifyChanged() {
        post(this);
    }
}