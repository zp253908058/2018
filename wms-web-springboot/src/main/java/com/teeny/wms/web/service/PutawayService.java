package com.teeny.wms.web.service;

import com.teeny.wms.app.model.KeyValueEntity;
import com.teeny.wms.web.model.request.AllotListRequestEntity;
import com.teeny.wms.web.model.response.AllocationEntity;
import com.teeny.wms.web.model.response.PutawayEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see PutawayService
 * @since 2017/11/8
 */
public interface PutawayService {
    List<KeyValueEntity> getSaList(int sId, String account);

    List<KeyValueEntity> getBillList(int sId, int saId, String account);

    List<PutawayEntity> getGoodsDetailList(String orderNo, String account);

    void all(List<Integer> ids, String account, int userId);

    void single(int originalId, String account, int userId);

    void update(AllotListRequestEntity entity, String account, int userId);

    List<AllocationEntity> getLocationList(int id, String account);
}
