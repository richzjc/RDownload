package com.richzjc.rdownload.builder;

import com.richzjc.rdownload.callback.DownloadCompleteCallback;
import com.richzjc.rdownload.callback.GetSaveModelCallback;
import com.richzjc.rdownload.config.Confirguration;
import com.richzjc.rdownload.constant.NetworkType;
import com.richzjc.rdownload.model.ConfigurationParamsModel;

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
