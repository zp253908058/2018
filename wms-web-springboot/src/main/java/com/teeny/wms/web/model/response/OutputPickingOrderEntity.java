package com.teeny.wms.web.model.response;

import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see OutputPickingOrderEntity
 * @since 2018/1/21
 */
@Alias("OutputPickingOrderEntity")
public class OutputPickingOrderEntity {
    private int id;               //单据id
    private String number;        //单据编号
    private String deskName;      //复核台
    private String clientName;    //往来单位
    private String orderRemark;   //订单备注
    private String originalType;  //原单类型
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

    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public String getOriginalType() {
        return originalType;
    }

    public void setOrginalType(String originalType) {
        this.originalType = originalType;
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

    public List<OutputPickingEntity> getTurnoverList() {
        return turnoverList;
    }

    public void setTurnoverList(List<OutputPickingEntity> turnoverList) {
        this.turnoverList = turnoverList;
    }
}
