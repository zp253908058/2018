package com.teeny.wms.model;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see InventoryCountEntity
 * @since 2017/12/27
 */

public class InventoryCountEntity {

    private int unfinishedNumber;

    private int finishedNumber;

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
