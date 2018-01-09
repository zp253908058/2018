package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReceivingItemEntity
 * @since 2017/7/20
 */

public class ReceivingItemEntity implements Parcelable {
    private int id;//id 1
    private int originalId;       //原始id
    private String billNo;
    private int goodsId;
    private String goodsName;
    private String lotNo;
    private String specification;
    private String validityDate;
    private String retailPrice;
    private String quantity;            //收货数量
    private String amount;              //订单（配送）数量 1
    private String manufacturer;
    private String produceArea; //产地
    private String serialNo; //序号
    private String barcode;
    private int status;

    private float rate;
    private String zhUnit;
    private String lhUnit;

    private String pinyin;

    public ReceivingItemEntity() {
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

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
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

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
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

    public String getProduceArea() {
        return produceArea;
    }

    public void setProduceArea(String produceArea) {
        this.produceArea = produceArea;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getZhUnit() {
        return zhUnit;
    }

    public void setZhUnit(String zhUnit) {
        this.zhUnit = zhUnit;
    }

    public String getLhUnit() {
        return lhUnit;
    }

    public void setLhUnit(String lhUnit) {
        this.lhUnit = lhUnit;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    protected ReceivingItemEntity(Parcel in) {
        id = in.readInt();
        originalId = in.readInt();
        billNo = in.readString();
        goodsId = in.readInt();
        goodsName = in.readString();
        lotNo = in.readString();
        specification = in.readString();
        validityDate = in.readString();
        retailPrice = in.readString();
        quantity = in.readString();
        amount = in.readString();
        manufacturer = in.readString();
        produceArea = in.readString();
        serialNo = in.readString();
        barcode = in.readString();
        status = in.readInt();
        rate = in.readFloat();
        zhUnit = in.readString();
        lhUnit = in.readString();
        pinyin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(originalId);
        dest.writeString(billNo);
        dest.writeInt(goodsId);
        dest.writeString(goodsName);
        dest.writeString(lotNo);
        dest.writeString(specification);
        dest.writeString(validityDate);
        dest.writeString(retailPrice);
        dest.writeString(quantity);
        dest.writeString(amount);
        dest.writeString(manufacturer);
        dest.writeString(produceArea);
        dest.writeString(serialNo);
        dest.writeString(barcode);
        dest.writeInt(status);
        dest.writeFloat(rate);
        dest.writeString(zhUnit);
        dest.writeString(lhUnit);
        dest.writeString(pinyin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReceivingItemEntity> CREATOR = new Creator<ReceivingItemEntity>() {
        @Override
        public ReceivingItemEntity createFromParcel(Parcel in) {
            return new ReceivingItemEntity(in);
        }

        @Override
        public ReceivingItemEntity[] newArray(int size) {
            return new ReceivingItemEntity[size];
        }
    };
}
