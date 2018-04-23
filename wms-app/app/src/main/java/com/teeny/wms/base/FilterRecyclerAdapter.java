package com.teeny.wms.base;

import android.support.annotation.Nullable;
import android.widget.Filterable;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see FilterRecyclerAdapter
 * @since 2018/4/21
 */
public abstract class FilterRecyclerAdapter<T> extends RecyclerAdapter<T> implements Filterable{
    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public FilterRecyclerAdapter(@Nullable List<T> items) {
        super(items);
    }
}
