package com.teeny.wms.web.repository;

import com.teeny.wms.web.model.request.BarcodeAddRequestEntity;
import com.teeny.wms.web.model.response.BarcodeGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BarcodeMapper
 * @since 2018/1/16
 */
@Repository
@Mapper
public interface BarcodeMapper {

    List<BarcodeGoodsEntity> getList(@Param("account") String account, @Param("location") String location, @Param("goods") String goods);

    List<BarcodeGoodsEntity> getListByGoods(@Param("account") String account, @Param("goods") String goods);

    BarcodeGoodsEntity getGoodsById(@Param("account") String account, @Param("id") int id);

    List<BarcodeGoodsEntity> getGoodsList(@Param("account") String account, @Param("goods") String goods);

    void add(@Param("account") String account, @Param("item") BarcodeAddRequestEntity entity);
}
