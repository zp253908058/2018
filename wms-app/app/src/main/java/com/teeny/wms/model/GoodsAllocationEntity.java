package com.teeny.wms.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see GoodsAllocationEntity
 * @since 2017/9/22
 */

public class GoodsAllocationEntity {

    @SerializedName(value = "value", alternate = {"code"})
    private String code;
    @SerializedName(value = "key", alternate = {"name"})
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
