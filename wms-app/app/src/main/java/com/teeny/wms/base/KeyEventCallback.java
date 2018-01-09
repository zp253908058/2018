package com.teeny.wms.base;

import android.view.KeyEvent;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see KeyEventCallback
 * @since 2017/7/19
 */

public interface KeyEventCallback {
    boolean dispatchKeyEvent(KeyEvent event);
}
