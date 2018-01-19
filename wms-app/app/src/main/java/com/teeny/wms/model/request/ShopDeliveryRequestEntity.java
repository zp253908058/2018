package com.teeny.wms.model.request;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopDeliveryRequestEntity
 * @since 2018/1/18
 */

public class ShopDeliveryRequestEntity {

    private int id;

    private List<Integer> ids;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
