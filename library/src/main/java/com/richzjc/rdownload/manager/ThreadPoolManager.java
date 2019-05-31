package com.richzjc.rdownload.manager;

import com.richzjc.rdownload.data.model.ConfigurationParamsModel;
import com.richzjc.rdownload.download.util.TaskUtils;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolManager {

    private static HashMap<String, ThreadPoolManager> map;
    private LinkedBlockingQueue<Runnable> queue;
    private ParentTaskCallback parentTaskCallback;
    private List<ParentTaskCallback> mDatas;
    private ConfigurationParamsModel paramsModel;
    private Lock lock;
    private Condition emptyCondition;

    public static ThreadPoolManager getInstance(String configurationKey, List<ParentTaskCallback> mDatas, ConfigurationParamsModel paramsModel) {
        if (map == null)
            map = new HashMap<>();
        if (map.containsKey(configurationKey))
            return map.get(configurationKey);
        else {
            ThreadPoolManager poolManager = new ThreadPoolManager(mDatas, paramsModel);
            map.put(configurationKey, poolManager);
            return poolManager;
        }
    }

    private ThreadPoolManager(List<ParentTaskCallback> mDatas, ConfigurationParamsModel paramsModel) {
        this.mDatas = mDatas;
        this.paramsModel = paramsModel;
        queue = new LinkedBlockingQueue<>();
        this.lock = new ReentrantLock();
        this.emptyCondition = lock.newCondition();
        init();

    }

    private void init() {
        new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        lock.lock();
                        if (queue.isEmpty()) {
                            saveCurrentDownloadStatus();
                            addNextTask();
                            try {
                                emptyCondition.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        try {
                            Runnable runnable = queue.take();
                            runnable.run();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } finally {
                    lock.unlock();
                }
            }
        }.start();
    }

    private void saveCurrentDownloadStatus() {
        if(parentTaskCallback != null){
            //TODO 判断是下载完成了，还是下载失败了，两种对应逻辑的处理
            //关键点在于怎么去判断 是下载完成了 还是下载失败了，或者是暂停了
        }
    }


    public void addNextTask() {
        if (mDatas != null && mDatas.size() > 0) {
            //TODO 标记进行到下载的逻辑，则要
            //TODO 1、刷新界面，通过注解去刷新界面
            //TODO 2、更新对应实体的属性，主要有进度， 状态，通过注解就可以去完成了
            this.parentTaskCallback = mDatas.get(0);
            List<Runnable> tasks = TaskUtils.getAllTasks(parentTaskCallback);
            queue.addAll(tasks);
            emptyCondition.signalAll();
        } else {
            this.parentTaskCallback = null;
        }
    }
}
