package com.teeny.wms.util.log;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see Printer
 * @since 2017/5/9
 */

public interface Printer {

    Settings init(String tag);

    void resetSettings();

    Printer t(String tag, int methodCount);

    void d(String message, Object... args);

    void d(Object object);

    void e(String message, Object... args);

    void e(Throwable throwable, String message, Object... args);

    void w(String message, Object... args);

    void i(String message, Object... args);

    void v(String message, Object... args);

    void wtf(String message, Object... args);

    void json(String json);

    void xml(String xml);

    void print(int priority, String tag, String message, Throwable throwable);
}
