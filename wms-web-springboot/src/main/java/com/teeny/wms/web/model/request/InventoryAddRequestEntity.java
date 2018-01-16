package com.teeny.wms.web.model.request;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see InventoryAddRequestEntity
 * @since 2017/9/7
 */

public class InventoryAddRequestEntity {
    private int inventoryId;                 //盘点单id
    private int goodsId;                     //商品id
    private String locationCode;             //货位码
    private String lotNo;                    //批号
    private int amount;                      //数量
    private String validateDate;             //有效期

    private int locationId;                  //货位id
    private int billState;                   //初盘1  复盘2

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
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

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getBillState() {
        return billState;
    }

    public void setBillState(int billState) {
        this.billState = billState;
    }
}
