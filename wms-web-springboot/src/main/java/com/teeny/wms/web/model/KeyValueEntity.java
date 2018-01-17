package com.teeny.wms.web.model;

import org.apache.ibatis.type.Alias;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see KeyValueEntity
 * @since 2017/10/25
 */
@Alias("keyValue")
public class KeyValueEntity implements KeyValue<Integer, String> {
    private static final long serialVersionUID = 7247714666080613255L;

    private int key;
    private String value;
    private String alternate;

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

    public String getAlternate() {
        return alternate;
    }

    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }
}
