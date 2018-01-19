package com.teeny.wms.page.second.helper;

import com.teeny.wms.page.common.helper.InventoryHelper;

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
