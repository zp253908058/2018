package com.teeny.wms.web.service.impl;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.response.ShopDeliveryGoodsEntity;
import com.teeny.wms.web.repository.ShopDeliveryMapper;
import com.teeny.wms.web.service.ShopDeliveryService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopDeliveryServiceImpl
 * @since 2018/1/18
 */
@Service
public class ShopDeliveryServiceImpl implements ShopDeliveryService {

    private ShopDeliveryMapper mMapper;

    @Autowired
    public void setMapper(ShopDeliveryMapper mapper) {
        mMapper = mapper;
    }

    @Override
    public List<KeyValueEntity> getOrderList(String account) {
        return mMapper.getOrderList(account);
    }

    @Override
    public List<ShopDeliveryGoodsEntity> getDeliveryGoodsList(String account, int id) {
        List<ShopDeliveryGoodsEntity> result = mMapper.getDeliveryGoodsList(account, id);
        mMapper.updateState(account, id);
        return result;
    }

    @Override
    public void complete(String account, List<Integer> ids, int billId, int userId) {
        mMapper.complete(account, ids, billId, userId);
        mMapper.updateStatus(account, billId);
    }

    @Override
    public void single(String account, int id, int billId, int userId) {
        single(account, id, 0, "", billId, userId);
    }

    @Override
    public void single(String account, int id, float amount, String remark, int billId, int userId) {
        mMapper.single(account, id, billId, amount, remark, userId);
        mMapper.updateStatus(account, billId);
    }
}
