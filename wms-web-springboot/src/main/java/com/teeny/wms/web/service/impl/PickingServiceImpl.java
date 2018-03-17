package com.teeny.wms.web.service.impl;

import com.teeny.wms.app.exception.InnerException;
import com.teeny.wms.util.Validator;
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
    public OutputPickingOrderEntity initialize(String account, int userId) {
        //这个方法执行步骤
        //1.执行存储过程获取单据id
        Integer id = mMapper.getIdByTrigger(account, userId);
        //如果单据不存在或者为0,直接返回
        if (id == null || id == 0) {
            throw new InnerException("没有可用单据.");
        }
        //2.根据存储过程返回id查找数据
        OutputPickingOrderEntity entity = mMapper.getDataById(account, id);
        //如果单据返回为空或者单据详情为空,更新单据状态并直接返回
        if (entity == null || Validator.isEmpty(entity.getDataList())) {
            if (entity != null) {
                mMapper.updateBillState(account, entity.getId());
            }
            throw new InnerException("没有可用单据.");
        }
        return entity;
    }

    @Override
    public List<OutPickingTaskEntity> getTaskList(String account, int userId) {
        return mMapper.getTaskList(account, userId);
    }

    @Override
    public void complete(String account, OutputPickingRequestEntity entity, int userId) {
        mMapper.delete(account, entity.getId());
        if (Validator.isNotEmpty(entity.getList())) {
            mMapper.add(account, entity.getId(), entity.getList());
        }
        mMapper.updateDetailDate(account, entity.getDetailId(), entity.getNumber(), userId);
        mMapper.updateBillState(account, entity.getId());
        mMapper.updateDate(account, entity.getId());
    }
}
