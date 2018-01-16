package com.teeny.wms.web.repository;

import com.teeny.wms.app.model.KeyValueEntity;
import com.teeny.wms.web.model.dto.ReceivingBillEntity;
import com.teeny.wms.web.model.response.ReceivingAddEntity;
import com.teeny.wms.web.model.response.ReceivingEntity;
import com.teeny.wms.web.model.response.ReceivingLotEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReceivingMapper
 * @since 2017/11/1
 */

@Repository
@Mapper
public interface ReceivingMapper {

    List<KeyValueEntity> getUnitList(@Param("account") String account, @Param("sId") int sId);

    List<ReceivingEntity> getDetailByUnitId(@Param("account") String account, @Param("unitId") int unitId);

    List<ReceivingEntity> getDetailByOrderNo(@Param("account") String account, @Param("orderNo") String orderNo);

    List<ReceivingLotEntity> getLotList(@Param("account") String account, @Param("id") int id);

    void updateStatus(@Param("account") String account, @Param("list") List<Integer> ids);

    ReceivingBillEntity getReceivingBill(@Param("account") String account, @Param("id") int originalId);

    @Delete("DELETE FROM ${account}.dbo.pda_RecBill_D WHERE original_id=#{id}")
    void deleteByOriginalId(@Param("account") String account, @Param("id") int id);

    void addData(@Param("account") String account, @Param("list") List<ReceivingBillEntity> target, @Param("id") int userId);

    void updateOrderStatus(@Param("account") String account, @Param("id") int id);
}
