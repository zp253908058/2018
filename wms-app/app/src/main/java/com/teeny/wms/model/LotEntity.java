package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see LotEntity
 * @since 2017/8/23
 */

public class LotEntity implements Parcelable{

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

    protected LotEntity(Parcel in) {
        lotNo = in.readString();
        count = in.readFloat();
        validateDate = in.readString();
    }

    public static final Creator<LotEntity> CREATOR = new Creator<LotEntity>() {
        @Override
        public LotEntity createFromParcel(Parcel in) {
            return new LotEntity(in);
        }

        @Override
        public LotEntity[] newArray(int size) {
            return new LotEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lotNo);
        dest.writeFloat(count);
        dest.writeString(validateDate);
    }
}
