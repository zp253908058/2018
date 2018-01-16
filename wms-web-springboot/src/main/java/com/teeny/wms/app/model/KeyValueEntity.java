package com.teeny.wms.app.model;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see KeyValueEntity
 * @since 2017/10/25
 */
public class KeyValueEntity implements KeyValue<Integer, String> {

    private int key;
    private String value;

    @Override
    public void setKey(Integer key) {
        this.key = key;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
