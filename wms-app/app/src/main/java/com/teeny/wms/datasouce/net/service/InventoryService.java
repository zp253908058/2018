package com.teeny.wms.datasouce.net.service;

import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.InventoryCountEntity;
import com.teeny.wms.model.InventoryGoodsWrapperEntity;
import com.teeny.wms.model.InventoryInitializeEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ResponseEntity;

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
 * @see InventoryService
 * @since 2017/12/27
 */

public interface InventoryService {

    /**
     * 获取盘点类型
     *
     * @param type 盘点类型： 1门店盘点 2复盘 4 仓库盘点
     * @return List<KeyValueEntity>
     */
    @GET("inventory/pdType")
    Flowable<ResponseEntity<List<KeyValueEntity>>> getPdType(@Query("type") int type);

    /**
     * 初始化盘点
     *
     * @param id   盘点单id
     * @param type 盘点类型： 1门店盘点 2复盘 4 仓库盘点
     * @return 库区、区域、统计数量
     */
    @GET("inventory/initialize")
    Flowable<ResponseEntity<InventoryInitializeEntity>> initialize(@Query("id") int id, @Query("type") int type);

    /**
     * 统计数量
     *
     * @param id           盘点单id
     * @param repositoryId 库区id
     * @param areaId       区域id
     * @param type         盘点类型： 1门店盘点 2复盘 4 仓库盘点
     * @return 统计数量
     */
    @GET("inventory/count")
    Flowable<ResponseEntity<InventoryCountEntity>> count(@Query("id") int id, @Query("repositoryId") int repositoryId, @Query("areaId") int areaId, @Query("type") int type);

    /**
     * 获取商品清单列表
     *
     * @param id           盘点单id
     * @param repositoryId 库区id
     * @param areaId       区域id
     * @param locationCode 货位码
     * @param type         盘点类型： 1门店盘点 2复盘 4 仓库盘点
     * @return 商品清单列表
     */
    @GET("inventory/home_data")
    Flowable<ResponseEntity<InventoryGoodsWrapperEntity>> getList(@Query("id") int id, @Query("repositoryId") int repositoryId, @Query("areaId") int areaId, @Query("locationCode") String locationCode, @Query("type") int type);

    /**
     * 组完成
     *
     * @param ids 需要完成的id列表
     * @return EmptyEntity
     */
    @POST("inventory/complete")
    Flowable<ResponseEntity<EmptyEntity>> complete(@Body List<Integer> ids);
}
