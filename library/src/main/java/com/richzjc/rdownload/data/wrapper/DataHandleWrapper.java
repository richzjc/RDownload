package com.richzjc.rdownload.data.wrapper;

import android.content.Context;
import com.richzjc.rdownload.db.helper.BaseDaoFactory;
import com.richzjc.rdownload.download.constant.DownloadConstance;
import com.richzjc.rdownload.manager.RDownloadManager;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
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
        try {
            Context context = RDownloadManager.getInstance().getConfiguration(key).paramsModel.context;
            Class cls = Class.forName(parentTask.getClass().getName());
            BaseDaoFactory.getInstance(context).getBaseDao(cls).insert(key, parentTask);

            mDatas.remove(parentTask);
            pauseAndErrorList.remove(parentTask);
            mDatas.add(parentTask);
            //TODO 入库，判断数据库里面是否存在

            //TODO 标记为等待缓存， 通过注解回调回去，
            DownloadUtil.updateDownloadState(parentTask, parentTask.progress, DownloadConstance.WAITING);
            // TODO 并且更新实体类的状态，状态也根据注解，反射去更新
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
            //TODO 标记为暂停状态， 并且通过注解回调回去更新界面的展示，
            // TODO 通过注解更新实体类的属性，
            DownloadUtil.updateDownloadState(parentTask, parentTask.progress, DownloadConstance.DOWNLOAD_PAUSE);
            //  TODO 从线程池里面暂停当前使用的这个类
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
