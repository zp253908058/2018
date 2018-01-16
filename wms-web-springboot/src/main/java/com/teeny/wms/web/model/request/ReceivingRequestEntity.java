package com.teeny.wms.web.model.request;

import com.teeny.wms.web.model.response.ReceivingAddEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReceivingRequestEntity
 * @since 2017/11/1
 */
public class ReceivingRequestEntity {

    private int id;                           //original_id
    private int smbId;                        //单据明细id
    private List<ReceivingAddEntity> param;

    public int getSmbId() {
        return smbId;
    }

    public void setSmbId(int smbId) {
        this.smbId = smbId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ReceivingAddEntity> getParam() {
        return param;
    }

    public void setParam(List<ReceivingAddEntity> param) {
        this.param = param;
    }
}
