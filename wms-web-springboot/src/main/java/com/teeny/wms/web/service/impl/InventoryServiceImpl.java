package com.teeny.wms.web.service.impl;

import com.teeny.wms.app.exception.InnerException;
import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.web.model.request.InventoryAddRequestEntity;
import com.teeny.wms.web.model.request.InventoryRequestEntity;
import com.teeny.wms.web.model.request.LotEntity;
import com.teeny.wms.web.model.request.SKUAddRequestEntity;
import com.teeny.wms.web.model.response.*;
import com.teeny.wms.web.repository.CommonMapper;
import com.teeny.wms.web.repository.InventoryMapper;
import com.teeny.wms.web.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see InventoryServiceImpl
 * @since 2017/11/8
 */

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private InventoryMapper mInventoryMapper;
    private CommonMapper mCommonMapper;

    @Autowired
    public void setInventoryMapper(InventoryMapper inventoryMapper) {
        mInventoryMapper = inventoryMapper;
    }

    @Autowired
    public void setCommonMapper(CommonMapper commonMapper) {
        mCommonMapper = commonMapper;
    }

    @Override
    public List<InventoryGoodsEntity> getInventoryList(int id, boolean isMerge, String account) {
        List<InventoryGoodsEntity> list = mInventoryMapper.getInventoryList(id, isMerge, account);
        mInventoryMapper.updateInventoryStatus(account, id, 1);
        return list;
    }

    @Override
    public int single(int originalId, String account, int userId) {
        mInventoryMapper.single(originalId, account, userId);
        Integer id = mInventoryMapper.getBillId(account, originalId);
        mInventoryMapper.updateState(account, id);
        return originalId;
    }

    @Override
    public void complete(List<Integer> ids, String account, int userId) {
        if (Validator.isNotEmpty(ids)) {
            mInventoryMapper.complete(account, ids, userId);
            Integer id = mInventoryMapper.getBillId(account, ids.get(0));
            mInventoryMapper.updateState(account, id);
            return;
        }
        throw new InnerException("没有可完成对象.");
    }

    @Override
    public void edit(InventoryRequestEntity entity, String account, int userId) {
        List<LotEntity> param = entity.getParam();
        if (Validator.isNotEmpty(param)) {
            List<Integer> ids = mInventoryMapper.getIdsByOriginalId(entity.getId(), account);
            mInventoryMapper.edit(entity.getId(), param, account, userId);
            mInventoryMapper.deleteByIds(ids, account);
        } else {
            throw new InnerException("盘点批次不能为空.");
        }
        mInventoryMapper.updateBillStatus(entity.getId(), account);
    }

    @Override
    public List<LotEntity> getLotList(int originalId, String account) {
        return mInventoryMapper.getLotList(originalId, account);
    }

    @Override
    public List<KeyValueEntity> getPdType(String account, int type, int sId) {
        return mInventoryMapper.getPdType(account, type, sId);
    }

    @Override
    public void addInventory(int type, InventoryAddRequestEntity entity, String account, int sId, int userId) {
        Integer locationId = mCommonMapper.getLocationIdByCode(entity.getLocationCode(), account);
        if (locationId != null && locationId >= 0) {
            entity.setLocationId(locationId);
            if (type != 2) {
                entity.setBillState(1);
            } else {
                entity.setBillState(2);
            }
            entity.setBillState(type);
            mInventoryMapper.addInventory(entity, type, account, sId, userId);
        } else {
            throw new InnerException("找不此货位:" + entity.getLocationCode());
        }
    }

    @Override
    public List<SKUEntity> getSKUList(int sId, String account, String locationCode, String barcode) {
        return mInventoryMapper.getSKUList(sId, account, locationCode, barcode);
    }

    @Override
    public List<GoodsDetailEntity> getGoodsDetailByCode(String goodsCode, String account) {
        return mInventoryMapper.getGoodsDetailByCode(goodsCode, account);
    }

    @Override
    public void addSku(SKUAddRequestEntity entity, String account, int sId, int userId) {
        int locationId = entity.getLocationId();
        if (locationId <= 0) {
            Integer id = mCommonMapper.getLocationIdByCode(entity.getLocationCode(), account);
            if (id == null || locationId == 0) {
                throw new InnerException("找不此货位:" + entity.getLocationCode());
            }
            locationId = id;
        }
        entity.setLocationId(locationId);
        mInventoryMapper.addProduct(entity, account, sId, userId);
    }

    @Override
    public InventoryInitializeEntity initialize(String account, int id, boolean isMerge) {
        InventoryInitializeEntity entity = new InventoryInitializeEntity();
        entity.setRepositoryList(mInventoryMapper.getCollection(account, id));
        InventoryCountEntity count = mInventoryMapper.count(account, id, 0, 0, "", isMerge);
        if (count != null) {
            entity.setFinishedNumber(count.getFinishedNumber());
            entity.setUnfinishedNumber(count.getUnfinishedNumber());
        }
        return entity;
    }

    @Override
    public InventoryCountEntity count(String account, int pdId, int repositoryId, int areaId, boolean isMerge) {
        return mInventoryMapper.count(account, pdId, repositoryId, areaId, "", isMerge);
    }

    @Override
    public InventoryGoodsWrapperEntity getHomeData(String account, int pdId, int repositoryId, int areaId, String locationCode, boolean isMerge) {
        InventoryGoodsWrapperEntity entity = new InventoryGoodsWrapperEntity();
        List<InventoryGoodsEntity> list = mInventoryMapper.getList(account, pdId, repositoryId, areaId, locationCode, isMerge);
        mInventoryMapper.updateInventoryStatus(account, pdId, 1);
        entity.setList(list);
        InventoryCountEntity count = mInventoryMapper.count(account, pdId, repositoryId, areaId, locationCode, isMerge);
        if (count != null) {
            entity.setFinishedNumber(count.getFinishedNumber());
            entity.setUnfinishedNumber(count.getUnfinishedNumber());
        }
        return entity;
    }

    @Override
    public void complete(String account, List<Integer> ids, int userId) {
        if (Validator.isNotEmpty(ids)) {
            mInventoryMapper.complete(account, ids, userId);
        }
    }
}
