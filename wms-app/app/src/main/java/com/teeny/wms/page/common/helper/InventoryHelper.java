package com.teeny.wms.page.common.helper;

import android.graphics.Rect;
import android.support.v4.util.SparseArrayCompat;

import com.teeny.wms.model.InventoryGoodsEntity;
import com.teeny.wms.model.WarehouseGoodsEntity;
import com.teeny.wms.util.ObjectUtils;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.log.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see InventoryHelper
 * @since 2017/12/27
 */

public abstract class InventoryHelper {

    private int id;

    private int mFinishedNumber;
    private int mUnfinishedNumber;
    private List<InventoryGoodsEntity> mDataList;
    private List<InventoryGoodsEntity> mOriginalData;
    private SparseArrayCompat<List<InventoryGoodsEntity>> mDataHolder;

    private String mGoodsCode;

    private EventBus mEventBus;
    private boolean mIsForceCompleteAvailable;

    public InventoryHelper() {
        mEventBus = EventBus.getDefault();
        mDataHolder = new SparseArrayCompat<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFinishedNumber() {
        return mFinishedNumber;
    }

    public void setNumber(int finishedNumber, int unfinishedNumber) {
        this.mFinishedNumber = finishedNumber;
        this.mUnfinishedNumber = unfinishedNumber;
        mEventBus.post(new NumberObserver(this));
    }

    public int getUnfinishedNumber() {
        return mUnfinishedNumber;
    }

    public List<InventoryGoodsEntity> getDataList() {
        return mDataList;
    }

    public void setDataList(List<InventoryGoodsEntity> dataList) {
        this.mDataList = dataList;
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataHolder.clear();
        this.mOriginalData = new ArrayList<>(mDataList);
        notifyChanged();
    }

    public String getGoodsCode() {
        return mGoodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        if (!ObjectUtils.equals(mGoodsCode, goodsCode)) {
            mGoodsCode = goodsCode;
            filter();
        }
    }

    private synchronized void filter() {
        if (mOriginalData == null) {
            return;
        }
        List<InventoryGoodsEntity> values = new ArrayList<>(mOriginalData);
        List<InventoryGoodsEntity> newValues;
        if (checkEmpty()) {
            newValues = new ArrayList<>(mOriginalData);
        } else {
            newValues = filterByGoodsCode(values);
        }
        mDataList = newValues;
        mDataHolder.clear();
        notifyChanged();
    }

    private List<InventoryGoodsEntity> filterByGoodsCode(List<InventoryGoodsEntity> values) {
        if (Validator.isEmpty(mGoodsCode)) {
            return values;
        }
        List<InventoryGoodsEntity> result = new ArrayList<>();
        for (int i = 0, count = values.size(); i < count; i++) {
            InventoryGoodsEntity value = values.get(i);
            if (ObjectUtils.contains(ObjectUtils.concat(value.getGoodsCode(), value.getPinyin()), mGoodsCode)) {
                result.add(value);
            }
        }
        return result;
    }

    private boolean checkEmpty() {
        return Validator.isEmpty(mGoodsCode);
    }

    public List<InventoryGoodsEntity> getDataByType(int type) {
        if (Validator.isEmpty(mDataList)) {
            return new ArrayList<>();
        }
        List<InventoryGoodsEntity> list = mDataHolder.get(type);
        if (list == null) {
            list = findDataByType(type);
            mDataHolder.put(type, list);
        }
        return list;
    }

    private List<InventoryGoodsEntity> findDataByType(int type) {
        List<InventoryGoodsEntity> list = new ArrayList<>();
        for (InventoryGoodsEntity entity : mDataList) {
            if (type == entity.getStatus()) {
                list.add(entity);
            }
        }
        return list;
    }

    public void reverseStatus(int id) {
        List<InventoryGoodsEntity> list = getDataByType(0);
        if (Validator.isEmpty(list)) {
            return;
        }
        for (InventoryGoodsEntity entity : list) {
            if (entity.getOriginalId() == id) {
                entity.setStatus(1);
                entity.setInventoryCount(entity.getCountInBill());
                break;
            }
        }
        mDataHolder.clear();
        inset(1);
        notifyChanged();
    }

    private void inset(int i) {
        mUnfinishedNumber -= i;
        mFinishedNumber += i;
        mEventBus.post(new NumberObserver(this));
    }

    public void reverseAllStatus() {
        List<InventoryGoodsEntity> list = getDataByType(0);
        if (Validator.isEmpty(list)) {
            return;
        }
        for (InventoryGoodsEntity entity : list) {
            entity.setStatus(1);
            entity.setInventoryCount(entity.getCountInBill());
        }
        mDataHolder.clear();
        inset(mUnfinishedNumber);
        notifyChanged();
    }

    public List<Integer> getAchievableIds() {
        List<InventoryGoodsEntity> list = getDataByType(0);
        if (Validator.isEmpty(list)) {
            return null;
        }
        int count = list.size();
        List<Integer> ids = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ids.add(list.get(i).getOriginalId());
        }
        return ids;
    }

    public void clear() {
        id = 0;
        mDataList = new ArrayList<>();
        mOriginalData = null;
        mFinishedNumber = 0;
        mUnfinishedNumber = 0;
        notifyChanged();
    }

    public abstract void notifyChanged();

    public void post(InventoryHelper helper) {
        mEventBus.post(helper);
    }

    /**
     * 获取统计数量
     *
     * @param type 0未完成 1 已完成
     */
    public int getNumber(int type) {
        return type == 0 ? mUnfinishedNumber : mFinishedNumber;
    }

    public boolean isForceCompleteAvailable() {
        return mIsForceCompleteAvailable && id != 0;
    }

    public void setForceCompleteAvailable(boolean forceCompleteAvailable) {
        mIsForceCompleteAvailable = forceCompleteAvailable;
    }

    public static final class NumberObserver {
        private InventoryHelper mHelper;

        NumberObserver(InventoryHelper helper) {
            mHelper = helper;
        }

        public InventoryHelper getHelper() {
            return mHelper;
        }

        public void setHelper(InventoryHelper helper) {
            mHelper = helper;
        }
    }
}
