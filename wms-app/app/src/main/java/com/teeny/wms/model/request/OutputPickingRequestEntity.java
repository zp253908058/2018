package com.teeny.wms.model.request;

import com.teeny.wms.model.OutputPickingEntity;

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
    private int number;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<OutputPickingEntity> getList() {
        return list;
    }

    public void setList(List<OutputPickingEntity> list) {
        this.list = list;
    }
}
