package com.teeny.wms.model;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see RecipientEntity
 * @since 2017/9/21
 */

public class RecipientEntity {

    private int id;
    private String serialNumber;
    private String pinyin;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
