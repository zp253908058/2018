package com.teeny.wms.model;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ExWarehouseReviewEntity
 * @since 2017/8/3
 */

public class ExWarehouseReviewEntity {

    private String billNo; //销售单号
    private String deliveryLine; //配送路线
    private String customer; //客户
    private String priority; //优先级
    private String tempArea; //暂存区
    private int status;//状态
    private int documentStatus; //单据状态
    private String billRemark; //单据备注

    private int zhQuantity; //整货数量
    private int pxQuantity; //拼箱件数
    private int dbQuantity; //打包件数

    private int zhQuantityTotal; //整货数量总数
    private int pxQuantityTotal; //拼箱件数总数
    private int dbQuantityTotal; //打包件数总数

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getDeliveryLine() {
        return deliveryLine;
    }

    public void setDeliveryLine(String deliveryLine) {
        this.deliveryLine = deliveryLine;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTempArea() {
        return tempArea;
    }

    public void setTempArea(String tempArea) {
        this.tempArea = tempArea;
    }

    public int getSStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(int documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getBillRemark() {
        return billRemark;
    }

    public void setBillRemark(String billRemark) {
        this.billRemark = billRemark;
    }

    public int getZhQuantity() {
        return zhQuantity;
    }

    public void setZhQuantity(int zhQuantity) {
        this.zhQuantity = zhQuantity;
    }

    public int getPxQuantity() {
        return pxQuantity;
    }

    public void setPxQuantity(int pxQuantity) {
        this.pxQuantity = pxQuantity;
    }

    public int getDbQuantity() {
        return dbQuantity;
    }

    public void setDbQuantity(int dbQuantity) {
        this.dbQuantity = dbQuantity;
    }

    public int getZhQuantityTotal() {
        return zhQuantityTotal;
    }

    public void setZhQuantityTotal(int zhQuantityTotal) {
        this.zhQuantityTotal = zhQuantityTotal;
    }

    public int getPxQuantityTotal() {
        return pxQuantityTotal;
    }

    public void setPxQuantityTotal(int pxQuantityTotal) {
        this.pxQuantityTotal = pxQuantityTotal;
    }

    public int getDbQuantityTotal() {
        return dbQuantityTotal;
    }

    public void setDbQuantityTotal(int dbQuantityTotal) {
        this.dbQuantityTotal = dbQuantityTotal;
    }

    public String getStatusString() {
        return status == 10 ? "未复核" : "已复核";
    }
}
