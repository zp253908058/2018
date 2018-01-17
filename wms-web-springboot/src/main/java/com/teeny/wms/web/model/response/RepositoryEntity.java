package com.teeny.wms.web.model.response;

import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see RepositoryEntity
 * @since 2018/1/16
 */
@Alias("RepositoryEntity")
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
}
