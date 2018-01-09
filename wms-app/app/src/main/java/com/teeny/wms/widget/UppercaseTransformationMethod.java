package com.teeny.wms.widget;

import android.text.method.ReplacementTransformationMethod;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see UppercaseTransformationMethod
 * @since 2017/8/16
 */

public class UppercaseTransformationMethod extends ReplacementTransformationMethod {
    @Override
    protected char[] getOriginal() {
        return new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    }

    @Override
    protected char[] getReplacement() {
        return new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    }
}
