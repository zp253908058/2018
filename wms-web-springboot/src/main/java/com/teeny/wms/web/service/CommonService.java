package com.teeny.wms.web.service;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.StringMapEntity;
import com.teeny.wms.web.model.response.DocumentResponseEntity;
import com.teeny.wms.web.model.response.HistoryGoodsEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see CommonService
 * @since 2017/11/7
 */
public interface CommonService {

    List<KeyValueEntity> getWarehouseList(String account);

    List<KeyValueEntity> getAreaList(int sId, String account);

    List<StringMapEntity> getHistoryLocation(String account, int pId);

    DocumentResponseEntity getDocumentList(String account, int sId);

    List<KeyValueEntity> obtainWarehouseList(String account);

    List<KeyValueEntity> obtainRepositoryList(String account, int warehouseId);

    List<KeyValueEntity> obtainAreaList(String account, int repositoryId);

    List<HistoryGoodsEntity> getHistoryGoods(String account, String condition);
}
