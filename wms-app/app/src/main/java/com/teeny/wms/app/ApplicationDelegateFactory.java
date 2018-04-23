package com.teeny.wms.app;

import android.os.Build;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ApplicationDelegateFactory
 * @since 2017/7/8
 */

public class ApplicationDelegateFactory {
    public static ApplicationDelegate create() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new ApplicationDelegateImplO();
        }
        return new ApplicationDelegateImpl();
    }
}
