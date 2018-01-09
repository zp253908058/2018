package com.teeny.wms.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class description: 首页实体
 *
 * @author zp
 * @version 1.0
 * @see HomeEntity
 * @since 2017/8/1
 */

public class HomeEntity {

    @SerializedName("acceptBillCount")
    private int acceptCount;                      //验收单数量
    @SerializedName("putawayBillCount")
    private int putawayCount;                     //上架单数量
    @SerializedName("reviewBillCount")
    private int reviewCount;                      //复核单数量
    @SerializedName("tranferBillCount")
    private int allotCount;                       //调拨单数量


    public int getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(int acceptCount) {
        this.acceptCount = acceptCount;
    }

    public int getPutawayCount() {
        return putawayCount;
    }

    public void setPutawayCount(int putawayCount) {
        this.putawayCount = putawayCount;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public int getAllotCount() {
        return allotCount;
    }

    public void setAllotCount(int allotCount) {
        this.allotCount = allotCount;
    }
}
