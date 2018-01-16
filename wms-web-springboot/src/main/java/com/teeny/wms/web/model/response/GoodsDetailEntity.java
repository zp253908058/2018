package com.teeny.wms.web.model.response;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see GoodsDetailEntity
 * @since 2017/11/8
 */
public class GoodsDetailEntity {
    private int pId;
    private String goodsName;
    private String standard;//规格
    private String number; //编号
    private String unit; //单位
    private String manufacturers;

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(String manufacturers) {
        this.manufacturers = manufacturers;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }
}
