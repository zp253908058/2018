package com.teeny.wms.web.model.request;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see LotEntity
 * @since 2017/8/23
 */

public class LotEntity {

    private String lotNo;
    private float count; //数量
    private String validateDate; //有效期

    public LotEntity() {
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public float getCount() {
        return count;
    }

    public void setCount(float count) {
        this.count = count;
    }

    public String getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(String validateDate) {
        this.validateDate = validateDate;
    }
}
