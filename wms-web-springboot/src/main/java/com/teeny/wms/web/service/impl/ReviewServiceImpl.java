package com.teeny.wms.web.service.impl;

import com.teeny.wms.app.exception.InnerException;
import com.teeny.wms.web.model.KeyValueEntity;
import com.teeny.wms.web.model.dto.ReviewBillEntity;
import com.teeny.wms.web.model.request.ExWarehouseReviewRequestEntity;
import com.teeny.wms.web.model.response.ExWarehouseReviewEntity;
import com.teeny.wms.web.model.response.RecipientEntity;
import com.teeny.wms.web.repository.CommonMapper;
import com.teeny.wms.web.repository.ReviewMapper;
import com.teeny.wms.web.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ReviewServiceImpl
 * @since 2017/11/7
 */
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private ReviewMapper mReviewMapper;
    private CommonMapper mCommonMapper;

    @Autowired
    public void setReviewMapper(ReviewMapper reviewMapper) {
        mReviewMapper = reviewMapper;
    }

    @Autowired
    public void setCommonMapper(CommonMapper commonMapper) {
        mCommonMapper = commonMapper;
    }

    @Override
    public ExWarehouseReviewEntity getWarehouseReview(String account, String billNo, int userId) {
        ReviewBillEntity bill = mReviewMapper.getBillByCode(billNo, account);
        if (bill == null) {
            throw new InnerException(billNo + "  不存在.");
        }
        if (bill.getDealStates() == 1) {
            throw new InnerException(billNo + "已扫描!");
        }
        mReviewMapper.updateStatus(bill.getSmbId(), account, userId);

        mReviewMapper.updateState(bill.getBillId(), account);

        mReviewMapper.updateBillStatus(bill.getBillId(), account);

        return mReviewMapper.getDetail(billNo, account);
    }

    @Override
    public List<RecipientEntity> getRecipients(String account, int sId) {
        return mReviewMapper.getRecipients(account, sId);
    }

    @Override
    public List<KeyValueEntity> getBillList(int sId, String account) {
        return mReviewMapper.getBillList(sId, account);
    }

    @Override
    public String getReplenishmentCount(String account, int sId) {
        return mReviewMapper.getReplenishmentCount(account, sId);
    }

    @Override
    public void complete(ExWarehouseReviewRequestEntity entity, String account) {
        mReviewMapper.complete(entity.getBillNo(), entity.getRecipientId(), entity.getRemark(), account);
    }
}
