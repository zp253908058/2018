package com.teeny.wms.web.repository;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.request.AllotLocationRequestEntity;
import com.teeny.wms.web.model.response.AllocationEntity;
import com.teeny.wms.web.model.response.AllotEntity;
import com.teeny.wms.web.model.response.AllotGoodsEntity;
import com.teeny.wms.web.model.response.AllotLocationEntity;
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
 * @see AllotMapper
 * @since 2017/11/7
 */
@Repository
@Mapper
public interface AllotMapper {


    List<AllotEntity> getList(@Param("billNo") String billNo, @Param("goodsCode") String goodsCode, @Param("sId") int sId, @Param("saId") int saId, @Param("account") String account);

    @Update("UPDATE ${account}.dbo.pda_TranBill SET pdaReTime=getdate(), pdastates=1 WHERE billnumber = #{billNo}")
    void updateStatus(@Param("billNo") String billNo, @Param("account") String account);

    void updateAll(@Param("id") int id, @Param("list") List<Integer> ids, @Param("account") String account, @Param("userId") int userId);

    void updateOne(@Param("id") int id, @Param("account") String account, @Param("userId") int userId);

    @Select("SELECT d.quantity AS amount, l.loc_code AS locationCode FROM ${account}.dbo.pda_TranBill_D d LEFT JOIN ${account}.dbo.pda_location l ON d.location_id2 = l.l_id WHERE d.original_id = #{id}")
    List<AllocationEntity> getLocationById(@Param("id") int id, @Param("account") String account);

    List<KeyValueEntity> getOrderList(@Param("saId") int saId, @Param("sId") int sId, @Param("account") String account);

    List<KeyValueEntity> getGoodsCode(@Param("account") String account, @Param("sId") int sId, @Param("saId") int saId);

    @Select("SELECT d.smb_id FROM ${account}.dbo.pda_TranBill_D d WHERE d.original_id=#{id}")
    List<Integer> getIdsByOriginalId(@Param("id") int id, @Param("account") String account);

    void copyData(@Param("id") int id, @Param("amount") float amount, @Param("locationId") int locationId, @Param("account") String account, @Param("userId") int userId);

    void deleteByIds(@Param("list") List<Integer> ids, @Param("originalId") int originalId, @Param("account") String account);

    void updateBillStatus(@Param("account") String account, @Param("id") int id);

    List<AllotGoodsEntity> getAllotGoodsList(@Param("account") String account, @Param("location") String location, @Param("goods") String goods, @Param("warehouseId") int warehouseId, @Param("repositoryId") int repositoryId, @Param("areaId") int areaId);

    void generateBill(@Param("account") String account, @Param("userId") int userId, @Param("billNo") String billNo);

    void generateAllotOrder(@Param("account") String account, @Param("id") int id, @Param("userId") int userId);

    List<AllotGoodsEntity> getTempleGoodsList(@Param("account") String account, @Param("userId") int userId);

    void completeAllot(@Param("account") String account, @Param("id") int id, @Param("billId") int billId, @Param("list") List<AllotLocationEntity> list);

    List<AllotLocationEntity> getLocationList(@Param("account") String account, @Param("id") int id);
}
