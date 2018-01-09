package com.teeny.wms.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see CollectionsUtils
 * @since 2017/7/8
 */

public class CollectionsUtils {

    private static final String TAG = CollectionsUtils.class.getSimpleName();

    private CollectionsUtils() {
        throw new UnsupportedOperationException();
    }

    public static <E> int sizeOf(@Nullable Collection<E> collection) {
        return Validator.isEmpty(collection) ? 0 : collection.size();
    }

    public static <K, V> int sizeOf(@Nullable Map<K, V> map) {
        return Validator.isEmpty(map) ? 0 : map.size();
    }

    public static <E> int sizeOf(@Nullable E[] e) {
        return Validator.isEmpty(e) ? 0 : e.length;
    }

    public static <E> E get(@NonNull List<E> collection, int index) {
        return sizeOf(collection) == 0 ? null : collection.get(index);
    }
}
