package com.teeny.wms.model;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see OutputPickingOrderEntity
 * @since 2018/1/21
 */

public class OutputPickingOrderEntity {

    private int id;               //单据id
    private String number;        //单据编号
    private int completed;
    private int total;
    private String warehouse;
    private String clerk;
    private String shopName;
    private float totalMoney;
    private List<OutputPickingItemEntity> dataList;
    private List<OutputPickingEntity> turnoverList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getClerk() {
        return clerk;
    }

    public void setClerk(String clerk) {
        this.clerk = clerk;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<OutputPickingItemEntity> getDataList() {
        return dataList;
    }

    public void setDataList(List<OutputPickingItemEntity> dataList) {
        this.dataList = dataList;
    }

    public String getProgress() {
        return (completed + 1) + "/" + total;
    }

    public List<OutputPickingEntity> getTurnoverList() {
        return turnoverList;
    }

    public void setTurnoverList(List<OutputPickingEntity> turnoverList) {
        this.turnoverList = turnoverList;
    }
}
