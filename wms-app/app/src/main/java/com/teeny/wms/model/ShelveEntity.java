package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class description:上架单实体
 *
 * @author zp
 * @version 1.0
 * @see ShelveEntity
 * @since 2017/7/25
 */

public class ShelveEntity implements Parcelable{

    private int id;
    private int originalId;
    private int status;
    private int goodsId;
    private String goodsName;
    private String lotNo;
    private String specification;
    private String productionDate;
    private String unit;
    private String amount;
    private String manufacturer;
    private String locationCode;
    private String goodsCode;
    private String pinyin;

    public ShelveEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOriginalId() {
        return originalId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    protected ShelveEntity(Parcel in) {
        id = in.readInt();
        originalId = in.readInt();
        status = in.readInt();
        goodsId = in.readInt();
        goodsName = in.readString();
        lotNo = in.readString();
        specification = in.readString();
        productionDate = in.readString();
        unit = in.readString();
        amount = in.readString();
        manufacturer = in.readString();
        locationCode = in.readString();
        goodsCode = in.readString();
        pinyin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(originalId);
        dest.writeInt(status);
        dest.writeInt(goodsId);
        dest.writeString(goodsName);
        dest.writeString(lotNo);
        dest.writeString(specification);
        dest.writeString(productionDate);
        dest.writeString(unit);
        dest.writeString(amount);
        dest.writeString(manufacturer);
        dest.writeString(locationCode);
        dest.writeString(goodsCode);
        dest.writeString(pinyin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShelveEntity> CREATOR = new Creator<ShelveEntity>() {
        @Override
        public ShelveEntity createFromParcel(Parcel in) {
            return new ShelveEntity(in);
        }

        @Override
        public ShelveEntity[] newArray(int size) {
            return new ShelveEntity[size];
        }
    };
}
