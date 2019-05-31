package com.richzjc.rdownload.manager;

import com.richzjc.rdownload.download.constant.NetworkType;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import com.richzjc.rdownload.data.model.ConfigurationParamsModel;
import com.richzjc.rdownload.data.wrapper.DataHandleWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类是一个配置项，
 * 比方说，下载音频和下载视频要分成两个下载队列，
 * 那么这个configuration就需要分开
 * 每一个configuration都有对应的一个key
 */
public class Confirguration {

   private ThreadPoolManager poolManager;
   private DataHandleWrapper wrapper;
   private ConfigurationParamsModel paramsModel;

    public Confirguration(String key, ConfigurationParamsModel paramsModel) {
        this.paramsModel = paramsModel;
        List<ParentTaskCallback> mDatas = new ArrayList<>();
        poolManager = ThreadPoolManager.getInstance(key, mDatas, paramsModel);
        List<ParentTaskCallback> pauseAndErrorList = new ArrayList<>();
        wrapper = new DataHandleWrapper(key, mDatas, pauseAndErrorList);
    }

    public void addParentTask(ParentTaskCallback parentTask) {
        wrapper.addParentTask(parentTask);
    }

    public void addParentTasks(List<ParentTaskCallback> parentTasks) {
        wrapper.addParentTasks(parentTasks);
    }

    public void pauseParentTask(ParentTaskCallback parentTask) {
        wrapper.pauseParentTask(parentTask);
    }

    public void pauseParentTasks(List<ParentTaskCallback> parentTasks) {
        wrapper.pauseParentTasks(parentTasks);
    }

    private boolean contains(ParentTaskCallback ptc) {
        return wrapper.contains(ptc);
    }

    public void setNetWorkType(NetworkType netWorkType) {
        //TODO 根据这个networkType 如果不符合条件 则要暂停去下载
        paramsModel.networkType = netWorkType;

    }
}
