package com.teeny.wms.web.model;

import java.io.Serializable;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see KeyValue
 * @since 2017/10/25
 */
public interface KeyValue<K, V> extends Serializable{

    void setKey(K key);

    K getKey();

    void setValue(V value);

    V getValue();
}
