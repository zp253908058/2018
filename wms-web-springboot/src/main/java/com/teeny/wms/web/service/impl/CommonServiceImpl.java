package com.teeny.wms.web.service.impl;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.StringMapEntity;
import com.teeny.wms.web.model.response.DocumentResponseEntity;
import com.teeny.wms.web.model.response.HistoryGoodsEntity;
import com.teeny.wms.web.repository.CommonMapper;
import com.teeny.wms.web.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see CommonServiceImpl
 * @since 2017/11/7
 */
@Service
@Transactional
public class CommonServiceImpl implements CommonService {

    private CommonMapper mCommonMapper;

    @Autowired
    public void setCommonMapper(CommonMapper commonMapper) {
        mCommonMapper = commonMapper;
    }

    @Override
    public List<KeyValueEntity> getWarehouseList(String account) {
        return mCommonMapper.getWarehouseList(account);
    }

    @Override
    public List<KeyValueEntity> getAreaList(int sId, String account) {
        return mCommonMapper.getAreaList(sId, account);
    }

    @Override
    public List<StringMapEntity> getHistoryLocation(String account, int pId) {
        return mCommonMapper.getHistoryLocation(account, pId);
    }

    @Override
    public DocumentResponseEntity getDocumentList(String account, int sId) {
        DocumentResponseEntity result = new DocumentResponseEntity();
        result.setReceivingList(mCommonMapper.getReceivingList(account, sId));
        result.setAllotList(mCommonMapper.getAllotList(account, sId));
        result.setPutawayList(mCommonMapper.getPutawayList(account, sId));
        result.setReviewList(mCommonMapper.getReviewList(account, sId));
        return result;
    }

    @Override
    public List<KeyValueEntity> obtainWarehouseList(String account) {
        return mCommonMapper.obtainWarehouseList(account);
    }

    @Override
    public List<KeyValueEntity> obtainRepositoryList(String account, int warehouseId) {
        return mCommonMapper.obtainRepositoryList(account, warehouseId);
    }

    @Override
    public List<KeyValueEntity> obtainAreaList(String account, int repositoryId) {
        return mCommonMapper.obtainAreaList(account, repositoryId);
    }

    @Override
    public List<HistoryGoodsEntity> getHistoryGoods(String account, String condition) {
        return mCommonMapper.getHistoryGoods(account, condition);
    }
}
