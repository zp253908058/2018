package com.teeny.wms.web.controller;

import com.teeny.wms.app.model.KeyValueEntity;
import com.teeny.wms.app.model.ResponseEntity;
import com.teeny.wms.web.model.EmptyEntity;
import com.teeny.wms.web.model.response.ReceivingEntity;
import com.teeny.wms.web.model.response.ReceivingLotEntity;
import com.teeny.wms.web.model.request.ReceivingRequestEntity;
import com.teeny.wms.web.service.ReceivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class description: 收货
 *
 * @author zp
 * @version 1.0
 * @see ReceivingController
 * @since 2017/11/1
 */
@RestController
@RequestMapping("/api/receiving")
public class ReceivingController {

    private ReceivingService mReceivingService;

    @Autowired
    public void setReceivingService(ReceivingService receivingService) {
        mReceivingService = receivingService;
    }

    @GetMapping("/unit")
    public ResponseEntity<List<KeyValueEntity>> getUnitList(@RequestHeader("account") String account, @RequestHeader(value = "sId", required = false, defaultValue = "0") int id) {
        List<KeyValueEntity> list = mReceivingService.getUnitList(account, id);
        return new ResponseEntity<>(list);
    }

    /**
     * 获取详情
     *
     * @param id 单位id
     * @return List<ReceivingAcceptanceEntity>
     */
    @GetMapping("/detail/{id}")
    public ResponseEntity<List<ReceivingEntity>> getDetailByUnitId(@RequestHeader("account") String account, @PathVariable("id") int id) {
        List<ReceivingEntity> list = mReceivingService.getDetailByUnitId(account, id);
        return new ResponseEntity<>(list);
    }

    /**
     * 通过单号获取详情
     *
     * @param orderNo 单号
     * @return List<ReceivingAcceptanceEntity>
     */
    @GetMapping("/detail")
    public ResponseEntity<List<ReceivingEntity>> getDetailByOrderNo(@RequestHeader("account") String account, @RequestParam("orderNo") String orderNo) {
        List<ReceivingEntity> list = mReceivingService.getDetailByOrderNo(account, orderNo);
        return new ResponseEntity<>(list);
    }

    @GetMapping("/lots/{id}")
    public ResponseEntity<List<ReceivingLotEntity>> getLotList(@RequestHeader("account") String account, @PathVariable("id") int id) {
        List<ReceivingLotEntity> list = mReceivingService.getLotList(account, id);
        return new ResponseEntity<>(list);
    }

    @PostMapping("/complete")
    public ResponseEntity<EmptyEntity> complete(@RequestHeader("account") String account, @RequestBody ReceivingRequestEntity entity) {
        mReceivingService.complete(account, entity, 1);
        return new ResponseEntity<>();

        //TODO    当前用户还没有实现
    }
}
