package com.richzjc.rdownload.model;

import com.richzjc.rdownload.callback.DownloadCompleteCallback;
import com.richzjc.rdownload.callback.GetSaveModelCallback;
import com.richzjc.rdownload.constant.NetworkType;

public class ConfigurationParamsModel {
    public NetworkType networkType;
    public DownloadCompleteCallback completeCallback;
    public GetSaveModelCallback saveModelCallback;
}
