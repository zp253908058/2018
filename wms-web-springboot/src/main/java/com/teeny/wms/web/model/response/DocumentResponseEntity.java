package com.teeny.wms.web.model.response;

import com.teeny.wms.web.model.dto.DocumentEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see DocumentResponseEntity
 * @since 2017/11/7
 */
public class DocumentResponseEntity {

    private List<DocumentEntity> receivingList;
    private List<DocumentEntity> putawayList;
    private List<DocumentEntity> allotList;
    private List<DocumentEntity> reviewList;

    public List<DocumentEntity> getReceivingList() {
        return receivingList;
    }

    public void setReceivingList(List<DocumentEntity> receivingList) {
        this.receivingList = receivingList;
    }

    public List<DocumentEntity> getPutawayList() {
        return putawayList;
    }

    public void setPutawayList(List<DocumentEntity> putawayList) {
        this.putawayList = putawayList;
    }

    public List<DocumentEntity> getAllotList() {
        return allotList;
    }

    public void setAllotList(List<DocumentEntity> allotList) {
        this.allotList = allotList;
    }

    public List<DocumentEntity> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<DocumentEntity> reviewList) {
        this.reviewList = reviewList;
    }
}
