package com.teeny.wms.page.delivery.helper;

import android.support.v4.util.SparseArrayCompat;

import com.teeny.wms.model.ShopDeliveryGoodsEntity;
import com.teeny.wms.util.ObjectUtils;
import com.teeny.wms.util.Validator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopDeliveryHelper
 * @since 2018/1/18
 */

public class ShopDeliveryHelper {

    private int mBillId;

    private List<ShopDeliveryGoodsEntity> mDataList;
    private List<ShopDeliveryGoodsEntity> mOriginalData;
    private SparseArrayCompat<List<ShopDeliveryGoodsEntity>> mDataHolder;

    private String mGoodsCode;

    private EventBus mEventBus;

    public ShopDeliveryHelper() {
        mEventBus = EventBus.getDefault();
        mDataHolder = new SparseArrayCompat<>();
    }

    public void setDataList(int billId, List<ShopDeliveryGoodsEntity> dataList) {
        this.mBillId = billId;
        mDataList = dataList;
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataHolder.clear();
        this.mOriginalData = new ArrayList<>(mDataList);
        notifyChanged();
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
        List<ShopDeliveryGoodsEntity> values = new ArrayList<>(mOriginalData);
        List<ShopDeliveryGoodsEntity> newValues;
        if (checkEmpty()) {
            newValues = new ArrayList<>(mOriginalData);
        } else {
            newValues = filterByGoodsCode(values);
        }
        mDataList = newValues;
        mDataHolder.clear();
        notifyChanged();
    }

    private List<ShopDeliveryGoodsEntity> filterByGoodsCode(List<ShopDeliveryGoodsEntity> values) {
        if (Validator.isEmpty(mGoodsCode)) {
            return values;
        }
        List<ShopDeliveryGoodsEntity> result = new ArrayList<>();
        for (int i = 0, count = values.size(); i < count; i++) {
            ShopDeliveryGoodsEntity value = values.get(i);
            if (ObjectUtils.contains(ObjectUtils.concat(value.getGoodsCode(), value.getPinyin()), mGoodsCode)) {
                result.add(value);
            }
        }
        return result;
    }

    private boolean checkEmpty() {
        return Validator.isEmpty(mGoodsCode);
    }

    public List<ShopDeliveryGoodsEntity> getDataByType(int type) {
        if (Validator.isEmpty(mDataList)) {
            return new ArrayList<>();
        }
        List<ShopDeliveryGoodsEntity> list = mDataHolder.get(type);
        if (list == null) {
            list = findDataByType(type);
            mDataHolder.put(type, list);
        }
        return list;
    }

    private List<ShopDeliveryGoodsEntity> findDataByType(int type) {
        List<ShopDeliveryGoodsEntity> list = new ArrayList<>();
        for (ShopDeliveryGoodsEntity entity : mDataList) {
            if (type == entity.getState()) {
                list.add(entity);
            }
        }
        return list;
    }

    public void reverseAllStatus() {
        List<ShopDeliveryGoodsEntity> list = getDataByType(0);
        if (Validator.isEmpty(list)) {
            return;
        }
        for (ShopDeliveryGoodsEntity entity : list) {
            entity.setState(1);
        }
        mDataHolder.clear();
        notifyChanged();
    }

    public void reverseStatus(Integer id) {
        List<ShopDeliveryGoodsEntity> list = getDataByType(0);
        if (Validator.isEmpty(list)) {
            return;
        }
        for (ShopDeliveryGoodsEntity entity : list) {
            if (entity.getId() == id) {
                entity.setState(1);
                break;
            }
        }
        mDataHolder.clear();
        notifyChanged();
    }


    public List<Integer> getAchievableIds() {
        List<ShopDeliveryGoodsEntity> list = getDataByType(0);
        if (Validator.isEmpty(list)) {
            return null;
        }
        int count = list.size();
        List<Integer> ids = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ids.add(list.get(i).getId());
        }
        return ids;
    }

    public void clear() {
        mDataList = new ArrayList<>();
        mOriginalData = null;
        notifyChanged();
    }


    public int getNumber(int position) {
        return getDataByType(position).size();
    }


    public int getBillId() {
        return mBillId;
    }

    public void notifyChanged() {
        post(this);
    }

    public void post(ShopDeliveryHelper helper) {
        mEventBus.post(helper);
    }
}
