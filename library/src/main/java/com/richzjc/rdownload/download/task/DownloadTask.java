package com.richzjc.rdownload.download.task;

public class DownloadTask implements IDownload {

    private String url;
    private String fileName;
    public DownloadTask(String url, String fileName){
        this.url = url;
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public void run(String configurationKey) {
        //TODO 判断当前的网络是否能去执行下载的逻辑
        //TODO 通过okHttp去同步请求数据
        //TODO 判断数据库里面的缓存是已经存在
        OkhttpDownload.getInstance().download(configurationKey,this);
    }
}
