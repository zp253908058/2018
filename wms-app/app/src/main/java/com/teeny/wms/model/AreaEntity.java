package com.teeny.wms.model;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AreaEntity
 * @since 2017/12/28
 */

public class AreaEntity {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
