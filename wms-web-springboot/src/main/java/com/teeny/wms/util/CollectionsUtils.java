package com.teeny.wms.util;

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

    public static <E> int sizeOf(Collection<E> collection) {
        return Validator.isEmpty(collection) ? 0 : collection.size();
    }

    public static <K, V> int sizeOf(Map<K, V> map) {
        return Validator.isEmpty(map) ? 0 : map.size();
    }

    public static <T> int sizeOf(T[] t) {
        return Validator.isEmpty(t) ? 0 : t.length;
    }

    public static <T> T get(List<T> collection, int index) {
        return sizeOf(collection) == 0 ? null : collection.get(index);
    }
}
