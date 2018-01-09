package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.SKUEntity;
import com.teeny.wms.model.SKUGoodsDetailEntity;
import com.teeny.wms.model.request.SKUAddRequestEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SKUService
 * @since 2017/8/24
 */

public interface SKUService {

    /**
     * 获取商品列表
     *
     * @return List<SKUEntity>
     */
    @GET("productsInventory/list")
    Flowable<ResponseEntity<List<SKUEntity>>> getList(@Query("location") String location, @Query("barcode") String barcode);

    /**
     * 保存
     *
     * @return EmptyEntity
     */
    @POST("productsInventory/update")
    Flowable<ResponseEntity<EmptyEntity>> save(@Body SKUAddRequestEntity entity);

    /**
     * 根据商品码获取商品详情
     *
     * @return EmptyEntity
     */
    @GET("productsInventory/detail")
    Flowable<ResponseEntity<List<SKUGoodsDetailEntity>>> getGoodsDetail(@Query("goodsCode") String goodsCode);

    /**
     * 添加单品
     *
     * @return EmptyEntity
     */
    @PUT("productsInventory/add")
    Flowable<ResponseEntity<EmptyEntity>> add(@Body SKUAddRequestEntity entity);
}
