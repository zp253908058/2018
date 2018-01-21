package com.teeny.wms.page.picking.helper;

import com.teeny.wms.model.OutputPickingItemEntity;
import com.teeny.wms.model.OutputPickingOrderEntity;
import com.teeny.wms.util.Validator;

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
            notifyChanged();
        }
    }

    public OutputPickingOrderEntity getData() {
        return this.mData;
    }

    public String getNextLocation() {
        List<OutputPickingItemEntity> list = mData.getDataList();
        int index = list.indexOf(mCurrent);
        int size = list.size();
        OutputPickingItemEntity result = null;
        for (int i = index; i < size; i++) {
            OutputPickingItemEntity entity = list.get(i);
            if (entity.getStatus() == 0) {
                result = entity;
                break;
            }
        }
        if (result == null) {
            for (int i = 0; i < index; i++) {
                OutputPickingItemEntity entity = list.get(i);
                if (entity.getStatus() == 0) {
                    result = entity;
                    break;
                }
            }
            if (result == null) {
                return "无";
            }
        }
        return result.getLocation();
    }

    public boolean hasNext() {
        return hasNext(true);
    }

    private boolean hasNext(boolean change) {
        if (mData == null) {
            return false;
        }
        List<OutputPickingItemEntity> list = mData.getDataList();
        if (Validator.isEmpty(list)) {
            return false;
        }
        int index = list.indexOf(mCurrent);
        int size = list.size();
        OutputPickingItemEntity result = null;
        for (int i = index; i < size; i++) {
            OutputPickingItemEntity entity = list.get(i);
            if (entity.getStatus() == 0) {
                result = entity;
                break;
            }
        }
        if (result == null) {
            for (int i = 0; i < index; i++) {
                OutputPickingItemEntity entity = list.get(i);
                if (entity.getStatus() == 0) {
                    result = entity;
                    break;
                }
            }
            if (result == null) {
                return false;
            }
        }
        if (change) {
            this.mCurrent = result;
        }
        return true;
    }

    public void next() {
        post();
    }

    public boolean hasPrev() {
        if (mData == null) {
            return false;
        }
        if (mCurrent == null) {
            return false;
        }
        int index = mData.getDataList().indexOf(mCurrent);
        return index != 0;
    }

    public void prev() {
        List<OutputPickingItemEntity> list = mData.getDataList();
        int index = list.indexOf(mCurrent);
        mCurrent = list.get(index - 1);
        post();
    }

    public OutputPickingItemEntity getCurrent() {
        return mCurrent;
    }

    public void addCount() {
        mData.setCompleted(mData.getCompleted() + 1);
    }

    public void post() {
        mEventBus.post(mCurrent);
    }

    public void clear() {
        mData = null;
        mCurrent = null;
    }

    public void notifyChanged() {
        mEventBus.post(this);
    }


}
