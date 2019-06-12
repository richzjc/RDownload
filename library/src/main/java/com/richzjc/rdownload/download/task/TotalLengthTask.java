package com.richzjc.rdownload.download.task;

import com.richzjc.rdownload.notification.callback.ParentTaskCallback;

public class TotalLengthTask implements IDownload {
    ParentTaskCallback ptc;
    public TotalLengthTask(ParentTaskCallback parentTaskCallback){
        this.ptc = parentTaskCallback;
    }
    @Override
    public void run(String configurationKey) {
        OkhttpDownload.getInstance().getTaskTotalLength(ptc);
    }
}
