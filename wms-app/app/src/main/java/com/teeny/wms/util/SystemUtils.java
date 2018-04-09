package com.teeny.wms.util;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SystemUtils
 * @since 2018/4/9
 */
public class SystemUtils {
    /**
     * 获取当前本地apk的版本
     *
     * @param context 上下文
     * @return 当前版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return 当前版本名称
     */
    public static String getVersionName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }
}
