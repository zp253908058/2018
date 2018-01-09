package com.teeny.wms.model.request;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ExWarehouseReviewRequestEntity
 * @since 2017/8/25
 */

public class ExWarehouseReviewRequestEntity {

    private String billNo; //订单号
    private int recipientId; //接收人ID
    private String remark; //差异备注

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
