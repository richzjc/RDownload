package com.richzjc.rdownload.manager;

import com.richzjc.rdownload.download.constant.NetworkType;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import com.richzjc.rdownload.data.model.ConfigurationParamsModel;
import com.richzjc.rdownload.data.wrapper.DataHandleWrapper;
import com.richzjc.rdownload.util.TDevice;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 该类是一个配置项，
 * 比方说，下载音频和下载视频要分成两个下载队列，
 * 那么这个configuration就需要分开
 * 每一个configuration都有对应的一个key
 */
public class Configuration {

    private List<ParentTaskCallback> pauseAndErrorList;
    public ThreadPoolManager poolManager;
    private DataHandleWrapper wrapper;
    public ConfigurationParamsModel paramsModel;
    public String key;
    LinkedBlockingQueue<ParentTaskCallback> mDatas;

    public Configuration(String key, ConfigurationParamsModel paramsModel) {
        this.key = key;
        this.paramsModel = paramsModel;
        mDatas = new LinkedBlockingQueue<>();
        pauseAndErrorList = new ArrayList<>();
        wrapper = new DataHandleWrapper(key, mDatas, pauseAndErrorList);
        poolManager = new ThreadPoolManager(key, mDatas);
    }

    public void addParentTask(ParentTaskCallback parentTask) {
        wrapper.addParentTask(parentTask);
        poolManager.start();
    }

    public void addParentTasks(List<ParentTaskCallback> parentTasks) {
        wrapper.addParentTasks(parentTasks);
        poolManager.start();
    }

    public void pauseParentTask(ParentTaskCallback parentTask) {
        wrapper.pauseParentTask(parentTask);
    }

    public void pauseAll() {
        ParentTaskCallback callback = poolManager.getDownloadParentTask();
        if (callback != null) {
            pauseParentTask(callback);
        }
        pauseParentTasks(mDatas);
    }

    public void startAll() {
        addParentTasks(pauseAndErrorList);
    }

    public void pauseParentTasks(Collection<ParentTaskCallback> parentTasks) {
        wrapper.pauseParentTasks(parentTasks);
    }

    private boolean contains(ParentTaskCallback ptc) {
        return wrapper.contains(ptc);
    }

    public void setNetWorkType(NetworkType netWorkType) {
        paramsModel.networkType = netWorkType;
        if (netWorkType == NetworkType.WIFI) {
            if (TDevice.isConnectWIFI(paramsModel.context))
                poolManager.start();
            else
                pauseAll();
        }
    }

    public List<ParentTaskCallback> getAllData() {
        List<ParentTaskCallback> list = new ArrayList<>();
        if (poolManager.getDownloadParentTask() != null)
            list.add(poolManager.getDownloadParentTask());
        list.addAll(mDatas);
        list.addAll(pauseAndErrorList);
        return list;
    }

    public int getDownloadSize() {
        return mDatas.size() + pauseAndErrorList.size();
    }
}
