package com.richzjc.rdownload.util;

import com.richzjc.rdownload.callback.ParentTaskCallback;

import java.util.ArrayList;
import java.util.List;

public class TaskUtils {
    public static List<Runnable> getAllTasks(ParentTaskCallback taskCallback){
        List<Runnable> tasks = new ArrayList<>();
        if(taskCallback != null){
            //TODO 组织数据添加到下载队列里面去
        }
        return tasks;
    }
}
