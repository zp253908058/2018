package com.teeny.wms.web.model;

import org.apache.ibatis.type.Alias;

@Alias("stringMap")
public class StringMapEntity implements KeyValue<String, String> {

    private static final long serialVersionUID = 7247714666080613256L;

    private String key;
    private String value;

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
