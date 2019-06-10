package com.richzjc.rdownload.util;

import android.util.Log;

import java.lang.reflect.Field;

public class UpdateDownloadStateUtil {

    //这里必须要保证attrs  和 fields里面的顺序是一致的
    public static void updateDownloadState(Object obj, Object[] attrs, Field[] fields){
        if(attrs == null || fields == null || attrs.length != fields.length)
            throw  new IllegalStateException("长度不对");

        if(obj == null)
            throw new IllegalStateException("obj 不能为空");

        int length = attrs.length;
        Field field;
        try {
            for(int i = 0; i < length; i ++){
                field = fields[i];
                if(field != null) {
                    field.setAccessible(true);
                    field.set(obj, attrs[i]);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Log.i("status", obj.toString());
    }
}
