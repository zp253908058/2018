package com.teeny.wms.web.repository;

import com.teeny.wms.app.model.KeyValueEntity;
import com.teeny.wms.web.model.request.InventoryAddRequestEntity;
import com.teeny.wms.web.model.request.LotEntity;
import com.teeny.wms.web.model.request.SKUAddRequestEntity;
import com.teeny.wms.web.model.response.GoodsDetailEntity;
import com.teeny.wms.web.model.response.InventoryGoodsEntity;
import com.teeny.wms.web.model.response.SKUEntity;
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
 * @see InventoryMapper
 * @since 2017/11/8
 */

@Repository
@Mapper
public interface InventoryMapper {


    /**
     * 获取盘点数据列表
     *
     * @param id      对应字段billid
     * @param isMerge 是否合并
     * @param account 账套
     * @return
     */
    List<InventoryGoodsEntity> getInventoryList(@Param("id") int id, @Param("isMerge") boolean isMerge, @Param("account") String account);

    /**
     * 更新盘点单状态
     *
     * @param id      盘点单id
     * @param status  盘点单状态 0提供,1pda已读取,2pda已完成,3pda已回写(后台更改,前台不做)
     * @param account 账套
     */
    @Update("UPDATE ${account}.dbo.pda_pdBill SET pdastates=#{status},pdaWrTime=getdate() WHERE billid = #{id}")
    void updateInventoryStatus(@Param("id") int id, @Param("status") int status, @Param("account") String account);

    void single(@Param("originalId") int originalId, @Param("account") String account, @Param("userId") int userId);

    void complete(@Param("list") List<Integer> ids, @Param("account") String account, @Param("userId") int userId);

    @Select("SELECT d.smb_id FROM ${account}.dbo.pda_pdBill_D d WHERE d.original_id=#{id}")
    List<Integer> getIdsByOriginalId(@Param("id") int id, @Param("account") String account);

    //获取批次
    @Select("SELECT CONVERT(varchar(100), d.Validdate, 23) AS validateDate, d.Batchno AS lotNo, d.EligibleQty AS count FROM ${account}.dbo.pda_pdBill_D d WHERE d.original_id = #{originalId}")
    List<LotEntity> getLotList(@Param("originalId") int originalId, @Param("account") String account);

    List<KeyValueEntity> getPdType(@Param("account") String account, @Param("type") int type, @Param("sId") int sId);

    void edit(@Param("id") int id, @Param("list") List<LotEntity> param, @Param("account") String account, @Param("userId") int userId);

    void deleteByIds(@Param("list") List<Integer> ids, @Param("account") String account);

    void updateBillStatus(@Param("id") int id, @Param("account") String account);

    //添加数据
    void addInventory(@Param("item") InventoryAddRequestEntity entity, @Param("type") int type, @Param("account") String account, @Param("sId") int sId, @Param("userId") int userId);

    List<SKUEntity> getSKUList(@Param("sId") int sId, @Param("account") String account, @Param("location") String locationCode, @Param("barcode") String barcode);

    //添加数据
    void addProduct(@Param("item") SKUAddRequestEntity dto, @Param("account") String account, @Param("sId") int sId, @Param("userId") int userId);

    @Select("SELECT p.p_id AS pId, p.name AS goodsName, p.unit1Name AS unit, p.standard, p.serial_number AS number ,p.Factory AS manufacturers FROM ${account}.dbo.pda_Products p WHERE p.barcode=#{goodsCode}")
    GoodsDetailEntity getGoodsDetailByCode(@Param("goodsCode") String goodsCode, @Param("account") String account);
}
