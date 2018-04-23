package com.teeny.wms.model;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see HistoryGoodsEntity
 * @since 2018/4/22
 */
public class HistoryGoodsEntity {

    private int id;                //商品id
    private String goodsName;      //商品名
    private String specification;  //规格
    private String goodsEncode;    //商品编码
    private String manufacturer;   //生产厂家

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

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getGoodsEncode() {
        return goodsEncode;
    }

    public void setGoodsEncode(String goodsEncode) {
        this.goodsEncode = goodsEncode;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
