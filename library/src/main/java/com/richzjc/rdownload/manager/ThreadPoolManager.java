package com.richzjc.rdownload.manager;

import java.util.HashMap;
import java.util.concurrent.*;

public class ThreadPoolManager {

    private static HashMap<String, ThreadPoolManager> map;
    private LinkedBlockingQueue<Runnable> queue;
    private ThreadPoolExecutor poolExecutor;

    public static ThreadPoolManager getInstance(String configurationKey) {
        if (map == null)
            map = new HashMap<>();
        if (map.containsKey(configurationKey))
            return map.get(configurationKey);
        else {
            ThreadPoolManager poolManager = new ThreadPoolManager();
            map.put(configurationKey, poolManager);
            return poolManager;
        }
    }

    private ThreadPoolManager() {
        queue = new LinkedBlockingQueue<>();
        poolExecutor = new ThreadPoolExecutor(1, 2, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                addTask(r);
            }
        });
        init();

    }

    private void init() {
        Runnable coreRunnable = new Runnable() {
            Runnable runnable = null;

            @Override
            public void run() {
                while (true) {
                    try {
                        runnable = queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (runnable != null)
                        poolExecutor.execute(runnable);
                }
            }
        };
        poolExecutor.execute(coreRunnable);
    }


    public void addTask(Runnable runnable) {
        if (runnable != null) {
            try {
                queue.put(runnable);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
