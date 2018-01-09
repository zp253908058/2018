package com.teeny.wms.util;

import com.teeny.wms.datasouce.local.Preferences;
import com.teeny.wms.util.log.Logger;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see PreferencesUtils
 * @since 2017/8/5
 */

public class PreferencesUtils {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface Ignore {

    }

    public static void putInSharedPreferences(Object object, Preferences sp) throws IllegalAccessException {
        if (object == null || sp == null) {
            Logger.e(String.valueOf(object == null) + "," + String.valueOf(sp == null));
            return;
        }
        List<Field> fields = ObjectUtils.getAllDeclaredFields(object);
        for (Field field : fields) {
            if(field.isSynthetic()){
                continue;
            }
            Annotation annotation = field.getAnnotation(Ignore.class);
            if (annotation != null) {
                continue;
            }
            field.setAccessible(true);
            String type = field.getType().getSimpleName();
            String key = field.getName();
            putInSharedPreferences(key, type, field.get(object), sp);
        }
    }

    public static void putInSharedPreferences(String key, String type, Object value, Preferences sp) {
        switch (type) {
            case "int":
                sp.putInt(key, (int) value);
                break;
            case "float":
                sp.putFloat(key, (float) value);
                break;
            case "long":
                sp.putLong(key, (long) value);
                break;
            case "boolean":
                sp.putBoolean(key, (boolean) value);
                break;
            case "String":
                sp.putString(key, (String) value);
                break;
            case "Set":
                //noinspection unchecked
                sp.putStringSet(key, (Set<String>) value);
                break;
            case "byte":
            case "short":
            case "double":
            case "char":
            default:
                Logger.e("unsupported type : " +type);
                break;
        }
    }

    public static void putInEntity(Object object, Preferences sp) throws IllegalAccessException {
        List<Field> fields = ObjectUtils.getAllDeclaredFields(object);
        for (Field field : fields) {
            if(field.isSynthetic()){
                continue;
            }
            Annotation annotation = field.getAnnotation(Ignore.class);
            if (annotation != null) {
                continue;
            }
            field.setAccessible(true);
            String type = field.getType().getSimpleName();
            switch (type) {
                case "int":
                    field.setInt(object, sp.getInt(field.getName(), 0));
                    break;
                case "float":
                    field.setFloat(object, sp.getFloat(field.getName(), 0));
                    break;
                case "long":
                    field.setLong(object, sp.getLong(field.getName(), 0));
                    break;
                case "boolean":
                    field.setBoolean(object, sp.getBoolean(field.getName(), false));
                    break;
                case "String":
                    field.set(object, sp.getString(field.getName(), ""));
                    break;
                case "Set":
                    field.set(object, sp.getStringSet(field.getName(), null));
                    break;
                case "byte":
                case "short":
                case "double":
                case "char":
                default:
                    Logger.e("unsupported type : " + type);
                    break;
            }
        }
    }
}
