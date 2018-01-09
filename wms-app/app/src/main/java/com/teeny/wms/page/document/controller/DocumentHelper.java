package com.teeny.wms.page.document.controller;

import com.teeny.wms.model.DocumentEntity;
import com.teeny.wms.model.DocumentResponseEntity;
import com.teeny.wms.util.CollectionsUtils;
import com.teeny.wms.util.Validator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see DocumentHelper
 * @since 2017/8/19
 */

public class DocumentHelper {

    private static volatile DocumentHelper mInstance;

    public static DocumentHelper getInstance() {
        if (mInstance == null) {
            synchronized (DocumentHelper.class) {
                if (mInstance == null) {
                    mInstance = new DocumentHelper();
                }
            }
        }
        return mInstance;
    }

    private DocumentResponseEntity mEntity;
    private EventBus mEventBus = EventBus.getDefault();
    private final Object mLock = new Object();

    private DocumentHelper() {

    }

    public void setEntity(DocumentResponseEntity entity) {
        synchronized (mLock) {
            mEntity = entity;
            if (mEntity == null) {
                throw new NullPointerException("entity == null.");
            }
            notifyChanged();
        }
    }

    private void notifyChanged() {
        mEventBus.post(this);
    }

    public int getAcceptanceCount() {
        synchronized (mLock) {
            return CollectionsUtils.sizeOf(mEntity.getAcceptanceList());
        }
    }

    public int getPutawayCount() {
        synchronized (mLock) {
            return CollectionsUtils.sizeOf(mEntity.getPutawayList());
        }
    }

    public int getAllotCount() {
        synchronized (mLock) {
            return CollectionsUtils.sizeOf(mEntity.getAllotList());
        }
    }

    public int getReviewCount() {
        synchronized (mLock) {
            return CollectionsUtils.sizeOf(mEntity.getReviewList());
        }
    }

    public List<DocumentEntity> getDataByType(int type) {
        switch (type) {
            case 0:
                return getAll();
            case 1:
                return getAcceptanceList();
            case 2:
                return getPutawayList();
            case 3:
                return getAllotList();
            case 4:
                return getReviewList();
            default:
                return null;
        }
    }

    private List<DocumentEntity> getAll() {
        synchronized (mLock) {
            ArrayList<DocumentEntity> result = new ArrayList<>();
            if (Validator.isNotEmpty(mEntity.getAcceptanceList())) {
                result.addAll(mEntity.getAcceptanceList());
            }
            if (Validator.isNotEmpty(mEntity.getAllotList())) {
                result.addAll(mEntity.getAllotList());
            }
            if (Validator.isNotEmpty(mEntity.getPutawayList())) {
                result.addAll(mEntity.getPutawayList());
            }
            if (Validator.isNotEmpty(mEntity.getReviewList())) {
                result.addAll(mEntity.getReviewList());
            }
            Collections.sort(result, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
            return result;
        }
    }

    private List<DocumentEntity> getAcceptanceList() {
        synchronized (mLock) {
            return mEntity.getAcceptanceList();
        }
    }

    private List<DocumentEntity> getPutawayList() {
        synchronized (mLock) {
            return mEntity.getPutawayList();
        }
    }

    private List<DocumentEntity> getAllotList() {
        synchronized (mLock) {
            return mEntity.getAllotList();
        }
    }

    private List<DocumentEntity> getReviewList() {
        synchronized (mLock) {
            return mEntity.getReviewList();
        }
    }

    public void notifyDocumentChanged() {
        mEventBus.post(new DocumentChangedFlag());
    }


    public static final class DocumentChangedFlag {

    }
}
