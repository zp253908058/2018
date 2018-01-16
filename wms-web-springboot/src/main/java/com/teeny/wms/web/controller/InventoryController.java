package com.teeny.wms.web.controller;

import com.teeny.wms.app.exception.InnerException;
import com.teeny.wms.app.model.KeyValueEntity;
import com.teeny.wms.app.model.ResponseEntity;
import com.teeny.wms.web.model.EmptyEntity;
import com.teeny.wms.web.model.request.InventoryAddRequestEntity;
import com.teeny.wms.web.model.request.InventoryRequestEntity;
import com.teeny.wms.web.model.request.LotEntity;
import com.teeny.wms.web.model.response.InventoryGoodsEntity;
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
@RequestMapping(value = {"/api/warehouseFirst", "/api/shopFirst", "/api/secondInventory"})
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
    public ResponseEntity<Integer> single(@RequestParam("id") int originalId, @RequestHeader("account") String account) {
        return new ResponseEntity<>(mInventoryService.single(originalId, account, 1));

        //TODO user
    }

    //确定
    @PostMapping(value = "/complete")
    public ResponseEntity<EmptyEntity> complete(@RequestBody List<Integer> ids, @RequestHeader("account") String account) {
        mInventoryService.complete(ids, account, 1);
        return new ResponseEntity<>();

        //TODO user
    }

    //盘点编辑
    @PostMapping(value = "/edit")
    public ResponseEntity<EmptyEntity> edit(@RequestBody InventoryRequestEntity entity, @RequestHeader("account") String account) {
        mInventoryService.edit(entity, account, 1);
        return new ResponseEntity<>();

        //TODO user
    }

    //获取批次
    @GetMapping(value = "/getLotList")
    public ResponseEntity<List<LotEntity>> getLotList(@RequestParam("originalId") int originalId, @RequestHeader("account") String account) {
        List<LotEntity> result = mInventoryService.getLotList(originalId, account);
        return new ResponseEntity<>(result);
    }

    //获取盘点类型
    @GetMapping(value = "/pdType")
    public ResponseEntity<List<KeyValueEntity>> getPdType(HttpServletRequest request, @RequestHeader("account") String account, @RequestHeader("sId") int sId) {
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
        List<KeyValueEntity> result = mInventoryService.getPdType(type, account, sId);
        return new ResponseEntity<>(result);
    }

    //新增
    @PutMapping(value = "/addInventory")
    public ResponseEntity<EmptyEntity> add(HttpServletRequest request, @RequestBody InventoryAddRequestEntity entity, @RequestHeader("account") String account, @RequestHeader("sId") int sId) {
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
        mInventoryService.addInventory(type, entity, account, sId, 1);
        return new ResponseEntity<>();

        //TODO user
    }
}
