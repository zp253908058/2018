package com.teeny.wms.base;

import com.teeny.wms.model.KeyValueEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see KeyValueMultipleChoiceAdapter
 * @since 2017/8/28
 */

public class KeyValueMultipleChoiceAdapter extends MultipleChoiceAdapter<KeyValueEntity> {
    public KeyValueMultipleChoiceAdapter(List<KeyValueEntity> items) {
        super(items);
    }

    @Override
    public long getItemId(int position) {
        KeyValueEntity entity = getItem(position);
        return entity != null ? entity.getKey() : 0;
    }
}
