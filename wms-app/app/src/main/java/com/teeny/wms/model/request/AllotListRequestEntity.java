package com.teeny.wms.model.request;

import com.teeny.wms.model.AllocationEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotListRequestEntity
 * @since 2017/8/24
 */

public class AllotListRequestEntity {

    private int id;
    private List<AllocationEntity> locations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public List<AllocationEntity> getLocations() {
        return locations;
    }

    public void setLocations(List<AllocationEntity> locations) {
        this.locations = locations;
    }
}
