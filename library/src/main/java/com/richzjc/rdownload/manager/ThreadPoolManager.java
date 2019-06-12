package com.richzjc.rdownload.manager;

import android.util.Log;
import com.richzjc.rdownload.download.task.IDownload;
import com.richzjc.rdownload.download.task.TotalLengthTask;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
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
            //TODO 判断是下载完成了，还是下载失败了，两种对应逻辑的处理
            //关键点在于怎么去判断 是下载完成了 还是下载失败了，或者是暂停了
        }
    }


    public void addNextTask() {
        //TODO 标记进行到下载的逻辑，则要
        //TODO 1、刷新界面，通过注解去刷新界面
        //TODO 2、更新对应实体的属性，主要有进度， 状态，通过注解就可以去完成了
        try {
            Confirguration confirguration = RDownloadManager.getInstance().getConfiguration(configurationKey);
            this.parentTaskCallback = confirguration.mDatas.take();
            new TotalLengthTask(parentTaskCallback).run(configurationKey);
            queue.addAll(parentTaskCallback.getDownloadTasks());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if(workThread == null){
            workThread = new workThread();
            workThread.start();
        }
    }

    public void pause() {
        //
    }

    class workThread extends Thread{
        @Override
        public void run() {
            while (true) {
                if (queue.isEmpty()) {
                    saveCurrentDownloadStatus();
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
