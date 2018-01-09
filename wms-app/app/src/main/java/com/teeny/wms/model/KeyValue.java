package com.teeny.wms.model;

import android.util.Pair;

import com.teeny.wms.util.ObjectUtils;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see KeyValue
 * @since 2017/8/19
 */

public class KeyValue<K, V> {

    private K key;
    private V value;

    public KeyValue() {
    }

    public KeyValue(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public K getKey() {
        return this.key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public V getValue() {
        return this.value;
    }

    /**
     * Checks the two objects for equality by delegating to their respective
     * {@link Object#equals(Object)} methods.
     *
     * @param o the {@link Pair} to which this one is to be checked for equality
     * @return true if the underlying objects of the Pair are both considered
     * equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof KeyValue)) {
            return false;
        }
        KeyValue<?, ?> keyValue = (KeyValue<?, ?>) o;
        return ObjectUtils.equals(keyValue.key, key) && ObjectUtils.equals(keyValue.value, value);
    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Pair
     */
    @Override
    public int hashCode() {
        return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
    }

    @Override
    public String toString() {
        return "KeyValue{" + String.valueOf(key) + " " + String.valueOf(value) + "}";
    }

    public static <K, V> KeyValue<K, V> create(K key, V value) {
        return new KeyValue<K, V>(key, value);
    }
}
