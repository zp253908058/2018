package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.teeny.wms.util.Validator;

/**
 * Class description: 调拨单实体
 *
 * @author zp
 * @version 1.0
 * @see AllotListEntity
 * @since 2017/7/18
 */

public class AllotListEntity implements Parcelable {
    private int id; //id
    private int originalId;
    private String billNo;
    private int goodsId;
    private String goodsName; //商品名
    private String lotNo; //批号
    private String specification; //规格
    private String manufacturer; //厂家
    private String unit; //单位
    private int amount; //数量
    private String validateDate; //有效期
    private String productDate; //生产日期
    private String barcode;  //商品码
    private int status; //状态
    private String number;   //编码
    private String exportName;   //调出货位
    private String importName;   //调入货位
    private String pinyin;

    public AllotListEntity() {
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExportName() {
        return Validator.isEmpty(exportName) ? "" : exportName;
    }

    public void setExportName(String exportName) {
        this.exportName = exportName;
    }

    public String getImportName() {
        return Validator.isEmpty(importName) ? "" : importName;
    }

    public void setImportName(String importName) {
        this.importName = importName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    protected AllotListEntity(Parcel in) {
        id = in.readInt();
        originalId = in.readInt();
        billNo = in.readString();
        goodsId = in.readInt();
        goodsName = in.readString();
        lotNo = in.readString();
        specification = in.readString();
        manufacturer = in.readString();
        unit = in.readString();
        amount = in.readInt();
        validateDate = in.readString();
        productDate = in.readString();
        barcode = in.readString();
        status = in.readInt();
        number = in.readString();
        exportName = in.readString();
        importName = in.readString();
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
        dest.writeString(manufacturer);
        dest.writeString(unit);
        dest.writeInt(amount);
        dest.writeString(validateDate);
        dest.writeString(productDate);
        dest.writeString(barcode);
        dest.writeInt(status);
        dest.writeString(number);
        dest.writeString(exportName);
        dest.writeString(importName);
        dest.writeString(pinyin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AllotListEntity> CREATOR = new Creator<AllotListEntity>() {
        @Override
        public AllotListEntity createFromParcel(Parcel in) {
            return new AllotListEntity(in);
        }

        @Override
        public AllotListEntity[] newArray(int size) {
            return new AllotListEntity[size];
        }
    };
}
