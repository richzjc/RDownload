package com.richzjc.rdownload.manager;

import com.richzjc.rdownload.download.constant.NetworkType;
import com.richzjc.rdownload.data.model.ConfigurationParamsModel;

public class ConfirgurationBuilder {

    private NetworkType networkType = NetworkType.WIFI;
    private String configurationKey;

    public ConfirgurationBuilder setNetWorkType(NetworkType workType) {
        this.networkType = workType;
        return this;
    }

    public ConfirgurationBuilder setConfigurationKey(String configurationKey) {
        this.configurationKey = configurationKey;
        return this;
    }

    public Confirguration build() {
        ConfigurationParamsModel paramsModel = new ConfigurationParamsModel();
        paramsModel.networkType = networkType;
        return new Confirguration(configurationKey, paramsModel);
    }
}
