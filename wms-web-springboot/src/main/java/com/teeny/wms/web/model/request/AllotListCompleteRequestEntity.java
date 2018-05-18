package com.teeny.wms.web.model.request;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotListCompleteRequestEntity
 * @since 2018/5/18
 */
public class AllotListCompleteRequestEntity {
    private int id;
    private int classType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassType() {
        return classType;
    }

    public void setClassType(int classType) {
        this.classType = classType;
    }
}
