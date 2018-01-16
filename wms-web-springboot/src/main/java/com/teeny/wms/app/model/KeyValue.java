package com.teeny.wms.app.model;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see KeyValue
 * @since 2017/10/25
 */
public interface KeyValue<K, V> {

    void setKey(K key);

    K getKey();

    void setValue(V value);

    V getValue();
}
