package com.teeny.wms.web.service.impl;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.request.OutputPickingRequestEntity;
import com.teeny.wms.web.model.response.OutPickingTaskEntity;
import com.teeny.wms.web.model.response.OutputPickingOrderEntity;
import com.teeny.wms.web.repository.PickingMapper;
import com.teeny.wms.web.service.PickingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see PickingServiceImpl
 * @since 2018/1/21
 */
@Service
@Transactional
public class PickingServiceImpl implements PickingService {

    private PickingMapper mMapper;

    @Autowired
    public void setMapper(PickingMapper mapper) {
        mMapper = mapper;
    }

    @Override
    public List<KeyValueEntity> getOrderList(String account, int userId) {
        //TODO  trigger
        return mMapper.getOrderList(account, userId);
    }

    @Override
    public OutputPickingOrderEntity getData(String account, int id) {
        return mMapper.getData(account, id);
    }

    @Override
    public List<OutPickingTaskEntity> getTaskList(String account, int userId) {
        return mMapper.getTaskList(account, userId);
    }

    @Override
    public void complete(String account, OutputPickingRequestEntity entity, int userId) {
        mMapper.delete(account, entity.getId());
        mMapper.add(account, entity.getId(), entity.getList());
        mMapper.updateDetailDate(account, entity.getId(), userId);
        mMapper.updateBillState(account, entity.getId());
        mMapper.updateDate(account, entity.getId());
    }
}
