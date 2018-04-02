package com.teeny.wms.web.repository;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.dto.ReviewBillEntity;
import com.teeny.wms.web.model.response.ExWarehouseReviewEntity;
import com.teeny.wms.web.model.response.RecipientEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReviewMapper
 * @since 2017/11/7
 */
@Repository
@Mapper
public interface ReviewMapper {

    @Select("SELECT b.smb_id AS smbId,b.bill_id AS billId, b.PickType AS pickType, b.DealStates AS dealStates FROM ${account}.dbo.pda_CheckBill_B b WHERE b.barcode=#{code}")
    ReviewBillEntity getBillByCode(@Param("code") String code, @Param("account") String account);

    void updateStatus(@Param("smbId") int smbId, @Param("account") String account, @Param("userId") int userId);

    void updateState(@Param("billId") int billId, @Param("account") String account);

    void updateBillStatus(@Param("billId") int billId, @Param("account") String account);

    ExWarehouseReviewEntity getDetail(@Param("billNo") String billNo, @Param("account") String account);

    List<RecipientEntity> getRecipients(@Param("account") String account, @Param("sId") int sId);

    List<KeyValueEntity> getBillList(@Param("sId") int sId, @Param("account") String account);

    String getReplenishmentCount(@Param("account") String account, @Param("sId") int sId);

    @Update("UPDATE ${account}.dbo.pda_CheckBill SET diff_remark=#{remark,jdbcType=VARCHAR}, recipient_id=#{recipientId,jdbcType=INTEGER}, billstates=13 WHERE billnumber=#{billNo}")
    void complete(@Param("billNo") String billNo, @Param("recipientId") int recipientId, @Param("remark") String remark, @Param("account") String account);
}
