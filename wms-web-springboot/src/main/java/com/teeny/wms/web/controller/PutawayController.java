package com.teeny.wms.web.controller;

import com.teeny.wms.app.annotation.User;
import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.ResponseEntity;
import com.teeny.wms.web.model.EmptyEntity;
import com.teeny.wms.web.model.UserEntity;
import com.teeny.wms.web.model.request.AllotListRequestEntity;
import com.teeny.wms.web.model.response.AllocationEntity;
import com.teeny.wms.web.model.response.PutawayEntity;
import com.teeny.wms.web.service.PutawayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see PutawayController
 * @since 2017/11/8
 */

@RestController
@RequestMapping("/api/shelve")
public class PutawayController {

    private PutawayService mPutawayService;

    @Autowired
    public void setPutawayService(PutawayService putawayService) {
        mPutawayService = putawayService;
    }

    /**
     * 获取库区
     *
     * @return List<KeyValueEntity>
     */
    @GetMapping("/saList")
    public ResponseEntity<List<KeyValueEntity>> getSaList(@RequestHeader("account") String account, @RequestHeader("sId") int sId) {
        List<KeyValueEntity> result = mPutawayService.getSaList(sId, account);
        return new ResponseEntity<>(result);
    }

    /**
     * 获取上架单号
     *
     * @param saId 库区id
     * @return List<KeyValueEntity>
     */
    @GetMapping("/billList/{saId}")
    public ResponseEntity<List<KeyValueEntity>> getOrderNoList(@PathVariable("saId") int saId, @RequestHeader("account") String account, @RequestHeader("sId") int sId) {
        List<KeyValueEntity> result = mPutawayService.getBillList(sId, saId, account);
        return new ResponseEntity<>(result);
    }

    /**
     * 获取商品详情列表
     *
     * @return List<Entity>
     */
    @GetMapping("/goodsDetailList/{orderNo}")
    public ResponseEntity<List<PutawayEntity>> getGoodsDetailList(@PathVariable("orderNo") String orderNo, @RequestHeader("account") String account, @RequestHeader(value = "sId", required = false, defaultValue = "0") int sId) {
        List<PutawayEntity> result = mPutawayService.getGoodsDetailList(orderNo, account, sId);
        return new ResponseEntity<>(result);
    }

    /**
     * 全部上架
     *
     * @return List<KeyValueEntity>
     */
    @PostMapping("/all")
    public ResponseEntity<EmptyEntity> all(@RequestBody List<Integer> ids, @RequestHeader("account") String account, @User UserEntity user) {
        mPutawayService.all(ids, account, user.getId());
        return new ResponseEntity<>();
    }

    /**
     * 单个上架
     *
     * @return List<KeyValueEntity>
     */
    @PostMapping("/single")
    public ResponseEntity<EmptyEntity> single(@RequestParam("id") int originalId, @RequestHeader("account") String account, @User UserEntity user) {
        mPutawayService.single(originalId, account, user.getId());
        return new ResponseEntity<>();
    }

    /**
     * 修改
     *
     * @return List<KeyValueEntity>
     */
    @PostMapping("/update")
    public ResponseEntity<EmptyEntity> update(@RequestBody AllotListRequestEntity entity, @RequestHeader("account") String account, @User UserEntity user) {
        mPutawayService.update(entity, account, user.getId());
        return new ResponseEntity<>();
    }

    /**
     * 获取货位
     *
     * @return List<AllocationEntity>
     */
    @GetMapping("/locationList/{id}")
    public ResponseEntity<List<AllocationEntity>> getLocationList(@PathVariable("id") int id, @RequestHeader("account") String account) {
        List<AllocationEntity> result = mPutawayService.getLocationList(id, account);
        return new ResponseEntity<>(result);
    }
}
