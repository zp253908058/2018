package com.teeny.wms.web.controller;

import com.teeny.wms.app.annotation.User;
import com.teeny.wms.web.model.ResponseEntity;
import com.teeny.wms.web.model.EmptyEntity;
import com.teeny.wms.web.model.UserEntity;
import com.teeny.wms.web.model.request.SKUAddRequestEntity;
import com.teeny.wms.web.model.response.GoodsDetailEntity;
import com.teeny.wms.web.model.response.SKUEntity;
import com.teeny.wms.web.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class description: 单品盘点
 *
 * @author zp
 * @version 1.0
 * @see SKUController
 * @since 2017/11/8
 */
@RestController
@RequestMapping("/api/productsInventory")
public class SKUController {

    private InventoryService mInventoryService;

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        mInventoryService = inventoryService;
    }

    /**
     * 获取商品列表
     *
     * @return List<SKUEntity>
     */
    @GetMapping("/getList")
    ResponseEntity<List<SKUEntity>> getList(@RequestHeader("sId") int sId, @RequestHeader("account") String account, @RequestParam("location") String locationCode, @RequestParam("barcode") String barcode) {
        List<SKUEntity> result = mInventoryService.getSKUList(sId, account, locationCode, barcode);
        return new ResponseEntity<>(result);
    }

    /**
     * 保存
     *
     * @return EmptyEntity
     */
    @PostMapping("/update")
    public ResponseEntity<EmptyEntity> save(@RequestBody SKUAddRequestEntity entity, @RequestHeader("account") String account, @RequestHeader("sId") int sId, @User UserEntity user) {
        mInventoryService.addSku(entity, account, sId, user.getId());
        return new ResponseEntity<>();
    }

    /**
     * 根据商品码获取商品详情
     *
     * @return EmptyEntity
     */
    @GetMapping("/detail")
    public ResponseEntity<GoodsDetailEntity> getGoodsDetail(@RequestParam("goodsCode") String goodsCode, @RequestHeader("account") String account) {
        GoodsDetailEntity entity = mInventoryService.getGoodsDetailByCode(goodsCode, account);
        return new ResponseEntity<>(entity);
    }

    /**
     * 添加单品
     *
     * @return EmptyEntity
     */
    @PutMapping("/add")
    public ResponseEntity<EmptyEntity> add(@RequestBody SKUAddRequestEntity entity, @RequestHeader("account") String account, @RequestHeader("sId") int sId, @User UserEntity user) {
        mInventoryService.addSku(entity, account, sId, user.getId());
        return new ResponseEntity<>();
    }
}
