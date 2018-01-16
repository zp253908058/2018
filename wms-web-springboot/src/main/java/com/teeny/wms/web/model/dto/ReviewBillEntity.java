package com.teeny.wms.web.model.dto;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReviewBillEntity
 * @since 2017/11/7
 */
public class ReviewBillEntity {
    /*复核单条码表id*/
    private int smbId;
    /*复核单id,=pda_CheckBill.billid*/
    private int billId;
    /*1整件 2拼箱 3打包*/
    private int pickType;
    /*条码*/
    private String barCode;
    /*数量*/
    private float eligibleQty;
    /*拣货单号*/
    private String pickNo;
    /*拣货人id,=pda_employees.e_id*/
    private int pickEmpId;
    /*复核人id,=pda_employees.e_id*/
    private int checkEmpId;
    /*处理状态，0 未处理, 1 PDA已扫到码 0531*/
    private int dealStates;
    /*交换状态 0提供 1pda已回写*/
    private int pdaStates;


    public int getSmbId() {
        return smbId;
    }

    public void setSmbId(int smbId) {
        this.smbId = smbId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getPickType() {
        return pickType;
    }

    public void setPickType(int pickType) {
        this.pickType = pickType;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public float getEligibleQty() {
        return eligibleQty;
    }

    public void setEligibleQty(float eligibleQty) {
        this.eligibleQty = eligibleQty;
    }

    public String getPickNo() {
        return pickNo;
    }

    public void setPickNo(String pickNo) {
        this.pickNo = pickNo;
    }

    public int getPickEmpId() {
        return pickEmpId;
    }

    public void setPickEmpId(int pickEmpId) {
        this.pickEmpId = pickEmpId;
    }

    public int getCheckEmpId() {
        return checkEmpId;
    }

    public void setCheckEmpId(int checkEmpId) {
        this.checkEmpId = checkEmpId;
    }

    public int getDealStates() {
        return dealStates;
    }

    public void setDealStates(int dealStates) {
        this.dealStates = dealStates;
    }

    public int getPdaStates() {
        return pdaStates;
    }

    public void setPdaStates(int pdaStates) {
        this.pdaStates = pdaStates;
    }
}
