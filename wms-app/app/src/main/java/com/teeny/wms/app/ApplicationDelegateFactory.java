package com.teeny.wms.app;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ApplicationDelegateFactory
 * @since 2017/7/8
 */

public class ApplicationDelegateFactory {
    public static ApplicationDelegate create(){
        return new ApplicationDelegateImpl();
    }
}
