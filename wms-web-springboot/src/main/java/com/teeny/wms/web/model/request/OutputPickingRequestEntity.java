package com.teeny.wms.web.model.request;

import com.teeny.wms.web.model.response.OutputPickingEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see OutputPickingRequestEntity
 * @since 2018/1/21
 */
public class OutputPickingRequestEntity {

    private int id;
    private int detailId;
    private float number;
    private List<OutputPickingEntity> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public List<OutputPickingEntity> getList() {
        return list;
    }

    public void setList(List<OutputPickingEntity> list) {
        this.list = list;
    }
}
