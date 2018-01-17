package com.teeny.wms.web.service.impl;


import com.teeny.wms.util.Validator;
import com.teeny.wms.web.model.request.BarcodeAddRequestEntity;
import com.teeny.wms.web.model.response.BarcodeGoodsEntity;
import com.teeny.wms.web.repository.BarcodeMapper;
import com.teeny.wms.web.service.BarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BarcodeServiceImpl
 * @since 2018/1/6
 */
@Service
@Transactional
public class BarcodeServiceImpl implements BarcodeService {

    private BarcodeMapper mBarcodeMapper;

    @Autowired
    public void setBarcodeMapper(BarcodeMapper mapper) {
        mBarcodeMapper = mapper;
    }

    @Override
    public List<BarcodeGoodsEntity> getList(String account, String location, String goods) {
        if (Validator.isEmpty(location)) {
            return mBarcodeMapper.getListByGoods(account, goods);
        }
        return mBarcodeMapper.getList(account, location, goods);
    }

    @Override
    public BarcodeGoodsEntity getGoodsById(String account, int id) {
        return mBarcodeMapper.getGoodsById(account, id);
    }

    @Override
    public List<BarcodeGoodsEntity> getGoodsList(String account, String goods) {
        return mBarcodeMapper.getGoodsList(account, goods);
    }

    @Override
    public void add(String account, BarcodeAddRequestEntity entity) {
        mBarcodeMapper.add(account, entity);
    }
}
