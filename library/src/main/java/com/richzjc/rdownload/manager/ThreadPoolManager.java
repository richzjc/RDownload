package com.richzjc.rdownload.manager;

import com.richzjc.rdownload.download.constant.DownloadConstants;
import com.richzjc.rdownload.download.task.IDownload;
import com.richzjc.rdownload.download.task.TotalLengthTask;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import com.richzjc.rdownload.notification.rx.EventBus;
import com.richzjc.rdownload.util.DownloadUtil;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolManager {

    private static HashMap<String, ThreadPoolManager> map;
    private LinkedBlockingQueue<IDownload> queue;
    private ParentTaskCallback parentTaskCallback;
    private String configurationKey;
    private workThread workThread;

    public static ThreadPoolManager getInstance(String configurationKey) {
        if (map == null)
            map = new HashMap<>();
        if (map.containsKey(configurationKey))
            return map.get(configurationKey);
        else {
            ThreadPoolManager poolManager = new ThreadPoolManager(configurationKey);
            map.put(configurationKey, poolManager);
            return poolManager;
        }
    }

    private ThreadPoolManager(String configurationKey) {
        this.configurationKey = configurationKey;
        queue = new LinkedBlockingQueue<>();
    }

    private void saveCurrentDownloadStatus() {
        if (parentTaskCallback != null) {
            if(parentTaskCallback.downloadLength == parentTaskCallback.totalLength){
                DownloadUtil.updateDownloadState(parentTaskCallback, parentTaskCallback.progress, DownloadConstants.DOWNLOAD_FINISH);
                EventBus.getInstance().postProgress(configurationKey, parentTaskCallback);
                int size = RDownloadManager.getInstance().getConfiguration(configurationKey).getDownloadSize();
                EventBus.getInstance().postSizeChange(configurationKey, size);
            }else if(parentTaskCallback.status != DownloadConstants.DOWNLOAD_PAUSE){
                DownloadUtil.updateDownloadState(parentTaskCallback, parentTaskCallback.progress, DownloadConstants.DOWNLOAD_ERROR);
                EventBus.getInstance().postProgress(configurationKey, parentTaskCallback);
            }else{
                DownloadUtil.updateDownloadState(parentTaskCallback, parentTaskCallback.progress, DownloadConstants.DOWNLOAD_PAUSE);
                EventBus.getInstance().postProgress(configurationKey, parentTaskCallback);
            }
        }
    }


    public void addNextTask() {
        try {
            Configuration configuration = RDownloadManager.getInstance().getConfiguration(configurationKey);
            this.parentTaskCallback = configuration.mDatas.take();
            DownloadUtil.updateDownloadState(parentTaskCallback, parentTaskCallback.progress, DownloadConstants.DOWNLOADING);
            EventBus.getInstance().postProgress(configurationKey, parentTaskCallback);
            new TotalLengthTask(parentTaskCallback).run(configurationKey);
            queue.addAll(parentTaskCallback.getDownloadTasks());
        } catch (Exception e) {
            e.printStackTrace();
            saveCurrentDownloadStatus();
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
                if (queue.isEmpty()) {
                    saveCurrentDownloadStatus();
                    parentTaskCallback = null;
                    addNextTask();
                }
                try {
                    IDownload runnable = queue.take();
                    runnable.run(configurationKey);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
