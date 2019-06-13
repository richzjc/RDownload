package com.richzjc.rdownload;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.richzjc.rdownload.download.constant.DownloadConstants;
import com.richzjc.rdownload.manager.RDownloadManager;
import com.richzjc.rdownload.notification.anotation.ProgressSubscribe;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import com.richzjc.rdownload.notification.rx.EventBus;
import com.richzjc.rdownload.widget.ProgressWscnImageView;

import java.util.List;

public class DonwloadDetailActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ProgressWscnImageView image;
    @BindView(R.id.showState)
    TextView showState;
    @BindView(R.id.image_parent)
    RelativeLayout imageParent;
    @BindView(R.id.news_title)
    TextView newsTitle;
    @BindView(R.id.news_time)
    TextView newsTime;

    ParentTaskCallback parentTaskCallback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paid_recycler_item_downloading_article);
        ButterKnife.bind(this, this);
        List<ParentTaskCallback> list = RDownloadManager.getInstance().getAllData("test");
        if (list.size() > 0)
            parentTaskCallback = list.get(0);
        init();
        EventBus.getInstance().register(this);
        imageParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parentTaskCallback != null) {
                    if (parentTaskCallback.status == DownloadConstants.WAITING || parentTaskCallback.status == DownloadConstants.DOWNLOADING)
                        RDownloadManager.getInstance().pauseParentTask("test", parentTaskCallback);
                    else
                        RDownloadManager.getInstance().addParentTask("test", parentTaskCallback);
                }
            }
        });
    }

    private void init() {
        Log.i("download", "init");
        if (parentTaskCallback != null) {
            if (parentTaskCallback.status == DownloadConstants.DOWNLOADING) {
                showState.setText(parentTaskCallback.progress + "%");
            } else if (parentTaskCallback.status == DownloadConstants.DOWNLOAD_FINISH) {
                showState.setText("下载完成");
            } else if (parentTaskCallback.status == DownloadConstants.WAITING) {
                showState.setText("等待缓存");
            } else if (parentTaskCallback.status == DownloadConstants.DOWNLOAD_PAUSE) {
                showState.setText("暂停下载");
            } else if (parentTaskCallback.status == DownloadConstants.DOWNLOAD_ERROR) {
                showState.setText("下载失败");
            }
        }
    }

    @ProgressSubscribe(configurationKey = "test")
    private void updateProgress(ParentTaskCallback taskCallback) {
        if (TextUtils.equals(taskCallback.getParentTaskId(), parentTaskCallback.getParentTaskId())) {
            init();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getInstance().unRegister(this);
    }
}
