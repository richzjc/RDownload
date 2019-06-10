package com.richzjc.rdownload.util;

import com.richzjc.rdownload.notification.anotation.DownloadProgress;
import com.richzjc.rdownload.notification.anotation.DownloadState;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class JavaFieldUtil {
    //Field数组里面只保留两个注解， DownloadProgress, DownloadState
    private static Map<String, Field[]> map = new HashMap<>();

    public static void saveFieldToMap(Object object) {
        if (object == null)
            return;
        String name = object.getClass().getName();
        if (!map.containsKey(name)) {
            Field[] fields = new Field[2];
            //0的位置存放DownloadProgress注解
            //1的位置存放DownloadState注解
            map.put(name, fields);
            DownloadProgress dp;
            DownloadState ds;
            Field[] allFields = object.getClass().getDeclaredFields();
            for (Field field : allFields) {
                dp = field.getAnnotation(DownloadProgress.class);
                if (dp != null)
                    fields[0] = field;

                ds = field.getAnnotation(DownloadState.class);
                if (ds != null)
                    fields[1] = field;
            }
        }
    }

    public static Field[] getFieldsFromMap(Object obj) {
        if (obj == null)
            return null;

        String name = obj.getClass().getName();
        if (map.containsKey(name))
            return map.get(name);
        else
            return null;
    }
}
