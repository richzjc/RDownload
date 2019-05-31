package com.richzjc.rdownload.download.util;

import com.richzjc.rdownload.notification.callback.ParentTaskCallback;

import java.util.ArrayList;
import java.util.List;

public class TaskUtils {
    public static List<Runnable> getAllTasks(ParentTaskCallback taskCallback){
        List<Runnable> tasks = new ArrayList<>();
        if(taskCallback != null){
           for(String url : taskCallback.getDownloadUrls()){

           }
        }
        return tasks;
    }
}
