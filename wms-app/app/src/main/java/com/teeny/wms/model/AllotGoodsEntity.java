package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotGoodsEntity
 * @since 2018/1/9
 */

public class AllotGoodsEntity implements Parcelable {
    private int id;                 //VW_PDA_Storehouse 唯一id
    private int detailId;           //调拨单明细id
    private int billId;             //调拨单据id
    private int goodsId;            //商品id
    private String goodsName;
    private String lotNo;
    private String specification;
    private String validityDate;
    private String productionDate;
    private String unit;
    private float amount;
    private String manufacturer;
    private String locationCode;
    private String goodsCode;

    private String exportLocation;
    private String importLocation;

    private float currentInventory;   //当前库存

    private boolean selected;

    public AllotGoodsEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getExportLocation() {
        return exportLocation;
    }

    public void setExportLocation(String exportLocation) {
        this.exportLocation = exportLocation;
    }

    public String getImportLocation() {
        return importLocation;
    }

    public void setImportLocation(String importLocation) {
        this.importLocation = importLocation;
    }

    public float getCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(float currentInventory) {
        this.currentInventory = currentInventory;
    }



    protected AllotGoodsEntity(Parcel in) {
        id = in.readInt();
        detailId = in.readInt();
        billId = in.readInt();
        goodsId = in.readInt();
        goodsName = in.readString();
        lotNo = in.readString();
        specification = in.readString();
        validityDate = in.readString();
        productionDate = in.readString();
        unit = in.readString();
        amount = in.readFloat();
        manufacturer = in.readString();
        locationCode = in.readString();
        goodsCode = in.readString();
        exportLocation = in.readString();
        importLocation = in.readString();
        currentInventory = in.readFloat();
        selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(detailId);
        dest.writeInt(billId);
        dest.writeInt(goodsId);
        dest.writeString(goodsName);
        dest.writeString(lotNo);
        dest.writeString(specification);
        dest.writeString(validityDate);
        dest.writeString(productionDate);
        dest.writeString(unit);
        dest.writeFloat(amount);
        dest.writeString(manufacturer);
        dest.writeString(locationCode);
        dest.writeString(goodsCode);
        dest.writeString(exportLocation);
        dest.writeString(importLocation);
        dest.writeFloat(currentInventory);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AllotGoodsEntity> CREATOR = new Creator<AllotGoodsEntity>() {
        @Override
        public AllotGoodsEntity createFromParcel(Parcel in) {
            return new AllotGoodsEntity(in);
        }

        @Override
        public AllotGoodsEntity[] newArray(int size) {
            return new AllotGoodsEntity[size];
        }
    };
}
