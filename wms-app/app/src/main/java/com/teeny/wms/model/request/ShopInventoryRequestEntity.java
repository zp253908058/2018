package com.teeny.wms.model.request;

import com.teeny.wms.model.LotEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopInventoryRequestEntity
 * @since 2017/8/20
 */

public class ShopInventoryRequestEntity {

    private int id;

    private List<LotEntity> param;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<LotEntity> getParam() {
        return param;
    }

    public void setParam(List<LotEntity> param) {
        this.param = param;
    }
}
