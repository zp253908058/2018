package com.teeny.wms.util;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see MathUtils
 * @since 2017/7/19
 */

public class MathUtils {
    public static int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    public static float constrain(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }
}
