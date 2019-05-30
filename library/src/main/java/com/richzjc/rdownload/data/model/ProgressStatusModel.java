package com.richzjc.rdownload.data.model;

import android.os.Parcel;
import android.os.Parcelable;

//TODO 状态属性的更新 希望能通过注解反射去修改， 这个类到时候就直接干掉
public class ProgressStatusModel implements Parcelable {
    public String parentTaskId;
    public int progress;
    public int downloadStatus;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parentTaskId);
        dest.writeInt(this.progress);
        dest.writeInt(this.downloadStatus);
    }

    public ProgressStatusModel() {
    }

    protected ProgressStatusModel(Parcel in) {
        this.parentTaskId = in.readString();
        this.progress = in.readInt();
        this.downloadStatus = in.readInt();
    }

    public static final Parcelable.Creator<ProgressStatusModel> CREATOR = new Parcelable.Creator<ProgressStatusModel>() {
        @Override
        public ProgressStatusModel createFromParcel(Parcel source) {
            return new ProgressStatusModel(source);
        }

        @Override
        public ProgressStatusModel[] newArray(int size) {
            return new ProgressStatusModel[size];
        }
    };
}
