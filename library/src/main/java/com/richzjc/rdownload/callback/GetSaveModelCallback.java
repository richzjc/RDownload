package com.richzjc.rdownload.callback;

import com.richzjc.rdownload.model.DownloadModel;

public interface GetSaveModelCallback {
    //TODO 通过url获取缓存的模型
    DownloadModel getCacheModel(String url);
}
