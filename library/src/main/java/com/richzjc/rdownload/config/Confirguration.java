package com.richzjc.rdownload.config;

import com.richzjc.rdownload.callback.ParentTaskCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class Confirguration {

    LinkedBlockingQueue<ParentTaskCallback> queue;
    List<ParentTaskCallback> pauseAndErrorList;
    private String key;

    public Confirguration(String key) {
        this.key = key;
        queue = new LinkedBlockingQueue<>();
        pauseAndErrorList = new ArrayList<>();
    }

    public void addParentTask(ParentTaskCallback parentTask) {
        if(parentTask != null) {
            if(contains(parentTask)){

            }else{
                boolean isSuccess = queue.offer(parentTask);
                if(isSuccess){
                    //TODO 通知刷新界面
                }
            }
        }
    }

    public void pauseParentTask(String parentTaskId) {

    }

    private boolean contains(ParentTaskCallback ptc){
        if(queue.contains(ptc))
            return true;
        else if(pauseAndErrorList.contains(ptc)){
            return true;
        }else
            return false;
    }
}
