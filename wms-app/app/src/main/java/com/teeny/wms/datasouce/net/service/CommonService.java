package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.GoodsAllocationEntity;
import com.teeny.wms.model.ResponseEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see CommonService
 * @since 2017/9/22
 */

public interface CommonService {

    /**
     * 获取仓库
     *
     * @return List<KeyValueEntity>
     */
    @GET("common/historyLocation")
    Flowable<ResponseEntity<List<GoodsAllocationEntity>>> getHistoryAllocation(@Query("goodsId") int goodsId);
}
