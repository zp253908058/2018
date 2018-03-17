package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.OutPickingTaskEntity;
import com.teeny.wms.model.OutputPickingOrderEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.OutputPickingRequestEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see PickingService
 * @since 2018/1/20
 */

public interface PickingService {

    /***
     * 执行初始化操作
     * @return OutputPickingOrderEntity
     */
    @GET("picking/initialize")
    Flowable<ResponseEntity<OutputPickingOrderEntity>> initialize();

    /**
     * 获取工作任务
     *
     * @return List<OutPickingTaskEntity>
     */
    @GET("picking/taskList")
    Flowable<ResponseEntity<List<OutPickingTaskEntity>>> getTaskList();

    @POST("picking/complete")
    Flowable<ResponseEntity<EmptyEntity>> complete(@Body OutputPickingRequestEntity entity);
}
