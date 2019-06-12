package com.richzjc.rdownload;

import android.os.Parcel;
import android.os.Parcelable;
import com.richzjc.rdownload.db.anotations.DbTable;
import com.richzjc.rdownload.download.task.DownloadTask;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import java.util.ArrayList;
import java.util.List;

@DbTable(value = "test_download_model")
public class ParentTaskModel extends ParentTaskCallback implements Parcelable {

    @Override
    public List<DownloadTask> getDownloadTasks() {
        List<DownloadTask> list = new ArrayList<>();
        list.add(new DownloadTask("https://premium.wallstcn.com/3a61745a-2a79-41d1-9c10-a56a899c0b4e.mp3", "第一个"));
        list.add(new DownloadTask("https://wpimg.wallstcn.com/7085964f-3ba9-47d5-9b71-0157596563a7.png?imageMogr2/auto-orient/thumbnail/901/format/webp/size-limit/15k!", ""));
        list.add(new DownloadTask("https://wpimg.wallstcn.com/2caf2006-5da2-4fe2-aa3f-887075719c5c.png?imageMogr2/auto-orient/thumbnail/901/format/webp/size-limit/15k!", ""));
        list.add(new DownloadTask("https://wpimg.wallstcn.com/cddfea7c-397a-4116-8b20-c2ce25e3f452.png?imageMogr2/auto-orient/thumbnail/901/format/webp/size-limit/15k!", ""));
        list.add(new DownloadTask("https://wpimg.wallstcn.com/f9b20d01-4285-467e-b4b3-7ef5b29a90b6.png?imageMogr2/auto-orient/thumbnail/901/format/webp/size-limit/15k!", ""));
        return list;
    }

    @Override
    public String getParentTaskId() {
        return "testId";
    }

    @Override
    public String toString() {
        return "progress = " + progress + "; state = " + status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.progress);
        dest.writeInt(this.status);
    }

    public ParentTaskModel() {
    }

    protected ParentTaskModel(Parcel in) {
        this.progress = in.readInt();
        this.status = in.readInt();
    }

    public static final Parcelable.Creator<ParentTaskModel> CREATOR = new Parcelable.Creator<ParentTaskModel>() {
        @Override
        public ParentTaskModel createFromParcel(Parcel source) {
            return new ParentTaskModel(source);
        }

        @Override
        public ParentTaskModel[] newArray(int size) {
            return new ParentTaskModel[size];
        }
    };
}
