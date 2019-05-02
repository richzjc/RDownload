package com.richzjc.rdownload.callback;

import com.richzjc.rdownload.model.ProgressStatusModel;

import java.util.List;

public interface ParentTaskCallback {

    List<String> getAllImageUris();
    List<String> getAllAudioVideoUris();
    String getParentTaskId();
    void updateProperty(ProgressStatusModel model);
}
