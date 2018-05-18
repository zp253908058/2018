package com.teeny.wms.web.service;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.response.ShopDeliveryGoodsEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopDeliveryService
 * @since 2018/1/18
 */
public interface ShopDeliveryService {

    List<KeyValueEntity> getOrderList(String account, int userId);

    List<ShopDeliveryGoodsEntity> getDeliveryGoodsList(String account, int id);

    void complete(String account, List<Integer> ids, int billId, int userId);

    void single(String account, int id, int billId, int userId);

    void single(String account, int id, float amount, String remark, int billId, int userId);
}
