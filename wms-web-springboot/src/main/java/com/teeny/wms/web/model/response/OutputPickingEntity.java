package com.teeny.wms.web.model.response;

import org.apache.ibatis.type.Alias;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see OutputPickingEntity
 * @since 2018/1/21
 */
@Alias("OutputPickingEntity")
public class OutputPickingEntity {

    private int number;
    private String turnover;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }
}
