package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SKUEntity
 * @since 2017/7/31
 */

public class SKUEntity implements Parcelable {

    private int id;                                 //库存表id
    private int goodsId;                            //商品id
    private String goodsName;                       //商品名
    private String barcode;                         //商品条码
    private String number;                          //编号(serial_number)
    private String locationName;                    //货位名
    private int locationId;                         //货位id
    private String lotNo;                           //批号
    private String manufacturer;                    //厂家
    private float quantity;                         //数量
    private String validateDate;                    //有效期
    private String productDate;                     //生产日期
    private String specification;                   //规格
    private String unit;                            //单位
    private float costPrice;                        //成本单价
    private float costTotal;                        //成本金额
    private String productionPlace;                 //产地
    private String pinyin;                          //拼音

    public SKUEntity() {
    }

    protected SKUEntity(Parcel in) {
        id = in.readInt();
        goodsId = in.readInt();
        goodsName = in.readString();
        barcode = in.readString();
        number = in.readString();
        locationName = in.readString();
        locationId = in.readInt();
        lotNo = in.readString();
        manufacturer = in.readString();
        quantity = in.readFloat();
        validateDate = in.readString();
        productDate = in.readString();
        specification = in.readString();
        unit = in.readString();
        costPrice = in.readFloat();
        costTotal = in.readFloat();
        productionPlace = in.readString();
        pinyin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(goodsId);
        dest.writeString(goodsName);
        dest.writeString(barcode);
        dest.writeString(number);
        dest.writeString(locationName);
        dest.writeInt(locationId);
        dest.writeString(lotNo);
        dest.writeString(manufacturer);
        dest.writeFloat(quantity);
        dest.writeString(validateDate);
        dest.writeString(productDate);
        dest.writeString(specification);
        dest.writeString(unit);
        dest.writeFloat(costPrice);
        dest.writeFloat(costTotal);
        dest.writeString(productionPlace);
        dest.writeString(pinyin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SKUEntity> CREATOR = new Creator<SKUEntity>() {
        @Override
        public SKUEntity createFromParcel(Parcel in) {
            return new SKUEntity(in);
        }

        @Override
        public SKUEntity[] newArray(int size) {
            return new SKUEntity[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(String validateDate) {
        this.validateDate = validateDate;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
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

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public float getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(float costTotal) {
        this.costTotal = costTotal;
    }

    public String getProductionPlace() {
        return productionPlace;
    }

    public void setProductionPlace(String productionPlace) {
        this.productionPlace = productionPlace;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
}
