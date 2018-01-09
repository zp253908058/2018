package com.teeny.wms.model;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReceivingEntity
 * @since 2017/8/10
 */

public class ReceivingEntity {

    private int orderId;
    private String status;
    private String billNo;
    private String buyer;
    private String buyerId;
    private List<ReceivingItemEntity> goodsList;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public List<ReceivingItemEntity> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<ReceivingItemEntity> goodsList) {
        this.goodsList = goodsList;
    }
}
