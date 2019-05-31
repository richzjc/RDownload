package com.richzjc.rdownload.download.task;

final class OkhttpDownload {

    private static volatile OkhttpDownload instance;

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

    }
}
