package com.teeny.wms.model.request;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BarcodeAddRequestEntity
 * @since 2018/1/7
 */

public class BarcodeAddRequestEntity {

    private int id;
    private String oldBarcode;
    private String newBarcode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOldBarcode() {
        return oldBarcode;
    }

    public void setOldBarcode(String oldBarcode) {
        this.oldBarcode = oldBarcode;
    }

    public String getNewBarcode() {
        return newBarcode;
    }

    public void setNewBarcode(String newBarcode) {
        this.newBarcode = newBarcode;
    }
}
