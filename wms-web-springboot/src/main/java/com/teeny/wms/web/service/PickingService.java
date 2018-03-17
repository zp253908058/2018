package com.teeny.wms.web.service;

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
    OutputPickingOrderEntity initialize(String account, int userId);

    List<OutPickingTaskEntity> getTaskList(String account, int userId);

    void complete(String account, OutputPickingRequestEntity entity, int userId);
}
