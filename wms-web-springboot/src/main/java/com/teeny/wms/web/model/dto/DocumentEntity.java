package com.teeny.wms.web.model.dto;

import org.apache.ibatis.type.Alias;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see DocumentEntity
 * @since 2017/11/7
 */

@Alias("DocumentEntity")
public class DocumentEntity {
    private int id;
    private String status;
    private int type;
    private String typeDescription;
    private String documentNo;
    private String documentDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDocumentNo() {
        return documentNo;
    }

    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    public String getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }
}
