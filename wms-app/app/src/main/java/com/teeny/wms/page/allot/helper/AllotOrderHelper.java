package com.teeny.wms.page.allot.helper;

import com.teeny.wms.model.AllotGoodsEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotOrderHelper
 * @since 2018/1/9
 */

public class AllotOrderHelper {

    private List<AllotGoodsEntity> mItems;
    private EventBus mEventBus;

    public AllotOrderHelper() {
        mEventBus = EventBus.getDefault();
    }

    public List<AllotGoodsEntity> getItems() {
        return mItems;
    }

    public void setItems(List<AllotGoodsEntity> items) {
        if (mItems == items){
            return;
        }
        this.mItems = items;
        if (mItems == null) {
            mItems = new ArrayList<>();
        }
        notifyChanged();
    }

    public List<AllotGoodsEntity> getSelectedItems() {
        return mItems;
    }

    private void notifyChanged(){
        mEventBus.post(this);
    }
}
