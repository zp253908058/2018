package com.teeny.wms.util.log;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see Logger
 * @since 2017/5/9
 */

public class Logger {

    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;

    @IntDef({DEBUG, ERROR, ASSERT, INFO, VERBOSE, WARN})
    @Retention(SOURCE)
    @Target({FIELD, METHOD, PARAMETER})
    public @interface Priority {

    }

    private static final String DEFAULT_TAG = "LOGGER";

    private static Printer sPrinter;

    private Logger() {

    }

    /**
     * It is used to get the settings object in order to change settings
     *
     * @return the settings object
     */
    public static Settings init() {
        return init(DEFAULT_TAG);
    }

    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in Logger as TAG
     */
    public static Settings init(String tag) {
        if (sPrinter == null) {
            synchronized (Logger.class) {
                if (sPrinter == null) {
                    sPrinter = new LoggerPrinter();
                }
            }
        }
        return sPrinter.init(tag);
    }

    public static void resetSettings() {
        sPrinter.resetSettings();
    }

    public static void t(String tag, int methodCount) {
        sPrinter.t(tag, methodCount);
    }

    public static void t(String tag) {
        sPrinter.t(tag, 2);
    }

    public static void t(int methodCount) {
        sPrinter.t(null, methodCount);
    }

    public static void log(@Priority int priority, String tag, String message, Throwable throwable) {
        sPrinter.print(priority, tag, message, throwable);
    }

    public static void d(String message, Object... args) {
        sPrinter.d(message, args);
    }

    public static void d(Object object) {
        sPrinter.d(object);
    }

    public static void e(String message, Object... args) {
        sPrinter.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        sPrinter.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        sPrinter.i(message, args);
    }

    public static void v(String message, Object... args) {
        sPrinter.v(message, args);
    }

    public static void w(String message, Object... args) {
        sPrinter.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        sPrinter.wtf(message, args);
    }

    /**
     * Formats the json content and print it
     *
     * @param json the json content
     */
    public static void json(String json) {
        sPrinter.json(json);
    }

    /**
     * Formats the json content and print it
     *
     * @param xml the xml content
     */
    public static void xml(String xml) {
        sPrinter.xml(xml);
    }

    /**
     * print current time millisecond.
     */
    public static void millis(){
        sPrinter.d(System.currentTimeMillis());
    }
}
