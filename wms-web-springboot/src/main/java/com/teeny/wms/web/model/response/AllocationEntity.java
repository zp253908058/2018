package com.teeny.wms.web.model.response;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllocationEntity
 * @since 2017/11/7
 */
public class AllocationEntity {
    private float amount;
    private String locationCode;

    public AllocationEntity() {
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
}
