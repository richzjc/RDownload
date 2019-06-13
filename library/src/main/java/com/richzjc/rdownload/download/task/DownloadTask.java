package com.richzjc.rdownload.download.task;

import com.richzjc.rdownload.data.model.ConfigurationParamsModel;
import com.richzjc.rdownload.download.constant.NetworkType;
import com.richzjc.rdownload.manager.Configuration;
import com.richzjc.rdownload.manager.RDownloadManager;
import com.richzjc.rdownload.util.TDevice;

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
        ConfigurationParamsModel paramsModel = RDownloadManager.getInstance().getConfiguration(configurationKey).paramsModel;
        if(paramsModel.networkType == NetworkType.WIFI){
            if(TDevice.isConnectWIFI(paramsModel.context))
                OkhttpDownload.getInstance().download(configurationKey,this);
        }else{
            OkhttpDownload.getInstance().download(configurationKey,this);
        }
    }
}
