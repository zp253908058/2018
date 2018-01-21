package com.teeny.wms.web.service;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.request.OutputPickingRequestEntity;
import com.teeny.wms.web.model.response.OutPickingTaskEntity;
import com.teeny.wms.web.model.response.OutputPickingOrderEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see PickingService
 * @since 2018/1/21
 */
public interface PickingService {
    List<KeyValueEntity> getOrderList(String account, int userId);

    OutputPickingOrderEntity getData(String account, int id);

    List<OutPickingTaskEntity> getTaskList(String account, int userId);

    void complete(String account, OutputPickingRequestEntity entity, int userId);
}
