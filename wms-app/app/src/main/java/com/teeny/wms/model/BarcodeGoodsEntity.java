package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BarcodeGoodsEntity
 * @since 2018/1/4
 */

public class BarcodeGoodsEntity implements Parcelable, GoodsChoiceEntity {

    private int id;                       //id
    private String goodsName;             //商品名
    private String barcode;               //条码
    private String specification;         //规格
    private String unit;                  //单位
    private String manufacturer;          //厂家
    private String dosageForm;            //剂型

    private String makeArea;              //产地
    private String approvalNumber;        //批准文号

    private String newBarcode;            //新条码

    public String getMakeArea() {
        return makeArea;
    }

    public void setMakeArea(String makeArea) {
        this.makeArea = makeArea;
    }

    public String getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSpecification() {
        return specification;
    }

    @Override
    public String getManufacturers() {
        return this.manufacturer;
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

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getNewBarcode() {
        return newBarcode;
    }

    public void setNewBarcode(String newBarcode) {
        this.newBarcode = newBarcode;
    }

    protected BarcodeGoodsEntity(Parcel in) {
        id = in.readInt();
        goodsName = in.readString();
        barcode = in.readString();
        specification = in.readString();
        unit = in.readString();
        manufacturer = in.readString();
        dosageForm = in.readString();
        makeArea = in.readString();
        approvalNumber = in.readString();
        newBarcode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(goodsName);
        dest.writeString(barcode);
        dest.writeString(specification);
        dest.writeString(unit);
        dest.writeString(manufacturer);
        dest.writeString(dosageForm);
        dest.writeString(makeArea);
        dest.writeString(approvalNumber);
        dest.writeString(newBarcode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BarcodeGoodsEntity> CREATOR = new Creator<BarcodeGoodsEntity>() {
        @Override
        public BarcodeGoodsEntity createFromParcel(Parcel in) {
            return new BarcodeGoodsEntity(in);
        }

        @Override
        public BarcodeGoodsEntity[] newArray(int size) {
            return new BarcodeGoodsEntity[size];
        }
    };
}
