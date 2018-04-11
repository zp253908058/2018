package com.teeny.wms.web.repository;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.request.InventoryAddRequestEntity;
import com.teeny.wms.web.model.request.LotEntity;
import com.teeny.wms.web.model.request.SKUAddRequestEntity;
import com.teeny.wms.web.model.response.*;
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
    @Update("UPDATE ${account}.dbo.pda_pdBill SET pdastates=#{status,jdbcType=INTEGER},pdaReTime=getdate() WHERE billid = #{id} AND pdastates = 0")
    void updateInventoryStatus(@Param("account") String account, @Param("id") int id, @Param("status") int status);

    void single(@Param("originalId") int originalId, @Param("account") String account, @Param("userId") int userId);

    void complete(@Param("account") String account, @Param("list") List<Integer> ids, @Param("userId") int userId);

    Integer getBillId(@Param("account") String account, @Param("originalId") int originalId);

    /**
     * @param account 账套
     * @param id      bill_id
     */
    void updateState(@Param("account") String account, @Param("id") int id);

    @Select("SELECT d.smb_id FROM ${account}.dbo.pda_pdBill_D d WHERE d.original_id=#{id}")
    List<Integer> getIdsByOriginalId(@Param("id") int id, @Param("account") String account);

    //获取批次
    @Select("SELECT CONVERT(varchar(100), d.Validdate, 23) AS validateDate, d.Batchno AS lotNo, d.EligibleQty AS count FROM ${account}.dbo.pda_pdBill_D d WHERE d.original_id = #{originalId}")
    List<LotEntity> getLotList(@Param("originalId") int originalId, @Param("account") String account);

    List<KeyValueEntity> getPdType(@Param("account") String account, @Param("type") int type, @Param("sId") int sId);

    void singleComplete(@Param("id") int id, @Param("list") List<LotEntity> param, @Param("account") String account, @Param("userId") int userId);

    void deleteByIds(@Param("list") List<Integer> ids, @Param("account") String account);

    void updateBillStatus(@Param("id") int id, @Param("account") String account);

    //添加数据
    void addInventory(@Param("item") InventoryAddRequestEntity entity, @Param("type") int type, @Param("account") String account, @Param("sId") int sId, @Param("userId") int userId);

    List<SKUEntity> getSKUList(@Param("sId") int sId, @Param("account") String account, @Param("location") String locationCode, @Param("barcode") String barcode);

    //添加数据
    void addProduct(@Param("item") SKUAddRequestEntity dto, @Param("account") String account, @Param("sId") int sId, @Param("userId") int userId);

    @Select("SELECT p.p_id AS pId, p.name AS goodsName, p.unit1Name AS unit, p.standard, p.serial_number AS number ,p.Factory AS manufacturers FROM ${account}.dbo.pda_Products p WHERE p.barcode=#{goodsCode}")
    List<GoodsDetailEntity> getGoodsDetailByCode(@Param("goodsCode") String goodsCode, @Param("account") String account);

    /**
     * 获取库区和区域的级联
     *
     * @param account 账户
     * @param id      盘点单id
     * @return
     */
    List<RepositoryEntity> getCollection(@Param("account") String account, @Param("id") int id);

    /**
     * 获取统计数量
     *
     * @param account      账户
     * @param pdId         盘点单id
     * @param repositoryId 库区id
     * @param areaId       区域id
     * @param barcode      货位码
     * @param isMerge      是否合并数据
     * @return
     */
    InventoryCountEntity count(@Param("account") String account, @Param("id") int pdId, @Param("repositoryId") int repositoryId, @Param("areaId") int areaId, @Param("barcode") String barcode, @Param("isMerge") boolean isMerge);

    List<InventoryGoodsEntity> getList(@Param("account") String account, @Param("id") int pdId, @Param("repositoryId") int repositoryId, @Param("areaId") int areaId, @Param("locationCode") String locationCode, @Param("isMerge") boolean isMerge);
}
