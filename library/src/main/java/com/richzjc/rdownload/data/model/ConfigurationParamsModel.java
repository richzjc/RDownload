package com.richzjc.rdownload.data.model;

import com.richzjc.rdownload.notification.callback.DownloadCompleteCallback;
import com.richzjc.rdownload.notification.callback.GetSaveModelCallback;
import com.richzjc.rdownload.download.constant.NetworkType;

public class ConfigurationParamsModel {
    public NetworkType networkType;
    public DownloadCompleteCallback completeCallback;
    public GetSaveModelCallback saveModelCallback;
}
