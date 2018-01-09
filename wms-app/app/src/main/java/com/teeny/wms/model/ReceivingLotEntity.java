package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReceivingLotEntity
 * @since 2017/8/15
 */

public class ReceivingLotEntity implements Parcelable {

    private String lotNo;
    private int serialNo;
    private String validityDate;
    private float price;
    private float zhAmount;
    private String zhUnit;
    private float lhAmount;
    private String lhUnit;
    private float rate;

    public ReceivingLotEntity() {
    }

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

    protected ReceivingLotEntity(Parcel in) {
        lotNo = in.readString();
        serialNo = in.readInt();
        validityDate = in.readString();
        price = in.readFloat();
        zhAmount = in.readFloat();
        zhUnit = in.readString();
        lhAmount = in.readFloat();
        lhUnit = in.readString();
        rate = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lotNo);
        dest.writeInt(serialNo);
        dest.writeString(validityDate);
        dest.writeFloat(price);
        dest.writeFloat(zhAmount);
        dest.writeString(zhUnit);
        dest.writeFloat(lhAmount);
        dest.writeString(lhUnit);
        dest.writeFloat(rate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReceivingLotEntity> CREATOR = new Creator<ReceivingLotEntity>() {
        @Override
        public ReceivingLotEntity createFromParcel(Parcel in) {
            return new ReceivingLotEntity(in);
        }

        @Override
        public ReceivingLotEntity[] newArray(int size) {
            return new ReceivingLotEntity[size];
        }
    };
}
