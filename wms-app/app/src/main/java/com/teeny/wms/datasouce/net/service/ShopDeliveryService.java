package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.InventoryGoodsWrapperEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.ShopDeliveryGoodsEntity;
import com.teeny.wms.model.request.ShopDeliveryRequestEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopDeliveryService
 * @since 2018/1/18
 */

public interface ShopDeliveryService {

    @GET("shop/delivery/orderList")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getOrderList();

    @GET("shop/delivery/goodsList/{id}")
    Flowable<ResponseEntity<List<ShopDeliveryGoodsEntity>>> getDeliveryGoodsList(@Path("id") int id);

    @POST("shop/delivery/complete")
    Flowable<ResponseEntity<EmptyEntity>> complete(@Body ShopDeliveryRequestEntity entity);

    @POST("shop/delivery/single/{id}/{billId}")
    @FormUrlEncoded
    Flowable<ResponseEntity<EmptyEntity>> single(@Path("id") int id, @Path("billId") int billId);

    @POST("shop/delivery/single")
    @FormUrlEncoded
    Flowable<ResponseEntity<EmptyEntity>> single(@Field("id") int id, @Field("amount") float amount, @Field("remark") String remark, @Field("billId") int billId);
}
