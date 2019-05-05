package com.richzjc.rdownload.manager;

import com.richzjc.rdownload.config.Confirguration;

import java.util.HashMap;

public class RDownloadManager {
    private static volatile RDownloadManager instance;
    private HashMap<String, Confirguration> map;

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

    public Confirguration getConfiguration(String key){
        if(map.containsKey(key)){
            return map.get(key);
        }else{
           return null;
        }
    }

    public void setConfiguration(String configurationKey, Confirguration confirguration){
        if(map.containsKey(configurationKey)){
            map.remove(configurationKey);
            map.put(configurationKey, confirguration);
        }else{
            map.put(configurationKey, confirguration);
        }
    }
}
