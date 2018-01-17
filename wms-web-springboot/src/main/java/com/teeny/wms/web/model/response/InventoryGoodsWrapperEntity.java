package com.teeny.wms.web.model.response;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see InventoryGoodsWrapperEntity
 * @since 2018/1/16
 */
public class InventoryGoodsWrapperEntity {
    private List<InventoryGoodsEntity> list;
    private int unfinishedNumber;
    private int finishedNumber;

    public List<InventoryGoodsEntity> getList() {
        return list;
    }

    public void setList(List<InventoryGoodsEntity> list) {
        this.list = list;
    }

    public int getUnfinishedNumber() {
        return unfinishedNumber;
    }

    public void setUnfinishedNumber(int unfinishedNumber) {
        this.unfinishedNumber = unfinishedNumber;
    }

    public int getFinishedNumber() {
        return finishedNumber;
    }

    public void setFinishedNumber(int finishedNumber) {
        this.finishedNumber = finishedNumber;
    }
}
