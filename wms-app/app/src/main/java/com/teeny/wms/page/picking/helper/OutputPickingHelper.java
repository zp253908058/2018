package com.teeny.wms.page.picking.helper;

import com.teeny.wms.model.OutputPickingItemEntity;
import com.teeny.wms.model.OutputPickingOrderEntity;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.CollectionsUtils;
import com.teeny.wms.util.ObjectUtils;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.log.Logger;

import org.greenrobot.eventbus.EventBus;

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
    private EventBus mEventBus;
    private OutputPickingItemEntity mCurrent;

    private OutputPickingHelper() {
        mEventBus = EventBus.getDefault();
    }

    public void setData(OutputPickingOrderEntity data) {
        if (data != null) {
            this.mData = data;
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
        List<OutputPickingItemEntity> list = mData.getDataList();
        OutputPickingItemEntity result = list.get(0);
        if (result.getStatus() == 0) {
            return result;
        }
        for (OutputPickingItemEntity entity : list) {
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
        List<OutputPickingItemEntity> list = mData.getDataList();
        int size = CollectionsUtils.sizeOf(list);
        int currentIndex = list.indexOf(mCurrent);
        Logger.e(TAG, currentIndex);
        String result = "";
        for (int i = 1; i <= 3; i++) {
            int j = currentIndex + i;
            if (j >= size) {
                break;
            }
            result = ObjectUtils.concat(result, list.get(j).getLocation());
            if (j + 1 == size) {
                break;
            }
            result = ObjectUtils.concat(result, ",");
        }
        return Validator.isEmpty(result) ? "无" : result.trim();
    }

    public boolean hasNext() {
        List<OutputPickingItemEntity> list = mData.getDataList();
        if (Validator.isEmpty(list)) {
            return false;
        }
        int size = CollectionsUtils.sizeOf(list);
        int next = list.indexOf(mCurrent) + 1;
        return next != size;
    }


    public void next() {
        List<OutputPickingItemEntity> list = mData.getDataList();
        int index = list.indexOf(mCurrent);
        mCurrent = list.get(index + 1);
        post();
    }

    public boolean hasPrev() {
        if (mData == null) {
            return false;
        }
        List<OutputPickingItemEntity> list = mData.getDataList();
        if (Validator.isEmpty(list)) {
            return false;
        }
        int index = list.indexOf(mCurrent);
        return index != 0;
    }

    public void prev() {
        List<OutputPickingItemEntity> list = mData.getDataList();
        int index = list.indexOf(mCurrent);
        mCurrent = list.get(index - 1);
        post();
    }

    public boolean isLast() {
        List<OutputPickingItemEntity> list = mData.getDataList();
        int size = CollectionsUtils.sizeOf(list);
        int next = list.indexOf(mCurrent) + 1;
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
        List<OutputPickingItemEntity> list = mData.getDataList();
        return (list.indexOf(mCurrent) + 1) + "/" + list.size();
    }
}