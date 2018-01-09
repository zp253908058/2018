package com.teeny.wms.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see DocumentEntity
 * @since 2017/7/18
 */

public class DocumentEntity implements Parcelable {

    private int id;
    private int type;                          //单据类型  1:验收单,2:上架单,3:调拨单,4:复核单
    private String typeDescription;            //单据类型描述
    private String status;                     //单据状态
    @SerializedName("documentNo")
    private String number;                     //单据编号
    @SerializedName("documentDate")
    private String date;                       //单据状态修改日期

    public DocumentEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    protected DocumentEntity(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        typeDescription = in.readString();
        status = in.readString();
        number = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeString(typeDescription);
        dest.writeString(status);
        dest.writeString(number);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DocumentEntity> CREATOR = new Creator<DocumentEntity>() {
        @Override
        public DocumentEntity createFromParcel(Parcel in) {
            return new DocumentEntity(in);
        }

        @Override
        public DocumentEntity[] newArray(int size) {
            return new DocumentEntity[size];
        }
    };
}
