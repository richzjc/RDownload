package com.richzjc.rdownload.data.wrapper;

import android.content.Context;
import com.richzjc.rdownload.db.helper.BaseDaoFactory;
import com.richzjc.rdownload.download.constant.DownloadConstants;
import com.richzjc.rdownload.manager.RDownloadManager;
import com.richzjc.rdownload.manager.ThreadPoolManager;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import com.richzjc.rdownload.notification.rx.EventBus;
import com.richzjc.rdownload.util.DownloadUtil;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class DataHandleWrapper {

    private LinkedBlockingQueue<ParentTaskCallback> mDatas;
    private List<ParentTaskCallback> pauseAndErrorList;
    private String key;

    public DataHandleWrapper(String key, LinkedBlockingQueue<ParentTaskCallback> mDatas, List<ParentTaskCallback> pauseAndErrorList) {
        this.key = key;
        this.mDatas = mDatas;
        this.pauseAndErrorList = pauseAndErrorList;
    }

    public void addParentTask(ParentTaskCallback parentTask) {
        RDownloadManager.getInstance().insert(key, parentTask);
        mDatas.remove(parentTask);
        pauseAndErrorList.remove(parentTask);
        mDatas.add(parentTask);
        DownloadUtil.updateDownloadState(parentTask, parentTask.progress, DownloadConstants.WAITING);
        EventBus.getInstance().postProgress(key, parentTask);
        ParentTaskCallback callback = RDownloadManager.getInstance().getCurrentDownloadPTask(key);
        int size = pauseAndErrorList.size() + mDatas.size();
        if (callback != null)
            size++;
        EventBus.getInstance().postSizeChange(key, size);
    }

    public void addParentTasks(List<ParentTaskCallback> parentTasks) {
        if (parentTasks != null) {
            for (ParentTaskCallback taskCallback : parentTasks) {
                addParentTask(taskCallback);
            }
        }
    }

    public void pauseParentTask(ParentTaskCallback parentTask) {
        if (parentTask != null) {
            mDatas.remove(parentTask);
            pauseAndErrorList.remove(parentTask);
            pauseAndErrorList.add(parentTask);
            DownloadUtil.updateDownloadState(parentTask, parentTask.progress, DownloadConstants.DOWNLOAD_PAUSE);
            EventBus.getInstance().postProgress(key, parentTask);
        }
    }

    public void pauseParentTasks(Collection<ParentTaskCallback> parentTasks) {
        if (parentTasks != null) {
            for (ParentTaskCallback taskCallback : parentTasks) {
                pauseParentTask(taskCallback);
            }
        }
    }

    public boolean contains(ParentTaskCallback ptc) {
        return mDatas.contains(ptc) || pauseAndErrorList.contains(ptc);
    }
}
