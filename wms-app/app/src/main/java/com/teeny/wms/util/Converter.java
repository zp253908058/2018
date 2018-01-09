package com.teeny.wms.util;

import android.content.Context;

import com.google.gson.Gson;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see Converter
 * @since 2017/7/15
 */

public class Converter {

    private static final String TAG = CollectionsUtils.class.getSimpleName();

    private static final Gson sGson = new Gson();

    private Converter() {
        throw new UnsupportedOperationException();
    }

    /**
     * return the integer value of the string.
     *
     * @param value        value
     * @param defaultValue defaultValue
     * @return if convert successful, the real value will be return.
     * otherwise the default value will be return.
     */
    public static int toInt(String value, int defaultValue) {
        if (!Validator.isNumeric(value)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * return the integer value of the string.
     *
     * @param value value
     * @return if convert successful, the real value will be return.
     * otherwise 0 will be return.
     */
    public static int toInt(String value) {
        return toInt(value, 0);
    }

    /**
     * return the long value of the string.
     *
     * @param value        value
     * @param defaultValue defaultValue
     * @return if success, the real value will be return.
     * otherwise the default value will be return.
     */
    public static long toLong(String value, long defaultValue) {
        if (!Validator.isNumeric(value)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * return the long value of the string.
     *
     * @param value value
     * @return if success, the real value will be return.
     * otherwise 0 will be return.
     */
    public static long toLong(String value) {
        return toLong(value, 0);
    }

    /**
     * return the double value of the string.
     *
     * @param value        value
     * @param defaultValue defaultValue
     * @return if success, the real value will be return.
     * otherwise the default value will be return.
     */
    public static double toDouble(String value, double defaultValue) {
        if (!Validator.isNumeric(value)) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * return the double value of the string.
     *
     * @param value value
     * @return if success, the real value will be return.
     * otherwise 0 will be return.
     */
    public static double toDouble(String value) {
        return toDouble(value, 0);
    }

    /**
     * return the float value of the string.
     *
     * @param value        value
     * @param defaultValue defaultValue
     * @return if success, the real value will be return.
     * otherwise the default value will be return.
     */
    public static float toFloat(String value, float defaultValue) {
        if (!Validator.isNumeric(value)) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * return the double value of the string.
     *
     * @param value value
     * @return if success, the real value will be return.
     * otherwise 0 will be return.
     */
    public static float toFloat(String value) {
        return toFloat(value, 0);
    }

    /**
     * return the boolean value of the string.
     *
     * @param value        value
     * @param defaultValue defaultValue
     * @return if success, the real value will be return.
     * otherwise the default value will be return.
     */
    public static boolean toBoolean(String value, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * return the boolean value of the string.
     *
     * @param value value
     * @return if success, the real value will be return.
     * otherwise 0 will be return.
     */
    public static boolean toBoolean(String value) {
        return toBoolean(value, false);
    }

    /**
     * return the pixel value.
     *
     * @param context context
     * @param dpValue dp value.
     * @return the pixel value.
     */
    public static int toPx(Context context, float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5F);
    }

    public static int toDp(Context context, float pxValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5F);
    }

    public static <T> T toEntity(Class<T> clz, String json) {
        return sGson.fromJson(json, clz);
    }

    public static String toJson(Object object) {
        return sGson.toJson(object);
    }
}
