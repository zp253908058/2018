package com.teeny.wms.page.warehouse.helper;

import com.teeny.wms.page.common.helper.InventoryHelper;

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