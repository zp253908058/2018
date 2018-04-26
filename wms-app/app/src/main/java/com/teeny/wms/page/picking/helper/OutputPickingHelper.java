package com.teeny.wms.page.picking.helper;

import com.teeny.wms.model.OutputPickingItemEntity;
import com.teeny.wms.model.OutputPickingOrderEntity;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.CollectionsUtils;
import com.teeny.wms.util.ObjectUtils;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.log.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see OutputPickingHelper
 * @since 2018/1/21
 */

public class OutputPickingHelper {

    private static final String TAG = OutputPickingHelper.class.getSimpleName();

    private static volatile OutputPickingHelper mInstance;

    public static OutputPickingHelper getInstance() {
        if (mInstance == null) {
            synchronized (OutputPickingHelper.class) {
                if (mInstance == null) {
                    mInstance = new OutputPickingHelper();
                }
            }
        }
        return mInstance;
    }

    private OutputPickingOrderEntity mData;
    List<OutputPickingItemEntity> mDataList;
    private EventBus mEventBus;
    private OutputPickingItemEntity mCurrent;
    private int mCurrentId;

    private OutputPickingHelper() {
        mEventBus = EventBus.getDefault();
    }

    public void setData(OutputPickingOrderEntity data) {
        if (data != null) {
            this.mData = data;
            mDataList = new ArrayList<>(data.getDataList());
            Collections.sort(mDataList, (t, t1) -> t.getId() > t1.getId() ? 1 : 0);
            initialize();
        }
    }

    public OutputPickingOrderEntity getData() {
        return this.mData;
    }

    public OutputPickingItemEntity getCurrent() {
        return mCurrent;
    }

    private void initialize() {
        mCurrent = findAvailableData();
        if (mCurrent.getStatus() == 0) {
            notifyChanged();
        } else {
            Toaster.showToast("单据已经全部完成.");
        }
    }

    private OutputPickingItemEntity findAvailableData() {
        OutputPickingItemEntity result = mDataList.get(0);
        mCurrentId = result.getId();
        if (result.getStatus() == 0) {
            return result;
        }
        for (OutputPickingItemEntity entity : mDataList) {
            if (entity.getStatus() == 0) {
                result = entity;
            }
        }
        return result;
    }

    private void notifyChanged() {
        mEventBus.post(this);
    }

    public String getNextLocation() {
        int size = CollectionsUtils.sizeOf(mDataList);
        int currentIndex = mDataList.indexOf(mCurrent);
        Logger.e(TAG, currentIndex);
        String result = "";
        for (int i = 1; i <= 3; i++) {
            int j = currentIndex + i;
            if (j >= size) {
                break;
            }
            result = ObjectUtils.concat(result, mDataList.get(j).getLocation());
            if (j + 1 == size) {
                break;
            }
            result = ObjectUtils.concat(result, ",");
        }
        return Validator.isEmpty(result) ? "无" : result.trim();
    }

    public boolean hasNext() {
        if (Validator.isEmpty(mDataList)) {
            return false;
        }
        int size = CollectionsUtils.sizeOf(mDataList);
        int next = mDataList.indexOf(mCurrent) + 1;
        return next != size;
    }


    public void next() {
        int index = mDataList.indexOf(mCurrent);
        mCurrent = mDataList.get(index + 1);
        post();
    }

    public boolean hasPrev() {
        if (mData == null) {
            return false;
        }
        if (Validator.isEmpty(mDataList)) {
            return false;
        }
        int index = mDataList.indexOf(mCurrent);
        return index != 0;
    }

    public void prev() {
        int index = mDataList.indexOf(mCurrent);
        mCurrent = mDataList.get(index - 1);
        post();
    }

    public boolean isLast() {
        int size = CollectionsUtils.sizeOf(mDataList);
        int next = mDataList.indexOf(mCurrent) + 1;
        return size == next;
    }

    public void clear() {
        mData = null;
        mCurrent = null;
    }

    private void post() {
        mEventBus.post(mCurrent);
    }

    public String getProgress() {
        return (mDataList.indexOf(mCurrent) + 1) + "/" + mDataList.size();
    }
}