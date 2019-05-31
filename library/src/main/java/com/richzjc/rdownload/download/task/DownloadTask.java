package com.richzjc.rdownload.download.task;

public class DownloadTask implements IDownload {

    private String url;
    public DownloadTask(String url){
        this.url = url;
    }

    @Override
    public void run() {
        //TODO 通过okHttp去同步请求数据
        //TODO 判断数据库里面的缓存是已经存在
    }
}
