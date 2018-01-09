package com.teeny.wms.datasouce.local.cache;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see Cache
 * @since 2017/8/5
 */

public interface Cache<T> {

    T get();

    Cache<T> set(T cache);

    void put(String key, Object value);

    Object peek(String key);

    void clear();

    void save();
}
