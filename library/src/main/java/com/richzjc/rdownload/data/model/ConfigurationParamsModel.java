package com.richzjc.rdownload.data.model;

import android.content.Context;
import com.richzjc.rdownload.download.constant.NetworkType;
import com.richzjc.rdownload.manager.Confirguration;

public class ConfigurationParamsModel {
    public NetworkType networkType;
    public Context context;
    public Confirguration.ErrorMsgCallback msgCallback;
}
