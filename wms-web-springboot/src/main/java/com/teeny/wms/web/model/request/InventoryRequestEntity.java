package com.teeny.wms.web.model.request;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see InventoryRequestEntity
 * @since 2017/8/20
 */

public class InventoryRequestEntity {

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
