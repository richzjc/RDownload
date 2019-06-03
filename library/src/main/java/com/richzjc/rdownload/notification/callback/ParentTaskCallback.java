package com.richzjc.rdownload.notification.callback;

import com.richzjc.rdownload.download.task.DownloadTask;

import java.util.List;

public interface ParentTaskCallback {

    List<DownloadTask> getDownloadUrls();
    String getParentTaskId();
}
