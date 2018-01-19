package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.AllocationEntity;
import com.teeny.wms.model.AllotListEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.AllotListRequestEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotListService
 * @since 2017/8/24
 */

public interface AllotListService {

    /**
     * 获取商品列表
     *
     * @param billCode  单据编号
     * @param goodsCode 商品编号
     * @param sId       仓库id
     * @param saId      库区id
     * @return List<AllotListEntity>
     */
    @GET("allot/list")
    Flowable<ResponseEntity<List<AllotListEntity>>> getList(@Query("billCode") String billCode, @Query("goodsCode") String goodsCode, @Query("sId") int sId, @Query("saId") int saId);


    /**
     * 获取仓库
     *
     * @return List<KeyValueEntity>
     */
    @GET("allot/warehouseList")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getWarehouseList();

    /**
     * 获取区域
     *
     * @return List<KeyValueEntity>
     */
    @GET("allot/saList/{id}")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getSaList(@Path("id") int warehouseId);

    /**
     * 全部完成
     *
     * @return null
     */
    @POST("allot/updateAll")
    Flowable<ResponseEntity<EmptyEntity>> complete(@Body List<Integer> ids);

    /**
     * 单个完成
     *
     * @return null
     */
    @POST("allot/updateOne")
    @FormUrlEncoded
    Flowable<ResponseEntity<EmptyEntity>> single(@Field("id") int id);

    /**
     * 单个完成
     *
     * @return null
     */
    @POST("allot/update")
    Flowable<ResponseEntity<EmptyEntity>> update(@Body AllotListRequestEntity entity);

    /**
     * 获取货位
     *
     * @return List<AllocationEntity>
     */
    @GET("allot/getLocations/{id}")
    Flowable<ResponseEntity<List<AllocationEntity>>> getLocations(@Path("id") int id);

    /**
     * 获取单据
     *
     * @return List<KeyValueEntity>
     */
    @GET("allot/billList")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getDocumentList(@Query("sId") int sId, @Query("saId") int saId);

    /**
     * 获取商品
     *
     * @return List<KeyValueEntity>
     */
    @GET("allot/goodsCode")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getGoodsList(@Query("sId") int sId, @Query("saId") int saId);
}
