package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see OutputPickingEntity
 * @since 2018/1/20
 */

public class OutputPickingEntity implements Parcelable {

    private int number;
    private String turnover;

    public OutputPickingEntity() {
    }

    public OutputPickingEntity(int number, String turnover) {
        this.number = number;
        this.turnover = turnover;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    protected OutputPickingEntity(Parcel in) {
        number = in.readInt();
        turnover = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(turnover);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OutputPickingEntity> CREATOR = new Creator<OutputPickingEntity>() {
        @Override
        public OutputPickingEntity createFromParcel(Parcel in) {
            return new OutputPickingEntity(in);
        }

        @Override
        public OutputPickingEntity[] newArray(int size) {
            return new OutputPickingEntity[size];
        }
    };

    @Override
    public String toString() {
        return "OutputPickingEntity{" +
                "number=" + number +
                ", turnover='" + turnover + '\'' +
                '}';
    }
}
