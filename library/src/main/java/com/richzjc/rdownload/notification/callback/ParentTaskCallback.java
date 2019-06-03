package com.richzjc.rdownload.notification.callback;

import com.richzjc.rdownload.download.task.DownloadTask;

import java.util.List;

public abstract class ParentTaskCallback {

    int progress = 0;
    int status = 0;
    int downloadLength = 0;
    int totalLength = 0;

    public abstract List<DownloadTask> getDownloadTasks();

    public abstract String getParentTaskId();
}
