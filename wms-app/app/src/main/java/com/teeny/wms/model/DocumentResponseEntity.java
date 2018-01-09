package com.teeny.wms.model;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see DocumentResponseEntity
 * @since 2017/9/1
 */

public class DocumentResponseEntity {

    private List<DocumentEntity> acceptanceList;
    private List<DocumentEntity> putawayList;
    private List<DocumentEntity> allotList;
    private List<DocumentEntity> reviewList;

    public List<DocumentEntity> getAcceptanceList() {
        return acceptanceList;
    }

    public void setAcceptanceList(List<DocumentEntity> acceptanceList) {
        this.acceptanceList = acceptanceList;
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
