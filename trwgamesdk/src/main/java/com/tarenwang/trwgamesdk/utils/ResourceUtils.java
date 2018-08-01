package com.tarenwang.trwgamesdk.utils;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/10/26.
 */

public class ResourceUtils {
    /**
     * 获取Anim资源ID
     *
     * @param context
     * @param name
     * @return
     */
    public static int getAnimId(Context context, String name) {
        return context.getResources().getIdentifier(name, "anim", context.getPackageName());
    }

    /**
     * 获取Color资源ID
     *
     * @param context
     * @param name
     * @return
     */
    public static int getColorId(Context context, String name) {
        return context.getResources().getIdentifier(name, "color", context.getPackageName());
    }

    /**
     * 获取Drawable资源ID
     *
     * @param context
     * @param name
     * @return
     */
    public static int getDrawableId(Context context, String name) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

    /**
     * 获取Id资源ID
     *
     * @param context
     * @param name
     * @return
     */
    public static int getId(Context context, String name) {
        return context.getResources().getIdentifier(name, "id", context.getPackageName());
    }

    /**
     * @param context
     * @param name
     * @return
     */
    public static int getMip(Context context, String name) {
        return context.getResources().getIdentifier(name, "mipmap", context.getPackageName());
    }

    /**
     * * 获取dialog资源ID
     *
     * @param context
     * @param name
     * @return
     */

    /**
     * 获取Layout资源ID
     *
     * @param context
     * @param name
     * @return
     */
    public static int getLayoutId(Context context, String name) {
        return context.getResources().getIdentifier(name, "layout", context.getPackageName());
    }

    /**
     * 获取指定类里的指定资源对象
     *
     * @param context
     * @param fieldName
     * @param className
     * @return
     */
    private static Object getResourceId(Context context, String fieldName, String className) {
        String pkg = context.getPackageName() + ".R";
        try {
            for (Class cls : Class.forName(pkg).getClasses()) {
                if (!cls.getSimpleName().equals(className))
                    continue;
                for (Field field : cls.getFields()) {
                    String f = field.getName();
                    if (!f.equals(fieldName))
                        continue;
                    System.out.println(f);
                    Object obj = field.get(null);
                    return obj;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取String资源ID
     *
     * @param context
     * @param name
     * @return
     */
    public static int getStringId(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());
    }

    /**
     * 获取Style资源ID
     *
     * @param context
     * @param name
     * @return
     */
    public static int getStyleId(Context context, String name) {
        return context.getResources().getIdentifier(name, "style", context.getPackageName());
    }

    /**
     * 获取styleable资源ID
     *
     * @param context
     * @param name
     * @return
     */
    public static int getStyleable(Context context, String name) {
        return ((Integer) getResourceId(context, name, "styleable")).intValue();
    }

    /**
     * 获取styleable资源ID列表
     *
     * @param context
     * @param name
     * @return
     */
    public static int[] getStyleableArray(Context context, String name) {
        return (int[]) (int[]) getResourceId(context, name, "styleable");
    }

    /**
     * 获取styleable资源ID列表
     *
     * @param context
     * @param name
     * @return
     */
    public static int[] getStyleableArrayId(Context context, String name) {
        return getStyleableArray(context, name);
    }

    /**
     * 获取styleable资源ID
     *
     * @param context
     * @param name
     * @return
     */
    public static int getStyleableId(Context context, String name) {
        return context.getResources().getIdentifier(name, "styleable", context.getPackageName());
    }

}
