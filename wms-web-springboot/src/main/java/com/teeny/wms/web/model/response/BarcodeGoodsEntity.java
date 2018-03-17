package com.teeny.wms.web.model.response;

import org.apache.ibatis.type.Alias;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BarcodeGoodsEntity
 * @since 2018/1/4
 */
@Alias("BarcodeGoodsEntity")
public class BarcodeGoodsEntity {

    private int id;                       //商品id
    private String goodsName;             //商品名
    private String barcode;               //条码
    private String specification;         //规格
    private String unit;                  //单位
    private String manufacturer;          //厂家
    private String dosageForm;            //剂型

    private String makeArea;              //产地
    private String approvalNumber;        //批准文号

    private String newBarcode;            //新条码

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

    public String getNewBarcode() {
        return newBarcode;
    }

    public void setNewBarcode(String newBarcode) {
        this.newBarcode = newBarcode;
    }
}
