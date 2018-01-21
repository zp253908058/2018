package com.teeny.wms.web.controller;

import com.teeny.wms.web.model.ResponseEntity;
import com.teeny.wms.web.model.request.BarcodeAddRequestEntity;
import com.teeny.wms.web.model.response.BarcodeGoodsEntity;
import com.teeny.wms.web.service.BarcodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BarcodeController
 * @since 2018/1/16
 */
@RestController
@RequestMapping("/api/barcode/")
public class BarcodeController {

    private BarcodeService mBarcodeService;

    @Autowired
    public void setBarcodeService(BarcodeService barcodeService) {
        mBarcodeService = barcodeService;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity<List<BarcodeGoodsEntity>> getList(@RequestHeader("account") String account, @RequestParam("goods") String goods, @RequestParam("location") String location) {
        return new ResponseEntity<>(mBarcodeService.getList(account, location, goods));
    }

    @RequestMapping(value = "goods", method = RequestMethod.GET)
    public ResponseEntity<BarcodeGoodsEntity> getGoodsById(@RequestHeader("account") String account, @RequestParam("id") int id) {
        return new ResponseEntity<>(mBarcodeService.getGoodsById(account, id));
    }

    @RequestMapping(value = "goodsList", method = RequestMethod.GET)
    public ResponseEntity<List<BarcodeGoodsEntity>> getGoodsList(@RequestHeader("account") String account, @RequestParam("goods") String goods) {
        return new ResponseEntity<>(mBarcodeService.getGoodsList(account, goods));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity add(@RequestHeader("account") String account, @RequestBody BarcodeAddRequestEntity entity) {
        mBarcodeService.add(account, entity);
        return new ResponseEntity();
    }
}
