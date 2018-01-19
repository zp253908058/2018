package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopDeliveryGoodsEntity
 * @since 2018/1/18
 */

public class ShopDeliveryGoodsEntity implements Parcelable{

    private int id;
    private int billId;
    private String goodsName;
    private String goodsCode;
    private String pinyin;
    private String specification;
    private String unit;
    private float receivingNumber;
    private float deliveryNumber;
    private String lotNumber;
    private String validateDate;
    private String manufacturer;
    private int state;

    public ShopDeliveryGoodsEntity() {
    }

    protected ShopDeliveryGoodsEntity(Parcel in) {
        id = in.readInt();
        billId = in.readInt();
        goodsName = in.readString();
        goodsCode = in.readString();
        pinyin = in.readString();
        specification = in.readString();
        unit = in.readString();
        receivingNumber = in.readFloat();
        deliveryNumber = in.readFloat();
        lotNumber = in.readString();
        validateDate = in.readString();
        manufacturer = in.readString();
        state = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(billId);
        dest.writeString(goodsName);
        dest.writeString(goodsCode);
        dest.writeString(pinyin);
        dest.writeString(specification);
        dest.writeString(unit);
        dest.writeFloat(receivingNumber);
        dest.writeFloat(deliveryNumber);
        dest.writeString(lotNumber);
        dest.writeString(validateDate);
        dest.writeString(manufacturer);
        dest.writeInt(state);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopDeliveryGoodsEntity> CREATOR = new Creator<ShopDeliveryGoodsEntity>() {
        @Override
        public ShopDeliveryGoodsEntity createFromParcel(Parcel in) {
            return new ShopDeliveryGoodsEntity(in);
        }

        @Override
        public ShopDeliveryGoodsEntity[] newArray(int size) {
            return new ShopDeliveryGoodsEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getReceivingNumber() {
        return receivingNumber;
    }

    public void setReceivingNumber(float receivingNumber) {
        this.receivingNumber = receivingNumber;
    }

    public float getDeliveryNumber() {
        return deliveryNumber;
    }

    public void setDeliveryNumber(float deliveryNumber) {
        this.deliveryNumber = deliveryNumber;
    }

    public String getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(String validateDate) {
        this.validateDate = validateDate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
