package com.teeny.wms.web.service;

import com.teeny.wms.web.model.request.BarcodeAddRequestEntity;
import com.teeny.wms.web.model.response.BarcodeGoodsEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BarcodeService
 * @since 2018/1/16
 */
public interface BarcodeService {
    List<BarcodeGoodsEntity> getList(String account, String location, String goods);

    BarcodeGoodsEntity getGoodsById(String account, int id);

    List<BarcodeGoodsEntity> getGoodsList(String account, String goods);

    void add(String account, BarcodeAddRequestEntity entity);
}
