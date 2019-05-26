package com.richzjc.rdownload.notification.callback;

import com.richzjc.rdownload.data.model.ProgressStatusModel;

import java.util.List;

public interface ParentTaskCallback {

    List<String> getAllImageUris();
    List<String> getAllAudioVideoUris();
    String getParentTaskId();
    void updateProperty(ProgressStatusModel model);
}
