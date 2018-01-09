package com.teeny.wms.page.allot.helper;

import com.teeny.wms.model.AllotListEntity;
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
 * @see AllotListHelper
 * @since 2017/8/24
 */

public class AllotListHelper {

    private String mExportLocation;
    private String mImportLocation;

    private List<AllotListEntity> mOriginalData;
    private List<AllotListEntity> mDataSource;
    private EventBus mEventBus = EventBus.getDefault();

    public List<AllotListEntity> getDataSource() {
        return mDataSource;
    }

    public void setDataSource(List<AllotListEntity> dataSource) {
        this.mDataSource = dataSource;
        if (mDataSource == null) {
            mDataSource = new ArrayList<>();
        }
        this.mOriginalData = null;
        notifyChanged();
    }

    public String getExportLocation() {
        return mExportLocation;
    }

    public void setExportLocation(String exportLocation) {
        if (!ObjectUtils.equals(mExportLocation, exportLocation)) {
            mExportLocation = exportLocation;
            filter();
        }
    }

    public String getImportLocation() {
        return mImportLocation;
    }

    public void setImportLocation(String importLocation) {
        if (!ObjectUtils.equals(mImportLocation, importLocation)) {
            mImportLocation = importLocation;
            filter();
        }
    }

    private void filter() {
        if (mOriginalData == null) {
            if (mDataSource == null) {
                return;
            }
            mOriginalData = new ArrayList<>(mDataSource);
        }

        List<AllotListEntity> values = new ArrayList<>(mOriginalData);
        final int count = values.size();
        final ArrayList<AllotListEntity> newValues;

        if (Validator.isEmpty(mExportLocation) && Validator.isEmpty(mImportLocation)) {
            newValues = new ArrayList<>(mOriginalData);
        } else {
            newValues = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                final AllotListEntity value = values.get(i);
                String fCondition = value.getExportName();
                String sCondition = value.getImportName();
                if (Validator.isEmpty(fCondition) && Validator.isNotEmpty(mExportLocation)) {
                    break;
                }
                if (Validator.isEmpty(sCondition) && Validator.isNotEmpty(mImportLocation)) {
                    break;
                }
                if (Validator.isNotEmpty(mExportLocation) && Validator.isNotEmpty(mImportLocation)) {
                    if (ObjectUtils.contains(fCondition, mExportLocation) && ObjectUtils.contains(sCondition, mImportLocation)) {
                        newValues.add(value);
                    }
                    continue;
                }
                if (Validator.isNotEmpty(mExportLocation)) {
                    if (ObjectUtils.contains(fCondition, mExportLocation)) {
                        newValues.add(value);
                    }
                    continue;
                }
                if (Validator.isNotEmpty(mImportLocation)) {
                    if (ObjectUtils.contains(sCondition, mImportLocation)) {
                        newValues.add(value);
                    }
                }
            }
        }
        mDataSource = newValues;
        notifyChanged();
    }

    public void remove(AllotListEntity entity) {
        if (Validator.isNotEmpty(mDataSource) && mDataSource.contains(entity)) {
            mDataSource.remove(entity);
        }

        if (Validator.isNotEmpty(mOriginalData) && mOriginalData.contains(entity)) {
            mOriginalData.remove(entity);
        }
    }

    public void removes(List<AllotListEntity> list) {
        if (Validator.isNotEmpty(mDataSource)) {
            mDataSource.removeAll(list);
        }

        if (Validator.isNotEmpty(mOriginalData)) {
            mOriginalData.removeAll(list);
        }
    }

    public List<Integer> getIds(List<AllotListEntity> list) {
        if (Validator.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<Integer> ids = new ArrayList<>();
        for (AllotListEntity entity : list) {
            ids.add(entity.getOriginalId());
        }
        return ids;
    }

    public void clear() {
        mDataSource = new ArrayList<>();
        mOriginalData = null;
        notifyChanged();
    }

    private void notifyChanged() {
        mEventBus.post(this);
    }
}
