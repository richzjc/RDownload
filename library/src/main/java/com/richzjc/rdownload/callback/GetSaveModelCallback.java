package com.richzjc.rdownload.callback;

public interface GetSaveModelCallback {
    //TODO 通过url获取缓存的模型
    CacheModelCallback getCacheModel(String url);
}
