package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.LotEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.SKUGoodsDetailEntity;
import com.teeny.wms.model.WarehouseGoodsEntity;
import com.teeny.wms.model.request.InventoryAddRequestEntity;
import com.teeny.wms.model.request.WarehouseInventoryRequestEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see WarehouseService
 * @since 2017/8/24
 */

public interface WarehouseService {
//    /**
//     * 获取库区列表
//     *
//     * @return List<KeyValueEntity>
//     */
//    @GET("warehouseFirst/saList")
//    Flowable<ResponseEntity<List<KeyValueEntity>>> getRepositoryList();
//
//
//    /**
//     * 获取区域列表
//     *
//     * @return List<KeyValueEntity>
//     */
//    @GET("warehouseFirst/areaList")
//    Flowable<ResponseEntity<List<KeyValueEntity>>> getAreaList();

    /**
     * 获取盘点商品列表
     *
     * @param id 盘点单id
     * @return List<KeyValueEntity>
     */
    @GET("warehouseFirst/getList")
    Flowable<ResponseEntity<List<WarehouseGoodsEntity>>> getGoodsList(@Query("id") int id);

    /**
     * 单个完成
     *
     * @param id 盘点类型
     * @return EmptyEntity
     */
    @POST("warehouseFirst/single")
    @FormUrlEncoded
    Flowable<ResponseEntity<Integer>> single(@Field("id") int id);

    /**
     * 组完成
     *
     * @param ids 需要完成的id列表
     * @return EmptyEntity
     */
    @POST("warehouseFirst/complete")
    Flowable<ResponseEntity<EmptyEntity>> complete(@Body List<Integer> ids);

    /**
     * 编辑
     *
     * @param entity 盘点编辑请求实体
     * @return EmptyEntity
     */
    @POST("warehouseFirst/edit")
    Flowable<ResponseEntity<EmptyEntity>> edit(@Body WarehouseInventoryRequestEntity entity);

    /**
     * 获取盘点商品列表
     *
     * @param originalId 盘点单id
     * @return List<KeyValueEntity>
     */
    @GET("warehouseFirst/getLotList")
    Flowable<ResponseEntity<List<LotEntity>>> getLotList(@Query("originalId") int originalId);


    /**
     * 获取盘点类型
     *
     * @return List<String>
     */
    @GET("warehouseFirst/pdType")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getPdType();

    /**
     * 根据商品码获取商品详情
     *
     * @return EmptyEntity
     */
    @GET("productsInventory/detail")
    Flowable<ResponseEntity<List<SKUGoodsDetailEntity>>> getGoodsDetail(@Query("goodsCode") String goodsCode);

    /**
     * 盘点新增
     *
     * @return EmptyEntity
     */
    @PUT("warehouseFirst/add")
    Flowable<ResponseEntity<EmptyEntity>> add(@Body InventoryAddRequestEntity entity);
}
