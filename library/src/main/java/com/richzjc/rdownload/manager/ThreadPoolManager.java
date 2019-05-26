package com.richzjc.rdownload.manager;

import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import com.richzjc.rdownload.data.model.ConfigurationParamsModel;
import com.richzjc.rdownload.download.util.TaskUtils;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolManager {

    private static HashMap<String, ThreadPoolManager> map;
    private LinkedBlockingQueue<Runnable> queue;
    private ExecutorService poolExecutor;
    private ParentTaskCallback parentTaskCallback;
    private List<ParentTaskCallback> mDatas;
    private ConfigurationParamsModel paramsModel;

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
        poolExecutor = Executors.newSingleThreadExecutor();
        init();

    }

    private void init() {
        Runnable coreRunnable = new Runnable() {
            Runnable runnable = null;

            @Override
            public void run() {
                while (true) {
                    try {
                        int size = queue.size();
                        if(size == 0 && mDatas.size() > 0){
                            addTask(mDatas.get(0));
                        }
                        runnable = queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (runnable != null)
                        poolExecutor.execute(runnable);
                }
            }
        };
        new Thread(coreRunnable).start();
    }


    public void addTask(ParentTaskCallback taskCallback) {
        this.parentTaskCallback = taskCallback;
        List<Runnable> tasks = TaskUtils.getAllTasks(taskCallback);

//        if (runnable != null) {
//            try {
//                queue.put(runnable);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }
}
