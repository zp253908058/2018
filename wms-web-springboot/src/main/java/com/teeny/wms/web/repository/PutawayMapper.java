package com.teeny.wms.web.repository;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.response.AllocationEntity;
import com.teeny.wms.web.model.response.PutawayEntity;
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
 * @see PutawayMapper
 * @since 2017/11/8
 */
@Repository
@Mapper
public interface PutawayMapper {

    List<KeyValueEntity> getSaListBysId(@Param("sId") int sId, @Param("account") String account);

    List<KeyValueEntity> getBillList(@Param("sId") int sId, @Param("saId") int saId, @Param("account") String account);

    List<PutawayEntity> getGoodsDetailList(@Param("orderNoId") String orderNoId, @Param("account") String account, @Param("sId") int sId);

    @Select("SELECT d.EligibleQty AS amount, l.loc_code AS locationCode FROM ${account}.dbo.pda_PutOnBill_D d LEFT JOIN ${account}.dbo.pda_location l ON d.Location_id = l.l_id WHERE d.original_id = #{id}")
    List<AllocationEntity> getLocationListById(@Param("id") int id, @Param("account") String account);

    @Update("UPDATE ${account}.dbo.pda_PutOnBill SET pdaReTime = getdate(), pdastates = 1 WHERE billnumber = #{orderNo}")
    void updateStatus(@Param("account") String account, @Param("orderNo") String orderNo);

    void all(@Param("id") int id, @Param("list") List<Integer> ids, @Param("account") String account, @Param("userId") int userId);

    void single(@Param("id") int id, @Param("account") String account, @Param("userId") int userId);

    @Select("SELECT d.smb_id FROM ${account}.dbo.pda_PutOnBill_D d WHERE d.original_id=#{id}")
    List<Integer> getIdsByOriginalId(@Param("id") int id, @Param("account") String account);

    void copyDataByParam(@Param("id") int id, @Param("amount") float amount, @Param("locationId") int locationId, @Param("account") String account, @Param("userId") int userId);

    void deleteByIds(@Param("list") List<Integer> ids, @Param("account") String account);

    void updateBillStatus(@Param("id") int id, @Param("account") String account);
}
