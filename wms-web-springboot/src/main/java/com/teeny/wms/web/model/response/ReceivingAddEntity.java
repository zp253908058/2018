package com.teeny.wms.web.model.response;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReceivingAddEntity
 * @since 2017/11/6
 */
public class ReceivingAddEntity {

    private String lotNo;
    private int serialNo;
    private String validityDate;
    private float price;
    private float zhAmount;
    private String zhUnit;
    private float lhAmount;
    private String lhUnit;
    private float rate;

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getZhAmount() {
        return zhAmount;
    }

    public void setZhAmount(float zhAmount) {
        this.zhAmount = zhAmount;
    }

    public String getZhUnit() {
        return zhUnit;
    }

    public void setZhUnit(String zhUnit) {
        this.zhUnit = zhUnit;
    }

    public float getLhAmount() {
        return lhAmount;
    }

    public void setLhAmount(float lhAmount) {
        this.lhAmount = lhAmount;
    }

    public String getLhUnit() {
        return lhUnit;
    }

    public void setLhUnit(String lhUnit) {
        this.lhUnit = lhUnit;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getAmount() {
        return zhAmount * rate + lhAmount;
    }
}
