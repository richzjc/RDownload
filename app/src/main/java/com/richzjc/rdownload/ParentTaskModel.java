package com.richzjc.rdownload;

import android.text.TextUtils;
import com.richzjc.rdownload.download.task.DownloadTask;
import com.richzjc.rdownload.notification.anotation.DownloadProgress;
import com.richzjc.rdownload.notification.anotation.DownloadState;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;

import java.util.ArrayList;
import java.util.List;

public class ParentTaskModel extends ParentTaskCallback {

    @DownloadProgress
    public int progress;
    @DownloadState
    public int state;

    @Override
    public List<DownloadTask> getDownloadTasks() {
        List<DownloadTask> list = new ArrayList<>();
        list.add(new DownloadTask("https://premium.wallstcn.com/3a61745a-2a79-41d1-9c10-a56a899c0b4e.mp3", "第一个"));
        return list;
    }

    @Override
    public String getParentTaskId() {
        return "testId";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParentTaskModel) {
            return TextUtils.equals(getParentTaskId(), ((ParentTaskModel) obj).getParentTaskId());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "progress = " + progress + "; state = " + state;
    }
}
