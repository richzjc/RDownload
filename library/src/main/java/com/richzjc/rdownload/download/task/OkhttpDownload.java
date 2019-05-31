package com.richzjc.rdownload.download.task;

import okhttp3.OkHttpClient;

final class OkhttpDownload {

    private static volatile OkhttpDownload instance;
    private OkHttpClient okHttpClient;

    public OkhttpDownload getInstance(){
        if(instance == null){
            synchronized (OkhttpDownload.class){
                if(instance == null)
                    instance = new OkhttpDownload();
            }
        }
        return instance;
    }

    private OkhttpDownload(){
        okHttpClient = new OkHttpClient.Builder().build();
    }

    public void download(String url){

    }
}
