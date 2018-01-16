package com.teeny.wms.web.service;

import com.teeny.wms.app.model.KeyValueEntity;
import com.teeny.wms.web.model.request.InventoryAddRequestEntity;
import com.teeny.wms.web.model.request.InventoryRequestEntity;
import com.teeny.wms.web.model.request.LotEntity;
import com.teeny.wms.web.model.request.SKUAddRequestEntity;
import com.teeny.wms.web.model.response.GoodsDetailEntity;
import com.teeny.wms.web.model.response.InventoryGoodsEntity;
import com.teeny.wms.web.model.response.SKUEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see InventoryService
 * @since 2017/11/8
 */
public interface InventoryService {

    List<InventoryGoodsEntity> getInventoryList(int id, boolean isMerge, String account);

    int single(int originalId, String account, int userId);

    void complete(List<Integer> ids, String account, int userId);

    void edit(InventoryRequestEntity entity, String account, int userId);

    List<LotEntity> getLotList(int originalId, String account);

    List<KeyValueEntity> getPdType(int type, String account, int sId);

    void addInventory(int type, InventoryAddRequestEntity entity, String account, int sId, int userId);

    List<SKUEntity> getSKUList(int sId, String account, String locationCode, String barcode);

    GoodsDetailEntity getGoodsDetailByCode(String goodsCode, String account);

    /**
     * 单品盘点添加数据
     *
     * @param entity  数据实体
     * @param account 账户
     * @param sId     仓库id
     * @param userId  用户id
     */
    void addSku(SKUAddRequestEntity entity, String account, int sId, int userId);
}
