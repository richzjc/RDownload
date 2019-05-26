package com.richzjc.rdownload.manager;

import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import com.richzjc.rdownload.manager.ThreadPoolManager;
import com.richzjc.rdownload.data.model.ConfigurationParamsModel;
import com.richzjc.rdownload.data.wrapper.DataHandleWrapper;

import java.util.ArrayList;
import java.util.List;

public class Confirguration {

    List<ParentTaskCallback> mDatas;
    List<ParentTaskCallback> pauseAndErrorList;
    ThreadPoolManager poolManager;
    DataHandleWrapper wrapper;
    public String key;

    public Confirguration(String key, ConfigurationParamsModel paramsModel) {
        this.key = key;
        mDatas = new ArrayList<>();
        poolManager = ThreadPoolManager.getInstance(key, mDatas, paramsModel);
        pauseAndErrorList = new ArrayList<>();
        wrapper = new DataHandleWrapper(key, mDatas, pauseAndErrorList);
    }

    public void addParentTask(ParentTaskCallback parentTask) {
        wrapper.addParentTask(parentTask);
    }

    public void addParentTasks(List<ParentTaskCallback> parentTasks){
        wrapper.addParentTasks(parentTasks);
    }

    public void pauseParentTask(ParentTaskCallback parentTask) {
        wrapper.pauseParentTask(parentTask);
    }

    public void pauseParentTasks(List<ParentTaskCallback> parentTasks){
        wrapper.pauseParentTasks(parentTasks);
    }

    private boolean contains(ParentTaskCallback ptc){
        return wrapper.contains(ptc);
    }
}
