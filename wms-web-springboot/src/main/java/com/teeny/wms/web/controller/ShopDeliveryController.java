package com.teeny.wms.web.controller;

import com.teeny.wms.app.annotation.User;
import com.teeny.wms.web.model.EmptyEntity;
import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.ResponseEntity;
import com.teeny.wms.web.model.UserEntity;
import com.teeny.wms.web.model.request.ShopDeliveryRequestEntity;
import com.teeny.wms.web.model.response.ShopDeliveryGoodsEntity;
import com.teeny.wms.web.service.ShopDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class description:
 * 门店收货
 *
 * @author zp
 * @version 1.0
 * @see ShopDeliveryController
 * @since 2018/1/18
 */
@RestController
@RequestMapping("/api/shop/delivery/")
public class ShopDeliveryController {

    private ShopDeliveryService mService;

    @Autowired
    public void setService(ShopDeliveryService service) {
        mService = service;
    }

    @GetMapping("orderList")
    public ResponseEntity<List<KeyValueEntity>> getOrderList(@RequestHeader("account") String account) {
        return new ResponseEntity<>(mService.getOrderList(account));
    }

    @GetMapping("goodsList/{id}")
    public ResponseEntity<List<ShopDeliveryGoodsEntity>> getDeliveryGoodsList(@RequestHeader("account") String account, @PathVariable("id") int id) {
        return new ResponseEntity<>(mService.getDeliveryGoodsList(account, id));
    }

    @PostMapping("complete")
    public ResponseEntity<EmptyEntity> complete(@RequestHeader("account") String account, @RequestBody ShopDeliveryRequestEntity requestEntity, @User UserEntity entity) {
        mService.complete(account, requestEntity.getIds(), requestEntity.getId(), entity.getId());
        return new ResponseEntity<>();
    }

    @PostMapping("single/{id}")
    public ResponseEntity<EmptyEntity> single(@RequestHeader("account") String account, @PathVariable("id") int id, @RequestParam("billId") int billId, @User UserEntity entity) {
        mService.single(account, id, billId, entity.getId());
        return new ResponseEntity<>();
    }

    @PostMapping("single")
    public ResponseEntity<EmptyEntity> single(@RequestHeader("account") String account, @RequestParam("id") int id, @RequestParam("amount") float amount, @RequestParam("remark") String remark, @RequestParam("billId") int billId, @User UserEntity entity) {
        mService.single(account, id, amount, remark, billId, entity.getId());
        return new ResponseEntity<>();
    }
}
