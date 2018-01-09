package com.teeny.wms.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see KeyValueEntity
 * @since 2017/8/1
 */

public class KeyValueEntity {

    @SerializedName(value = "key", alternate = {"id"})
    private int key;
    @SerializedName(value = "value", alternate = {"name"})
    private String value;

    private String alternate;

    public KeyValueEntity() {
    }

    public KeyValueEntity(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String getAlternate() {
        return alternate;
    }

    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }
}
