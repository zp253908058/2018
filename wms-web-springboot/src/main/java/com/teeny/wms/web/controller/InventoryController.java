package com.teeny.wms.web.controller;

import com.teeny.wms.app.annotation.User;
import com.teeny.wms.app.exception.InnerException;
import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.ResponseEntity;
import com.teeny.wms.web.model.EmptyEntity;
import com.teeny.wms.web.model.UserEntity;
import com.teeny.wms.web.model.request.InventoryAddRequestEntity;
import com.teeny.wms.web.model.request.InventoryRequestEntity;
import com.teeny.wms.web.model.request.LotEntity;
import com.teeny.wms.web.model.response.InventoryCountEntity;
import com.teeny.wms.web.model.response.InventoryGoodsEntity;
import com.teeny.wms.web.model.response.InventoryGoodsWrapperEntity;
import com.teeny.wms.web.model.response.InventoryInitializeEntity;
import com.teeny.wms.web.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see InventoryController
 * @since 2017/11/8
 */

@RestController
@RequestMapping(value = {"/api/warehouseFirst", "/api/shopFirst", "/api/secondInventory", "/api/inventory/"})
public class InventoryController {

    private InventoryService mInventoryService;

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        mInventoryService = inventoryService;
    }

    @GetMapping(value = "/getList")
    public ResponseEntity<List<InventoryGoodsEntity>> getInventoryList(HttpServletRequest request, @RequestParam("id") int id, @RequestHeader("account") String account) {
        String url = request.getRequestURL().toString();
        boolean merge = url.contains("shopFirst");
        return new ResponseEntity<>(mInventoryService.getInventoryList(id, merge, account));
    }

    //单个完成
    @PostMapping(value = "/single")
    public ResponseEntity<Integer> single(@RequestParam("id") int originalId, @RequestHeader("account") String account, @User UserEntity user) {
        return new ResponseEntity<>(mInventoryService.single(originalId, account, user.getId()));
    }

//    //确定
//    @PostMapping(value = "/complete")
//    public ResponseEntity<EmptyEntity> complete(@RequestBody List<Integer> ids, @RequestHeader("account") String account, @User UserEntity user) {
//        mInventoryService.complete(ids, account, user.getId());
//        return new ResponseEntity<>();
//    }

    //盘点编辑
    @PostMapping(value = "/singleComplete")
    public ResponseEntity<EmptyEntity> singleComplete(@RequestBody InventoryRequestEntity entity, @RequestHeader("account") String account, @User UserEntity user) {
        mInventoryService.singleComplete(entity, account, user.getId());
        return new ResponseEntity<>();
    }

    //获取批次
    @GetMapping(value = "/getLotList")
    public ResponseEntity<List<LotEntity>> getLotList(@RequestParam("originalId") int originalId, @RequestHeader("account") String account) {
        List<LotEntity> result = mInventoryService.getLotList(originalId, account);
        return new ResponseEntity<>(result);
    }

    //新增
    @PutMapping(value = "/add")
    public ResponseEntity<EmptyEntity> add(HttpServletRequest request, @RequestBody InventoryAddRequestEntity entity, @RequestHeader("account") String account, @RequestHeader("sId") int sId, @User UserEntity user) {
        int type = -1;
        String url = request.getRequestURL().toString();
        if (url.contains("shopFirst")) {
            type = 1;
        }
        if (url.contains("secondInventory")) {
            type = 2;
        }
        if (url.contains("warehouseFirst")) {
            type = 4;
        }
        if (type == -1) {
            throw new InnerException("程序访问错误.");
        }
        mInventoryService.addInventory(type, entity, account, sId, user.getId());
        return new ResponseEntity<>();
    }

    //20180116

    /**
     * 获取盘点类型
     *
     * @param account 账户
     * @param sId     仓库id
     * @param type    盘点类型： 1门店盘点 2复盘 4 仓库盘点
     * @return 盘点类型列表
     */
    @GetMapping(value = "pdType")
    public ResponseEntity<List<KeyValueEntity>> getPdType(@RequestHeader("account") String account, @RequestHeader("sId") int sId, @RequestParam("type") int type) {
        return new ResponseEntity<>(mInventoryService.getPdType(account, type, sId));
    }

    /**
     * 初始化盘点数据
     *
     * @param account 账户
     * @param id      盘点单id
     * @param type    盘点类型： 1门店盘点 2复盘 4 仓库盘点
     * @return 初始化数据列表
     */
    @GetMapping(value = "initialize")
    public ResponseEntity<InventoryInitializeEntity> initialize(@RequestHeader("account") String account, @RequestParam("id") int id, @RequestParam("type") int type) {
        return new ResponseEntity<>(mInventoryService.initialize(account, id, type == 1));
    }

    /**
     * 统计数量
     *
     * @param account      账户
     * @param id           盘点单id
     * @param repositoryId 库区id
     * @param areaId       区域id
     * @param type         盘点类型： 1门店盘点 2复盘 4 仓库盘点
     * @return 统计数量
     */
    @GetMapping(value = "count")
    public ResponseEntity<InventoryCountEntity> count(@RequestHeader("account") String account, @RequestParam("id") int id, @RequestParam("repositoryId") int repositoryId, @RequestParam("areaId") int areaId, @RequestParam("type") int type) {
        return new ResponseEntity<>(mInventoryService.count(account, id, repositoryId, areaId, type == 1));
    }

    /**
     * 获取商品清单列表
     *
     * @param account      账户
     * @param id           盘点单id
     * @param repositoryId 库区id
     * @param areaId       区域id
     * @param locationCode 货位码
     * @param type         盘点类型： 1门店盘点 2复盘 4 仓库盘点
     * @return 商品清单列表
     */
    @GetMapping(value = "home_data")
    public ResponseEntity<InventoryGoodsWrapperEntity> getHomeData(@RequestHeader("account") String account, @RequestParam("id") int id, @RequestParam("repositoryId") int repositoryId, @RequestParam("areaId") int areaId, @RequestParam("locationCode") String locationCode, @RequestParam("type") int type) {
        return new ResponseEntity<>(mInventoryService.getHomeData(account, id, repositoryId, areaId, locationCode, type == 1));
    }

    //确定
    @PostMapping(value = "complete")
    public ResponseEntity completeByBillId(@RequestHeader("account") String account, @RequestBody List<Integer> ids, @User UserEntity user) {
        mInventoryService.complete(account, ids, user.getId());
        return new ResponseEntity();
    }

    @PostMapping(value = "forceComplete")
    public ResponseEntity forceComplete(@RequestHeader("account") String account, @RequestParam("id") int id, @User UserEntity user) {
        mInventoryService.forceComplete(account, id, user.getId());
        return new ResponseEntity();
    }
}
