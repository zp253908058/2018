package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SecondInventoryGoodsEntity
 * @since 2017/8/24
 */

public class SecondInventoryGoodsEntity implements Parcelable {
    private int id;                   //id
    private int originalId;           //原始单号id
    private int billId;               //盘点单id
    private int goodsId;              //商品id
    private String goodsName;         //商品名
    private String location;          // 货位
    private float countInBill;        //账面数量
    private float inventoryCount;     //盘点数量
    private String unit;              //单位盒
    private String specification;     //规格
    private String manufacturer;      //厂家
    private int status;               //状态
    private String locationCode;      //货位码
    private String goodsCode;         //商品码
    private String lotNo;             // 批号
    private String validateDate;      //有效期
    private String productionDate;    //生产日期
    private String repositoryName;    //库区名字
    private String areaName;          //区域名字

    private String pinyin;

    public SecondInventoryGoodsEntity() {
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

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getCountInBill() {
        return countInBill;
    }

    public void setCountInBill(float countInBill) {
        this.countInBill = countInBill;
    }

    public float getInventoryCount() {
        return inventoryCount;
    }

    public void setInventoryCount(float inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(String validateDate) {
        this.validateDate = validateDate;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    protected SecondInventoryGoodsEntity(Parcel in) {
        id = in.readInt();
        originalId = in.readInt();
        billId = in.readInt();
        goodsId = in.readInt();
        goodsName = in.readString();
        location = in.readString();
        countInBill = in.readFloat();
        inventoryCount = in.readFloat();
        unit = in.readString();
        specification = in.readString();
        manufacturer = in.readString();
        status = in.readInt();
        locationCode = in.readString();
        goodsCode = in.readString();
        lotNo = in.readString();
        validateDate = in.readString();
        productionDate = in.readString();
        repositoryName = in.readString();
        areaName = in.readString();
        pinyin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(originalId);
        dest.writeInt(billId);
        dest.writeInt(goodsId);
        dest.writeString(goodsName);
        dest.writeString(location);
        dest.writeFloat(countInBill);
        dest.writeFloat(inventoryCount);
        dest.writeString(unit);
        dest.writeString(specification);
        dest.writeString(manufacturer);
        dest.writeInt(status);
        dest.writeString(locationCode);
        dest.writeString(goodsCode);
        dest.writeString(lotNo);
        dest.writeString(validateDate);
        dest.writeString(productionDate);
        dest.writeString(repositoryName);
        dest.writeString(areaName);
        dest.writeString(pinyin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SecondInventoryGoodsEntity> CREATOR = new Creator<SecondInventoryGoodsEntity>() {
        @Override
        public SecondInventoryGoodsEntity createFromParcel(Parcel in) {
            return new SecondInventoryGoodsEntity(in);
        }

        @Override
        public SecondInventoryGoodsEntity[] newArray(int size) {
            return new SecondInventoryGoodsEntity[size];
        }
    };
}
