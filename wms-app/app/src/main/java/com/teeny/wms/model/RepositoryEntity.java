package com.teeny.wms.model;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see RepositoryEntity
 * @since 2017/12/28
 */

public class RepositoryEntity {
    private int id;
    private String name;

    private List<AreaEntity> areas;

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

    public List<AreaEntity> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaEntity> areas) {
        this.areas = areas;
    }

    @Override
    public String toString() {
        return name;
    }
}
