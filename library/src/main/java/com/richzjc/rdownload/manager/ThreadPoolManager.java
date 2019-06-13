package com.richzjc.rdownload.manager;

import com.richzjc.rdownload.download.constant.DownloadConstants;
import com.richzjc.rdownload.download.task.DownloadTask;
import com.richzjc.rdownload.download.task.TotalLengthTask;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import com.richzjc.rdownload.notification.rx.EventBus;
import com.richzjc.rdownload.util.DownloadUtil;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolManager {

    private ParentTaskCallback parentTaskCallback;
    private String configurationKey;
    private LinkedBlockingQueue<ParentTaskCallback> mDatas;
    private workThread workThread;

    public ThreadPoolManager(String configurationKey, LinkedBlockingQueue<ParentTaskCallback> mDatas) {
        this.configurationKey = configurationKey;
        this.mDatas = mDatas;
    }

    private void saveCurrentDownloadStatus() {
        if (parentTaskCallback != null) {
            if (parentTaskCallback.downloadLength == parentTaskCallback.totalLength) {
                DownloadUtil.updateDownloadState(parentTaskCallback, parentTaskCallback.progress, DownloadConstants.DOWNLOAD_FINISH);
                EventBus.getInstance().postProgress(configurationKey, parentTaskCallback);
                int size = RDownloadManager.getInstance().getDownloadSize(configurationKey);
                EventBus.getInstance().postSizeChange(configurationKey, size);
            } else if (parentTaskCallback.status != DownloadConstants.DOWNLOAD_PAUSE) {
                DownloadUtil.updateDownloadState(parentTaskCallback, parentTaskCallback.progress, DownloadConstants.DOWNLOAD_ERROR);
                EventBus.getInstance().postProgress(configurationKey, parentTaskCallback);
            } else {
                DownloadUtil.updateDownloadState(parentTaskCallback, parentTaskCallback.progress, DownloadConstants.DOWNLOAD_PAUSE);
                EventBus.getInstance().postProgress(configurationKey, parentTaskCallback);
            }

            RDownloadManager.getInstance().update(configurationKey, parentTaskCallback);
           }
    }

    public void start() {
        if (workThread == null) {
            workThread = new workThread();
            workThread.start();
        }
    }

    public ParentTaskCallback getDownloadParentTask() {
        return parentTaskCallback;
    }

    class workThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    parentTaskCallback = mDatas.take();
                    DownloadUtil.updateDownloadState(parentTaskCallback, parentTaskCallback.progress, DownloadConstants.DOWNLOADING);
                    EventBus.getInstance().postProgress(configurationKey, parentTaskCallback);
                    new TotalLengthTask(parentTaskCallback).run(configurationKey);
                    for(DownloadTask task : parentTaskCallback.getDownloadTasks()){
                        task.run(configurationKey);
                    }
                    saveCurrentDownloadStatus();
                    parentTaskCallback = null;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
