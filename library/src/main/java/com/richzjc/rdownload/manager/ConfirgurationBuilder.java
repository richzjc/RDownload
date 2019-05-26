package com.richzjc.rdownload.manager;

import com.richzjc.rdownload.notification.callback.DownloadCompleteCallback;
import com.richzjc.rdownload.notification.callback.GetSaveModelCallback;
import com.richzjc.rdownload.download.constant.NetworkType;
import com.richzjc.rdownload.data.model.ConfigurationParamsModel;

public class ConfirgurationBuilder {

    private NetworkType networkType = NetworkType.WIFI;
    private String configurationKey;
    private DownloadCompleteCallback completeCallback;
    private GetSaveModelCallback saveModelCallback;

    public ConfirgurationBuilder setNetWorkType(NetworkType workType) {
        this.networkType = workType;
        return this;
    }

    public ConfirgurationBuilder setConfigurationKey(String configurationKey) {
        this.configurationKey = configurationKey;
        return this;
    }

    public ConfirgurationBuilder setDownloadCompleteCallback(DownloadCompleteCallback completeCallback) {
        this.completeCallback = completeCallback;
        return this;
    }

    public ConfirgurationBuilder setGetSaveModelCallback(GetSaveModelCallback saveModelCallback){
        this.saveModelCallback = saveModelCallback;
        return this;
    }

    public Confirguration build() {
        ConfigurationParamsModel paramsModel = new ConfigurationParamsModel();
        paramsModel.networkType = networkType;
        paramsModel.completeCallback = completeCallback;
        paramsModel.saveModelCallback = saveModelCallback;
        return new Confirguration(configurationKey, paramsModel);
    }
}
