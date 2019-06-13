package com.richzjc.rdownload.manager;

import java.util.HashMap;

/**
 *下载的单例类
 */
public class RDownloadManager {
    private static volatile RDownloadManager instance;
    private HashMap<String, Configuration> map;

    public static RDownloadManager getInstance(){
        if (instance == null) {
            synchronized (RDownloadManager.class) {
                // 第二次检查
                if (instance == null) {
                    instance = new RDownloadManager();
                }
            }
        }
        return instance;
    }

    private RDownloadManager(){
        map = new HashMap<>();
    }

    public Configuration getConfiguration(String key){
        if(map.containsKey(key)){
            return map.get(key);
        }else{
           return null;
        }
    }

    public void setConfiguration(Configuration configuration){
        if(map.containsKey(configuration.key)){
            map.remove(configuration.key);
            map.put(configuration.key, configuration);
        }else{
            map.put(configuration.key, configuration);
        }
    }
}
