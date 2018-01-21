package com.teeny.wms.model;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see OutPickingTaskEntity
 * @since 2018/1/21
 */

public class OutPickingTaskEntity {

    private int id;
    private String documentNo;
    private String username;
    private String detailRow;
    private String totalMoney;
    private int status;           //10未处理、处理中 13已完成

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDetailRow() {
        return detailRow;
    }

    public void setDetailRow(String detailRow) {
        this.detailRow = detailRow;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isUntreated() {
        return status == 13;
    }
}
