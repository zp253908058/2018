package com.teeny.wms.model.request;

import com.teeny.wms.model.AllotLocationEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotLocationRequestEntity
 * @since 2018/1/18
 */

public class AllotLocationRequestEntity {
    private int id;        //调拨单明细id
    private int billId;    //调拨单id
    private List<AllotLocationEntity> param;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public List<AllotLocationEntity> getParam() {
        return param;
    }

    public void setParam(List<AllotLocationEntity> param) {
        this.param = param;
    }
}
