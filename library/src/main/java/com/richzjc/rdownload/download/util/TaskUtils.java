package com.richzjc.rdownload.download.util;

import com.richzjc.rdownload.download.task.DownloadTask;
import com.richzjc.rdownload.download.task.IDownload;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;

import java.util.ArrayList;
import java.util.List;

public class TaskUtils {
    public static List<IDownload> getAllTasks(ParentTaskCallback taskCallback){
        List<IDownload> tasks = new ArrayList<>();
        if(taskCallback != null){
           for(String url : taskCallback.getDownloadUrls()){
               tasks.add(new DownloadTask(url));
           }
        }
        return tasks;
    }
}
