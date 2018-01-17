package com.teeny.wms.web.service.impl;

import com.teeny.wms.app.exception.InnerException;
import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.web.model.dto.ReceivingBillEntity;
import com.teeny.wms.web.model.response.ReceivingAddEntity;
import com.teeny.wms.web.model.response.ReceivingEntity;
import com.teeny.wms.web.model.response.ReceivingLotEntity;
import com.teeny.wms.web.model.request.ReceivingRequestEntity;
import com.teeny.wms.web.repository.ReceivingMapper;
import com.teeny.wms.web.service.ReceivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReceivingServiceImpl
 * @since 2017/11/1
 */
@Service
@Transactional
public class ReceivingServiceImpl implements ReceivingService {

    private ReceivingMapper mReceivingMapper;

    @Autowired
    public void setReceivingMapper(ReceivingMapper receivingMapper) {
        mReceivingMapper = receivingMapper;
    }

    @Override
    public List<KeyValueEntity> getUnitList(String account, int sId) {
        return mReceivingMapper.getUnitList(account, sId);
    }

    @Override
    public List<ReceivingEntity> getDetailByUnitId(String account, int unitId) {
        List<ReceivingEntity> result = mReceivingMapper.getDetailByUnitId(account, unitId);
        updateStatus(account, result);
        return result;
    }

    @Override
    public List<ReceivingEntity> getDetailByOrderNo(String account, String orderNo) {
        List<ReceivingEntity> result = mReceivingMapper.getDetailByOrderNo(account, orderNo);
        updateStatus(account, result);
        return result;
    }

    private void updateStatus(String account, List<ReceivingEntity> list) {
        if (Validator.isEmpty(list)) {
            return;
        }
        List<Integer> ids = new ArrayList<>();
        for (ReceivingEntity entity : list) {
            ids.add(entity.getOrderId());
        }
        mReceivingMapper.updateStatus(account, ids);
    }

    @Override
    public List<ReceivingLotEntity> getLotList(String account, int id) {
        return mReceivingMapper.getLotList(account, id);
    }

    @Override
    public void complete(String account, ReceivingRequestEntity entity, int userId) {
        List<ReceivingAddEntity> param = entity.getParam();
        if (Validator.isEmpty(param)) {
            throw new InnerException("未添加批号.");
        }
        //查找出原始数据中任意一条作为模板
        ReceivingBillEntity original = mReceivingMapper.getReceivingBill(account, entity.getId());
        //删除原始所有数据
        mReceivingMapper.deleteByOriginalId(account, entity.getId());
        //得到插入的数据
        List<ReceivingBillEntity> target = convert(original, entity.getParam());
        System.out.println(target);
        //插入新数据
        mReceivingMapper.addData(account, target, userId);
        //更新大单状态
        mReceivingMapper.updateOrderStatus(account, entity.getId());
    }

    private List<ReceivingBillEntity> convert(ReceivingBillEntity temp, List<ReceivingAddEntity> params) {
        List<ReceivingBillEntity> result = new ArrayList<>();
        for (ReceivingAddEntity entity : params) {
            ReceivingBillEntity in = new ReceivingBillEntity(temp);
            in.setDealStates(1);
            in.setBatchno(entity.getLotNo());
            in.setValiddate(entity.getValidityDate());
            in.setEligibleQty(entity.getAmount());
            in.setTaxPrice(entity.getPrice());
            in.setRownumber(entity.getSerialNo());
            in.setRetailQty(entity.getLhAmount());
            in.setWholeQty(entity.getZhAmount());
            result.add(in);
        }
        return result;
    }
}
