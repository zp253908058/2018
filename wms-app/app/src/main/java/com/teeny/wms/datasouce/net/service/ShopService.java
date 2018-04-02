package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.LotEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.SKUGoodsDetailEntity;
import com.teeny.wms.model.request.InventoryAddRequestEntity;
import com.teeny.wms.model.request.ShopInventoryRequestEntity;

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
 * @see ShopService
 * @since 2017/8/20
 */

public interface ShopService {

    /**
     * 单个完成
     *
     * @param id 明细id
     * @return EmptyEntity
     */
    @POST("shopFirst/single")
    @FormUrlEncoded
    Flowable<ResponseEntity<Integer>> single(@Field("id") int id);

    /**
     * 组完成
     *
     * @param ids 需要完成的id列表
     * @return EmptyEntity
     */
    @POST("shopFirst/complete")
    Flowable<ResponseEntity<EmptyEntity>> complete(@Body List<Integer> ids);

    /**
     * 编辑
     *
     * @param entity 盘点编辑请求实体
     * @return EmptyEntity
     */
    @POST("shopFirst/singleComplete")
    Flowable<ResponseEntity<EmptyEntity>> edit(@Body ShopInventoryRequestEntity entity);

    /**
     * 获取批次
     *
     * @param originalId 盘点单id
     * @return List<KeyValueEntity>
     */
    @GET("shopFirst/getLotList")
    Flowable<ResponseEntity<List<LotEntity>>> getLotList(@Query("originalId") int originalId);

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
    @PUT("shopFirst/add")
    Flowable<ResponseEntity<EmptyEntity>> add(@Body InventoryAddRequestEntity entity);
}
