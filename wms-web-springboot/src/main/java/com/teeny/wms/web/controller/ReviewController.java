package com.teeny.wms.web.controller;

import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.ResponseEntity;
import com.teeny.wms.web.model.EmptyEntity;
import com.teeny.wms.web.model.request.ExWarehouseReviewRequestEntity;
import com.teeny.wms.web.model.response.ExWarehouseReviewEntity;
import com.teeny.wms.web.model.response.RecipientEntity;
import com.teeny.wms.web.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Class description: 复核
 *
 * @author zp
 * @version 1.0
 * @see ReviewController
 * @since 2017/11/7
 */
@RestController
@RequestMapping("/api/recheck")
public class ReviewController {

    private ReviewService mReviewService;

    @Autowired
    public void setReviewService(ReviewService reviewService) {
        mReviewService = reviewService;
    }

    /**
     * 出库复核
     *
     * @return 复核单详情
     */
    @GetMapping("/exWarehouseReview/{billNo}")
    public ResponseEntity<ExWarehouseReviewEntity> detail(@RequestHeader("account") String account, @PathVariable("billNo") String billNo) {
        return new ResponseEntity<>(mReviewService.getWarehouseReview(account, billNo, 1));

        //TODO  user
    }

    /**
     * 获取接收人
     *
     * @return List<KeyValueEntity>
     */
    @GetMapping("/recipients")
    public ResponseEntity<List<RecipientEntity>> getRecipient(@RequestHeader("account") String account, @RequestHeader("sId") int sId) {
        return new ResponseEntity<>(mReviewService.getRecipients(account, sId));
    }


    /**
     * 获取单据
     *
     * @return List<KeyValueEntity>
     */
    @GetMapping("/bills")
    public ResponseEntity<List<KeyValueEntity>> getBillList(@RequestHeader("account") String account, @RequestHeader("sId") int sId) {
        return new ResponseEntity<>(mReviewService.getBillList(sId, account));
    }

    /**
     * 获取补货数量
     *
     * @return int
     */
    @GetMapping("/replenishment")
    public ResponseEntity<Integer> getReplenishmentCount(@RequestHeader("account") String account, @RequestHeader("sId") int sId) {
        return new ResponseEntity<>(mReviewService.getReplenishmentCount(account, sId));
    }

    /**
     * 强制完成
     *
     * @return EmptyEntity
     */
    @PostMapping("/complete")
    public ResponseEntity<EmptyEntity> complete(@RequestBody ExWarehouseReviewRequestEntity entity, @RequestHeader("account") String account) {
        mReviewService.complete(entity, account);
        return new ResponseEntity<>();
    }
}
