package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllocationEntity
 * @since 2017/8/25
 */

public class AllocationEntity implements Parcelable{

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

    @Override
    public String toString() {
        return "AllocationEntity{" +
                "amount=" + amount +
                ", locationCode='" + locationCode + '\'' +
                '}';
    }

    protected AllocationEntity(Parcel in) {
        amount = in.readFloat();
        locationCode = in.readString();
    }

    public static final Creator<AllocationEntity> CREATOR = new Creator<AllocationEntity>() {
        @Override
        public AllocationEntity createFromParcel(Parcel in) {
            return new AllocationEntity(in);
        }

        @Override
        public AllocationEntity[] newArray(int size) {
            return new AllocationEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(amount);
        dest.writeString(locationCode);
    }
}
