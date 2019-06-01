package com.richzjc.rdownload;

import android.text.TextUtils;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import com.richzjc.rdownload.data.model.ProgressStatusModel;
import java.util.ArrayList;
import java.util.List;

public class ParentTaskModel implements ParentTaskCallback {
    @Override
    public List<String> getDownloadUrls() {
        List<String> list = new ArrayList<>();
        list.add("https://premium.wallstcn.com/3a61745a-2a79-41d1-9c10-a56a899c0b4e.mp3");
        list.add("http://k.zol-img.com.cn/sjbbs/7692/a7691515_s.jpg");
        return list;
    }

    @Override
    public String getParentTaskId() {
        return "testId";
    }

    @Override
    public void updateProperty(ProgressStatusModel model) {

    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ParentTaskModel) {
            return TextUtils.equals(getParentTaskId(), ((ParentTaskModel) obj).getParentTaskId());
        }
        return super.equals(obj);
    }
}
