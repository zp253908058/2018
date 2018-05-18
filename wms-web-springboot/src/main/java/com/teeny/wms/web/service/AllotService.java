package com.teeny.wms.web.service;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.request.AllotListCompleteRequestEntity;
import com.teeny.wms.web.model.request.AllotListRequestEntity;
import com.teeny.wms.web.model.request.AllotLocationRequestEntity;
import com.teeny.wms.web.model.response.AllocationEntity;
import com.teeny.wms.web.model.response.AllotEntity;
import com.teeny.wms.web.model.response.AllotGoodsEntity;
import com.teeny.wms.web.model.response.AllotLocationEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotService
 * @since 2017/11/7
 */
public interface AllotService {

    List<AllotEntity> getAllotList(String billNo, String goodsCode, int sId, int saId, String account);

    void updateAll(List<AllotListCompleteRequestEntity> params, String account, int userId);

    void updateOne(int id, int classType, String account, int userId);

    void update(AllotListRequestEntity entity, String account, int userId);

    List<AllocationEntity> getLocationsById(int id, String account);

    List<KeyValueEntity> getOrderList(int saId, int sId, String account);

    List<KeyValueEntity> getGoodsCode(String account, int sId, int saId);

    List<AllotGoodsEntity> getAllotGoodsList(String account, String location, String goods, int warehouseId, int repositoryId, int areaId);

    AllotGoodsEntity add(String account, int id, int userId, String serial);

    List<AllotGoodsEntity> getTempleGoodsList(String account, int userId);

    void completeAllot(String account, AllotLocationRequestEntity entity);

    List<AllotLocationEntity> getLocationList(String account, int id);

    void remove(String account, int detailId, int locationRowId);

    void finishBill(String account, int userId);
}
