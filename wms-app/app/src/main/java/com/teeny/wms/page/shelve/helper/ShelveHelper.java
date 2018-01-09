package com.teeny.wms.page.shelve.helper;

import android.support.v4.util.SparseArrayCompat;

import com.teeny.wms.model.ShelveEntity;
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
 * @see ShelveHelper
 * @since 2017/8/11
 */

public class ShelveHelper {

    private List<ShelveEntity> mOriginalData;
    private List<ShelveEntity> mDataSource;
    private SparseArrayCompat<List<ShelveEntity>> mDataHolder;
    private EventBus mEventBus;

    public ShelveHelper() {
        mDataHolder = new SparseArrayCompat<>();
        mEventBus = EventBus.getDefault();
    }

    public List<ShelveEntity> getDataSource() {
        return mDataSource;
    }

    public void setDataSource(List<ShelveEntity> dataSource) {
        this.mDataSource = dataSource;
        if (mDataSource == null) {
            mDataSource = new ArrayList<>();
        }
        mDataHolder.clear();
        this.mOriginalData = null;
        notifyChanged();
    }

    public List<ShelveEntity> getDataByType(int type) {
        if (Validator.isEmpty(mDataSource)) {
            return new ArrayList<>();
        }
        List<ShelveEntity> list = mDataHolder.get(type);
        if (list == null) {
            list = findDataByType(type);
            mDataHolder.put(type, list);
        }
        return list;
    }

    private List<ShelveEntity> findDataByType(int type) {
        List<ShelveEntity> list = new ArrayList<>();
        switch (type) {
            case 0:
                list.addAll(mDataSource);
                break;
            case 1:
            case 2:
                for (ShelveEntity entity : mDataSource) {
                    if (type - 1 == entity.getStatus()) {
                        list.add(entity);
                    }
                }
                break;
        }
        return list;
    }

    public void filterByLocationAndGoodsCode(String locationCode, String goodsCode) {
        if (mOriginalData == null) {
            if (mDataSource == null) {
                return;
            }
            mOriginalData = new ArrayList<>(mDataSource);
        }

        List<ShelveEntity> values = new ArrayList<>(mOriginalData);
        final int count = values.size();
        final ArrayList<ShelveEntity> newValues;

        if (Validator.isEmpty(locationCode) && Validator.isEmpty(goodsCode)) {
            newValues = new ArrayList<>(mOriginalData);
        } else {
            newValues = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                final ShelveEntity value = values.get(i);
                String fCondition = value.getLocationCode();
                String sCondition = ObjectUtils.concat(value.getGoodsCode(), value.getPinyin());
                if (Validator.isEmpty(fCondition) && Validator.isNotEmpty(locationCode)) {
                    continue;
                }
                if (Validator.isEmpty(sCondition) && Validator.isNotEmpty(goodsCode)) {
                    continue;
                }

                if (Validator.isNotEmpty(locationCode) && Validator.isNotEmpty(goodsCode)) {
                    if (fCondition.contains(locationCode) && sCondition.contains(goodsCode)) {
                        newValues.add(value);
                    }
                    continue;
                }
                if (Validator.isNotEmpty(locationCode)) {
                    if (fCondition.contains(locationCode)) {
                        newValues.add(value);
                    }
                    continue;
                }
                if (Validator.isNotEmpty(goodsCode)) {
                    if (sCondition.contains(goodsCode)) {
                        newValues.add(value);
                    }
                }
            }
        }
        mDataSource = newValues;
        mDataHolder.clear();
        notifyChanged();
    }

    public List<Integer> getAchievableIds() {
        List<ShelveEntity> list = getDataByType(1);
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

    public void reverseAllStatus() {
        List<ShelveEntity> list = getDataByType(1);
        if (Validator.isEmpty(list)) {
            return;
        }
        for (ShelveEntity entity : list) {
            entity.setStatus(1);
        }
        mDataHolder.clear();
        notifyChanged();
    }

    public void reverseStatus(int id) {
        List<ShelveEntity> list = getDataByType(1);
        if (Validator.isEmpty(list)) {
            return;
        }
        for (ShelveEntity entity : list) {
            if (entity.getOriginalId() == id) {
                entity.setStatus(1);
                list.remove(entity);
                getDataByType(2).add(entity);
                break;
            }
        }
        notifyChanged();
    }

    public void clear() {
        mDataSource = new ArrayList<>();
        mOriginalData = null;
        notifyChanged();
    }

    public void notifyChanged() {
        mEventBus.post(this);
    }
}
