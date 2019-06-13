package com.richzjc.rdownload.notification.callback;

import com.richzjc.rdownload.download.task.DownloadTask;

import java.util.List;

public abstract class ParentTaskCallback {

    public int progress = 0;
    public int status = 0;
    public long totalLength;
    public long downloadLength;

    public abstract List<DownloadTask> getDownloadTasks();

    public abstract String getParentTaskId();
}
