package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.AllotGoodsEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ResponseEntity;

import java.util.List;

import io.reactivex.Flowable;
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
    Flowable<ResponseEntity<List<AllotGoodsEntity>>> getAllotGoodsList(@Query("location") String location, @Query("goods") String goods);

    @POST("allot/select/{id}")
    Flowable<ResponseEntity<EmptyEntity>> select(@Path("id") int id);
}
