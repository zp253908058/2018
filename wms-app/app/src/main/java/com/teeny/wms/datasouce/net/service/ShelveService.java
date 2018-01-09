package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.AllocationEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.ShelveEntity;
import com.teeny.wms.model.request.AllotListRequestEntity;

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
 * @see ShelveService
 * @since 2017/8/11
 */

public interface ShelveService {

    /**
     * 获取库区
     *
     * @return List<KeyValueEntity>
     */
    @GET("shelve/saList")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getSaList();

    /**
     * 获取上架单号
     *
     * @param id 库区id
     * @return List<KeyValueEntity>
     */
    @GET("shelve/billList/{id}")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getOrderNoList(@Path("id") int id);

    /**
     * 获取商品详情列表
     *
     * @return List<ShelveEntity>
     */
    @GET("shelve/goodsDetailList/{orderNo}")
    Flowable<ResponseEntity<List<ShelveEntity>>> getGoodsDetailList(@Path("orderNo") String orderNo);

    /**
     * 全部上架
     *
     * @return List<KeyValueEntity>
     */
    @POST("shelve/all")
    Flowable<ResponseEntity<EmptyEntity>> all(@Body List<Integer> ids);

    /**
     * 单个上架
     *
     * @return List<KeyValueEntity>
     */
    @POST("shelve/single")
    @FormUrlEncoded
    Flowable<ResponseEntity<EmptyEntity>> single(@Field("id") int id);

    /**
     * 修改
     *
     * @return List<KeyValueEntity>
     */
    @POST("shelve/update")
    Flowable<ResponseEntity<EmptyEntity>> update(@Body AllotListRequestEntity entity);

    /**
     * 获取货位
     *
     * @return List<AllocationEntity>
     */
    @GET("shelve/locationList/{id}")
    Flowable<ResponseEntity<List<AllocationEntity>>> getLocations(@Path("id") int id);
}
