package com.richzjc.rdownload.config;

import com.richzjc.rdownload.callback.DownloadCompleteCallback;
import com.richzjc.rdownload.callback.GetSaveModelCallback;
import com.richzjc.rdownload.callback.ParentTaskCallback;
import com.richzjc.rdownload.constant.NetworkType;
import com.richzjc.rdownload.manager.ThreadPoolManager;
import com.richzjc.rdownload.wrapper.DataHandleWrapper;
import java.util.ArrayList;
import java.util.List;

public class Confirguration {

    List<ParentTaskCallback> mDatas;
    List<ParentTaskCallback> pauseAndErrorList;
    ThreadPoolManager poolManager;
    DataHandleWrapper wrapper;
    private String key;
    private NetworkType networkType;
    private DownloadCompleteCallback completeCallback;
    private GetSaveModelCallback saveModelCallback;

    public Confirguration(String key, NetworkType networkType, DownloadCompleteCallback completeCallback, GetSaveModelCallback saveModelCallback) {
        this.key = key;
        this.networkType = networkType;
        this.completeCallback = completeCallback;
        this.saveModelCallback = saveModelCallback;
        mDatas = new ArrayList<>();
        poolManager = ThreadPoolManager.getInstance(key, mDatas);
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
