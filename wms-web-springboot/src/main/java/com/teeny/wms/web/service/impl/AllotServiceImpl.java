package com.teeny.wms.web.service.impl;

import com.teeny.wms.app.exception.InnerException;
import com.teeny.wms.app.model.KeyValueEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.web.model.request.AllotListRequestEntity;
import com.teeny.wms.web.model.response.AllocationEntity;
import com.teeny.wms.web.model.response.AllotEntity;
import com.teeny.wms.web.model.response.AllotGoodsEntity;
import com.teeny.wms.web.repository.AllotMapper;
import com.teeny.wms.web.repository.CommonMapper;
import com.teeny.wms.web.service.AllotService;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotServiceImpl
 * @since 2017/11/7
 */
@Service
public class AllotServiceImpl implements AllotService {

    private static final String DATE_PATTERN = "yyyyMMddHHmmss";

    private AllotMapper mAllotMapper;
    private CommonMapper mCommonMapper;

    @Autowired
    public void setAllotMapper(AllotMapper allotMapper) {
        mAllotMapper = allotMapper;
    }

    @Autowired
    public void setCommonMapper(CommonMapper commonMapper) {
        mCommonMapper = commonMapper;
    }

    @Override
    public List<AllotEntity> getAllotList(String billNo, String goodsCode, int sId, int saId, String account) {
        List<AllotEntity> list = mAllotMapper.getList(billNo, goodsCode, sId, saId, account);
        mAllotMapper.updateStatus(billNo, account);
        return list;
    }

    @Override
    public void updateAll(List<Integer> ids, String account, int userId) {
        if (Validator.isNotEmpty(ids)) {
            mAllotMapper.updateAll(ids.get(0), ids, account, userId);
        }
    }

    @Override
    public void updateOne(int id, String account, int userId) {
        mAllotMapper.updateOne(id, account, userId);
    }

    @Override
    public void update(AllotListRequestEntity entity, String account, int userId) {
        List<Integer> ids = mAllotMapper.getIdsByOriginalId(entity.getId(), account);
        List<AllocationEntity> locations = entity.getLocations();
        if (Validator.isNotEmpty(locations)) {
            for (AllocationEntity allocation : locations) {
                Integer locationId = mCommonMapper.findLocationIdByCode(account, allocation.getLocationCode());
                if (locationId == null || locationId == 0) {
                    throw new InnerException("找不此货位:" + allocation.getLocationCode());
                }
                mAllotMapper.copyData(entity.getId(), allocation.getAmount(), locationId, account, userId);
            }
        } else {
            mAllotMapper.copyData(entity.getId(), 0, 0, account, userId);
        }

        mAllotMapper.deleteByIds(ids, entity.getId(), account);

        mAllotMapper.updateBillStatus(account, entity.getId());
    }

    @Override
    public List<AllocationEntity> getLocationsById(int id, String account) {
        return mAllotMapper.getLocationById(id, account);
    }

    @Override
    public List<KeyValueEntity> getOrderList(int saId, int sId, String account) {
        return mAllotMapper.getOrderList(saId, sId, account);
    }

    @Override
    public List<KeyValueEntity> getGoodsCode(String account, int sId, int saId) {
        return mAllotMapper.getGoodsCode(account, sId, saId);
    }

    @Override
    public List<AllotGoodsEntity> getAllotGoodsList(String account, String location, String goods, int warehouseId, int repositoryId, int areaId) {
        return mAllotMapper.getAllotGoodsList(account, location, goods, warehouseId, repositoryId, areaId);
    }

    @Override
    public void add(String account, int id, int userId, String serial) {
        String billNo = serial + DateFormatUtils.format(new Date(), DATE_PATTERN);
        //生成大单据
        mAllotMapper.generateBill(account, userId, billNo);
        //添加至待完成
        mAllotMapper.generateAllotOrder(account, id, userId);
    }

    @Override
    public List<AllotGoodsEntity> getTempleGoodsList(String account, int userId) {
        return mAllotMapper.getTempleGoodsList(account, userId);
    }
}
