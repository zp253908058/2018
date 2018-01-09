package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ExWarehouseReviewEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.RecipientEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.ExWarehouseReviewRequestEntity;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReviewService
 * @since 2017/8/3
 */

public interface ReviewService {

    /**
     * 出库复核
     *
     * @return 复核单详情
     */
    @GET("recheck/exWarehouseReview/{billNo}")
    Flowable<ResponseEntity<ExWarehouseReviewEntity>> detail(@Path("billNo") String billNo);

    /**
     * 获取接收人
     *
     * @return List<KeyValueEntity>
     */
    @GET("recheck/recipients")
    Flowable<ResponseEntity<List<RecipientEntity>>> getRecipient();


    /**
     * 获取单据
     *
     * @return List<KeyValueEntity>
     */
    @GET("recheck/bills")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getBillList();

    /**
     * 获取补货数量
     *
     * @return int
     */
    @GET("recheck/replenishment")
    Flowable<ResponseEntity<Integer>> getReplenishmentCount();

    /**
     * 强制完成
     *
     * @return EmptyEntity
     */
    @POST("recheck/complete")
    Flowable<ResponseEntity<EmptyEntity>> complete(@Body ExWarehouseReviewRequestEntity entity);
}
