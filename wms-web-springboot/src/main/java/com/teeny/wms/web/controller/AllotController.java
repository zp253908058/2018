package com.teeny.wms.web.controller;

import com.teeny.wms.app.annotation.User;
import com.teeny.wms.web.model.EmptyEntity;
import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.ResponseEntity;
import com.teeny.wms.web.model.UserEntity;
import com.teeny.wms.web.model.request.AllotListCompleteRequestEntity;
import com.teeny.wms.web.model.request.AllotListRequestEntity;
import com.teeny.wms.web.model.request.AllotLocationRequestEntity;
import com.teeny.wms.web.model.response.AllocationEntity;
import com.teeny.wms.web.model.response.AllotEntity;
import com.teeny.wms.web.model.response.AllotGoodsEntity;
import com.teeny.wms.web.model.response.AllotLocationEntity;
import com.teeny.wms.web.service.AllotService;
import com.teeny.wms.web.service.CommonService;
import org.jboss.logging.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class description: 调拨单
 *
 * @author zp
 * @version 1.0
 * @see AllotController
 * @since 2017/11/7
 */
@RestController
@RequestMapping("/api/allot/")
public class AllotController {

    private AllotService mAllotService;
    private CommonService mCommonService;

    @Autowired
    public void setAllotService(AllotService allotService) {
        mAllotService = allotService;
    }

    @Autowired
    public void setCommonService(CommonService commonService) {
        mCommonService = commonService;
    }

    /**
     * 获取商品列表
     *
     * @param billNo    单据编号
     * @param goodsCode 商品编号
     * @param sId       仓库id
     * @param saId      库区id
     * @return List<AllotListEntity>
     */
    @GetMapping("list")
    public ResponseEntity<List<AllotEntity>> getList(@RequestParam(value = "billCode") String billNo, @RequestParam("goodsCode") String goodsCode, @RequestParam("sId") int sId, @RequestParam("saId") int saId, @RequestHeader("account") String account) {
        return new ResponseEntity<>(mAllotService.getAllotList(billNo, goodsCode, sId, saId, account));
    }


    /**
     * 获取仓库
     *
     * @return List<KeyValueEntity>
     */
    @GetMapping("warehouseList")
    public ResponseEntity<List<KeyValueEntity>> getWarehouseList(@RequestHeader("account") String account) {
        return new ResponseEntity<>(mCommonService.getWarehouseList(account));
    }

    /**
     * 获取区域
     *
     * @return List<KeyValueEntity>
     */
    @GetMapping("saList/{id}")
    public ResponseEntity<List<KeyValueEntity>> getAreaList(@PathVariable("id") int warehouseId, @RequestHeader("account") String account) {
        return new ResponseEntity<>(mCommonService.getAreaList(warehouseId, account));
    }

    /**
     * 全部完成
     *
     * @return null
     */
    @PostMapping("updateAll")
    public ResponseEntity<EmptyEntity> complete(@RequestBody List<AllotListCompleteRequestEntity> params, @RequestHeader("account") String account, @User UserEntity entity) {
        mAllotService.updateAll(params, account, entity.getId());
        return new ResponseEntity<>();
    }

    /**
     * 单个完成
     *
     * @return null
     */
    @PostMapping("updateOne")
    public ResponseEntity<EmptyEntity> single(@RequestParam("id") int id, @RequestParam("classType") int classType, @RequestHeader("account") String account, @User UserEntity entity) {
        mAllotService.updateOne(id,classType, account, entity.getId());
        return new ResponseEntity<>();
    }

    /**
     * 单个完成
     *
     * @return null
     */
    @PostMapping("update")
    public ResponseEntity<EmptyEntity> update(@RequestBody AllotListRequestEntity entity, @RequestHeader("account") String account, @User UserEntity userEntity) {
        mAllotService.update(entity, account, userEntity.getId());
        return new ResponseEntity<>();
    }

    /**
     * 获取货位
     *
     * @return List<AllocationEntity>
     */
    @GetMapping("getLocations/{id}")
    public ResponseEntity<List<AllocationEntity>> getLocations(@PathVariable("id") int id, @RequestHeader("account") String account) {
        return new ResponseEntity<>(mAllotService.getLocationsById(id, account));
    }

    /**
     * 获取单据
     *
     * @return List<KeyValueEntity>
     */
    @GetMapping("billList")
    public ResponseEntity<List<KeyValueEntity>> getDocumentList(@RequestParam("sId") int sId, @RequestParam("saId") int saId, @RequestHeader("account") String account) {
        return new ResponseEntity<>(mAllotService.getOrderList(saId, sId, account));
    }

    /**
     * 获取商品
     *
     * @return List<KeyValueEntity>
     */
    @GetMapping("goodsCode")
    public ResponseEntity<List<KeyValueEntity>> getGoodsList(@RequestHeader("account") String account, @RequestParam("sId") int sId, @RequestParam("saId") int saId) {
        return new ResponseEntity<>(mAllotService.getGoodsCode(account, sId, saId));
    }


    @GetMapping(value = "goodsList")
    public ResponseEntity<List<AllotGoodsEntity>> getAllotGoodsList(@RequestHeader("account") String account, @RequestParam("location") String location, @RequestParam("goods") String goods, @RequestParam(value = "warehouseId", required = false, defaultValue = "0") int warehouseId, @RequestParam(value = "repositoryId", required = false, defaultValue = "0") int repositoryId, @RequestParam(value = "areaId", required = false, defaultValue = "0") int areaId) {
        return new ResponseEntity<>(mAllotService.getAllotGoodsList(account, location, goods, warehouseId, repositoryId, areaId));
    }

    @PostMapping(value = "add/{id}")
    public ResponseEntity<AllotGoodsEntity> add(@RequestHeader("account") String account, @PathVariable("id") int id, @User UserEntity userEntity) {
        return new ResponseEntity<>(mAllotService.add(account, id, userEntity.getId(), userEntity.getSerialNumber()));
    }

    @GetMapping(value = "getTempleGoodsList")
    public ResponseEntity<List<AllotGoodsEntity>> getTempleGoodsList(@RequestHeader("account") String account, @User UserEntity userEntity) {
        return new ResponseEntity<>(mAllotService.getTempleGoodsList(account, userEntity.getId()));
    }

    @PostMapping("complete")
    public ResponseEntity finish(@RequestHeader("account") String account, @RequestBody AllotLocationRequestEntity entity) {
        mAllotService.completeAllot(account, entity);
        return new ResponseEntity();
    }

    @GetMapping("locationList/{id}")
    public ResponseEntity<List<AllotLocationEntity>> getLocationList(@RequestHeader("account") String account, @PathVariable("id") int id) {
        return new ResponseEntity<>(mAllotService.getLocationList(account, id));
    }

    @DeleteMapping("remove")
    public ResponseEntity<EmptyEntity> remove(@RequestHeader("account") String account, @RequestParam("detailId") int detailId, @RequestParam("locationRowId") int locationRowId) {
        mAllotService.remove(account, detailId, locationRowId);
        return new ResponseEntity<>();
    }

    @PostMapping("finish")
    public ResponseEntity<EmptyEntity> finishBill(@RequestHeader("account") String account, @User UserEntity entity) {
        mAllotService.finishBill(account, entity.getId());
        return new ResponseEntity<>();
    }
}
