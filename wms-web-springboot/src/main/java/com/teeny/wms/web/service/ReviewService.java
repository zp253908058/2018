package com.teeny.wms.web.service;

import com.teeny.wms.app.model.KeyValueEntity;
import com.teeny.wms.web.model.request.ExWarehouseReviewRequestEntity;
import com.teeny.wms.web.model.response.ExWarehouseReviewEntity;
import com.teeny.wms.web.model.response.RecipientEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReviewService
 * @since 2017/11/7
 */
public interface ReviewService {
    ExWarehouseReviewEntity getWarehouseReview(String account, String billNo, int i);

    List<RecipientEntity> getRecipients(String account, int sId);

    List<KeyValueEntity> getBillList(int sId, String account);

    int getReplenishmentCount(String account, int sId);

    void complete(ExWarehouseReviewRequestEntity entity, String account);
}
