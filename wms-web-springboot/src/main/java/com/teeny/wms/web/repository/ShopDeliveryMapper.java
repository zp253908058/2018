package com.teeny.wms.web.repository;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.response.ShopDeliveryGoodsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopDeliveryMapper
 * @since 2018/1/18
 */
@Mapper
@Repository
public interface ShopDeliveryMapper {

    List<KeyValueEntity> getOrderList(@Param("account") String account);

    List<ShopDeliveryGoodsEntity> getDeliveryGoodsList(@Param("account") String account, @Param("id") int id);

    void updateState(@Param("account") String account, @Param("id") int id);

    void complete(@Param("account") String account, @Param("list") List<Integer> ids, @Param("billId") int billId, @Param("userId") int userId);

    void single(@Param("account") String account, @Param("id") int id, @Param("billId") int billId, @Param("amount") float amount, @Param("remark") String remark, @Param("userId") int userId);
}
