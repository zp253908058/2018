package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.DocumentResponseEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ResponseEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see HomeService
 * @since 2017/8/1
 */

public interface HomeService {

    /**
     * 获取仓库接口
     *
     * @return 仓库数据
     */
    @GET("home/warehouseList")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getWarehouseList();

    /**
     * 获取单据信息
     *
     * @return 首页信息
     */
    @GET("home/documentList")
    Flowable<ResponseEntity<DocumentResponseEntity>> getDocumentInfo();

    /**
     * 获取单据信息
     *
     * @return 首页信息
     */
    @GET("home/username")
    Flowable<ResponseEntity<String>> getUsername();
}
