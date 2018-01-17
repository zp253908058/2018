package com.teeny.wms.web.service.impl;

import com.teeny.wms.app.exception.InnerException;
import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.web.model.request.AllotListRequestEntity;
import com.teeny.wms.web.model.response.AllocationEntity;
import com.teeny.wms.web.model.response.PutawayEntity;
import com.teeny.wms.web.repository.CommonMapper;
import com.teeny.wms.web.repository.PutawayMapper;
import com.teeny.wms.web.service.PutawayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see PutawayServiceImpl
 * @since 2017/11/8
 */

@Service
@Transactional
public class PutawayServiceImpl implements PutawayService {

    private PutawayMapper mPutawayMapper;
    private CommonMapper mCommonMapper;

    @Autowired
    public void setPutawayMapper(PutawayMapper putawayMapper) {
        mPutawayMapper = putawayMapper;
    }

    @Autowired
    public void setCommonMapper(CommonMapper commonMapper) {
        mCommonMapper = commonMapper;
    }

    @Override
    public List<KeyValueEntity> getSaList(int sId, String account) {
        return mPutawayMapper.getSaListBysId(sId, account);
    }

    @Override
    public List<KeyValueEntity> getBillList(int sId, int saId, String account) {
        return mPutawayMapper.getBillList(sId, saId, account);
    }

    @Override
    public List<PutawayEntity> getGoodsDetailList(String orderNo, String account, int sId) {
        List<PutawayEntity> list = mPutawayMapper.getGoodsDetailList(orderNo, account, sId);
        mPutawayMapper.updateStatus(account, orderNo);
        return list;
    }

    @Override
    public void all(List<Integer> ids, String account, int userId) {
        if (Validator.isEmpty(ids)) {
            return;
        }
        mPutawayMapper.all(ids.get(0), ids, account, userId);
    }

    @Override
    public void single(int originalId, String account, int userId) {
        mPutawayMapper.single(originalId, account, userId);
    }

    @Override
    public void update(AllotListRequestEntity entity, String account, int userId) {
        List<Integer> ids = mPutawayMapper.getIdsByOriginalId(entity.getId(), account);
        List<AllocationEntity> locations = entity.getLocations();
        if (Validator.isNotEmpty(locations)) {
            for (AllocationEntity location : locations) {
                Integer locationId = mCommonMapper.getLocationIdByCode(location.getLocationCode(), account);
                if (locationId == null || locationId == 0) {
                    throw new InnerException("找不此货位:" + location.getLocationCode());
                }
                mPutawayMapper.copyDataByParam(entity.getId(), location.getAmount(), locationId, account, userId);
            }
            mPutawayMapper.deleteByIds(ids, account);
        }
        mPutawayMapper.updateBillStatus(entity.getId(), account);
    }

    @Override
    public List<AllocationEntity> getLocationList(int id, String account) {
        return mPutawayMapper.getLocationListById(id, account);
    }
}
