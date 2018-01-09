package com.teeny.wms.model.request;

import com.teeny.wms.model.ReceivingLotEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReceivingRequestEntity
 * @since 2017/8/24
 */

public class ReceivingRequestEntity {

    private int id;
    private int smbId;
    private List<ReceivingLotEntity> param;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSmbId() {
        return smbId;
    }

    public void setSmbId(int smbId) {
        this.smbId = smbId;
    }

    public List<ReceivingLotEntity> getParam() {
        return param;
    }

    public void setParam(List<ReceivingLotEntity> param) {
        this.param = param;
    }
}
