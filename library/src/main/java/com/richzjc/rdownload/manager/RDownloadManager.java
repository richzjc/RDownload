package com.richzjc.rdownload.manager;

import java.util.HashMap;

/**
 *下载的单例类
 */
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

    public void setConfiguration(Confirguration confirguration){
        if(map.containsKey(confirguration.key)){
            map.remove(confirguration.key);
            map.put(confirguration.key, confirguration);
        }else{
            map.put(confirguration.key, confirguration);
        }
        confirguration.start();
    }
}
