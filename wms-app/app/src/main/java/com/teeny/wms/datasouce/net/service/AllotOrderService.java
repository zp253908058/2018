package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.AllotGoodsEntity;
import com.teeny.wms.model.AllotLocationEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.AllotLocationRequestEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotOrderService
 * @since 2018/1/10
 */

public interface AllotOrderService {

    @GET("allot/goodsList")
    Flowable<ResponseEntity<List<AllotGoodsEntity>>> getAllotGoodsList(@Query("location") String location, @Query("goods") String goods, @Query("warehouseId") int warehouseId, @Query("repositoryId") int repositoryId, @Query("areaId") int areaId);

    @POST("allot/add/{id}")
    Flowable<ResponseEntity<EmptyEntity>> select(@Path("id") int id);


    /**
     * 获取仓库
     *
     * @return List<KeyValueEntity>
     */
    @GET("common/warehouseList")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getWarehouseList();

    /**
     * 获取库区
     *
     * @return List<KeyValueEntity>
     */
    @GET("common/repositoryList/{id}")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getRepositoryList(@Path("id") int warehouseId);

    /**
     * 获取区域
     *
     * @return List<KeyValueEntity>
     */
    @GET("common/areaList/{id}")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getAreaList(@Path("id") int repositoryId);

    @GET("allot/getTempleGoodsList")
    Flowable<ResponseEntity<List<AllotGoodsEntity>>> getSelectGoods();

    @POST("allot/complete")
    Flowable<ResponseEntity<EmptyEntity>> complete(@Body AllotLocationRequestEntity entity);

    @GET("allot/locationList/{id}")
    Flowable<ResponseEntity<List<AllotLocationEntity>>> getLocationList(@Path("id") int id);
}
