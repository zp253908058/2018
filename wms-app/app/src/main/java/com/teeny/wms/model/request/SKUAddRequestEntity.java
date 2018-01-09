package com.teeny.wms.model.request;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SKUAddRequestEntity
 * @since 2017/8/24
 */

public class SKUAddRequestEntity {
   private int id;
   private int pId;
   private String lotNo;
   private String locationCode;
   private int locationId;
   private float amount;
   private float originalAmount;
   private String validateDate; //有效期

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(float originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(String validateDate) {
        this.validateDate = validateDate;
    }
}
