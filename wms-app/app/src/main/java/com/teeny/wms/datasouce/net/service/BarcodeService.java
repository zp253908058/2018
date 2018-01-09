package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.BarcodeGoodsEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.BarcodeAddRequestEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BarcodeService
 * @since 2018/1/4
 */

public interface BarcodeService {

    @GET("barcode/list")
    Flowable<ResponseEntity<List<BarcodeGoodsEntity>>> getList(@Query("location") String location, @Query("goods") String goods);

    @GET("barcode/goods")
    Flowable<ResponseEntity<BarcodeGoodsEntity>> getGoodsById(@Query("id") int id);

    @GET("barcode/goodsList")
    Flowable<ResponseEntity<List<BarcodeGoodsEntity>>> getGoodsList(@Query("goods") String goods);

    @POST("barcode/add")
    Flowable<ResponseEntity<EmptyEntity>> add(@Body BarcodeAddRequestEntity entity);
}
