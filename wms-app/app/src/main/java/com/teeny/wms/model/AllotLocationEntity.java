package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotLocationEntity
 * @since 2018/1/18
 */

public class AllotLocationEntity implements Parcelable{

    private float amount;
    private String locationCode;

    public AllotLocationEntity() {
    }

    protected AllotLocationEntity(Parcel in) {
        amount = in.readFloat();
        locationCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(amount);
        dest.writeString(locationCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AllotLocationEntity> CREATOR = new Creator<AllotLocationEntity>() {
        @Override
        public AllotLocationEntity createFromParcel(Parcel in) {
            return new AllotLocationEntity(in);
        }

        @Override
        public AllotLocationEntity[] newArray(int size) {
            return new AllotLocationEntity[size];
        }
    };

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
