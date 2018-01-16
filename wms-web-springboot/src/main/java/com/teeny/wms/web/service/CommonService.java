package com.teeny.wms.web.service;

import com.teeny.wms.app.model.KeyValueEntity;
import com.teeny.wms.app.model.StringMapEntity;
import com.teeny.wms.web.model.response.DocumentResponseEntity;

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
}
