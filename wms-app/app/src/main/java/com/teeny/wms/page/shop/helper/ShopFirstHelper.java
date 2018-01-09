package com.teeny.wms.page.shop.helper;

import com.teeny.wms.page.common.helper.InventoryHelper;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopFirstHelper
 * @since 2017/8/20
 */

public class ShopFirstHelper extends InventoryHelper{

    @Override
    public void notifyChanged() {
        post(this);
    }
}
