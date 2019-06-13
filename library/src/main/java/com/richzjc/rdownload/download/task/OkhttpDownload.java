package com.richzjc.rdownload.download.task;

import android.util.Log;
import com.richzjc.rdownload.data.model.ConfigurationParamsModel;
import com.richzjc.rdownload.download.constant.DownloadConstants;
import com.richzjc.rdownload.manager.Configuration;
import com.richzjc.rdownload.manager.RDownloadManager;
import com.richzjc.rdownload.manager.ThreadPoolManager;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import com.richzjc.rdownload.notification.rx.EventBus;
import com.richzjc.rdownload.util.DownloadUtil;
import com.richzjc.rdownload.util.FileUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import java.io.*;
import java.util.List;

final class OkhttpDownload {

    private static volatile OkhttpDownload instance;
    private OkHttpClient okHttpClient;

    public static OkhttpDownload getInstance() {
        if (instance == null) {
            synchronized (OkhttpDownload.class) {
                if (instance == null)
                    instance = new OkhttpDownload();
            }
        }
        return instance;
    }

    private OkhttpDownload() {
        okHttpClient = new OkHttpClient.Builder().build();
    }

    public void download(String configurationKey, DownloadTask task) {
        ConfigurationParamsModel model = RDownloadManager.getInstance().getConfigurationParamsModel(configurationKey);
        if(model == null)
            return;
        File file = new File(DownloadUtil.getDownloadFilePath(model.context, task));
        if (!FileUtil.createFile(file.getAbsolutePath()))
            return;
        String range = "bytes=" + file.length() + "-";
        String url = task.getUrl();
        Request request = new Request.Builder().url(url)
                .addHeader("RANGE", range)
                .addHeader("Connection", "close")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody body = response.body();
            Log.i("body", body.contentType().toString());
            writeFiles(configurationKey, body, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeFiles(String configurationKey, ResponseBody body, File file) {
        ParentTaskCallback taskCallback = RDownloadManager.getInstance().getCurrentDownloadPTask(configurationKey);
        try {
            RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
            accessFile.seek(0);
            int readCount;
            int len = 1024;
            byte[] buffer = new byte[len];
            BufferedInputStream bufferedInputStream = new BufferedInputStream(body.byteStream(), len);
            do {
                readCount = bufferedInputStream.read(buffer);
                if (readCount != -1) {
                    if (taskCallback.status == DownloadConstants.DOWNLOADING) {
                        accessFile.write(buffer, 0, readCount);
                        taskCallback.downloadLength += readCount;
                        taskCallback.updateProgress();
                        DownloadUtil.updateDownloadState(taskCallback, taskCallback.progress, DownloadConstants.DOWNLOADING);
                        EventBus.getInstance().postProgress(configurationKey, taskCallback);
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            } while (true);
            accessFile.close();
            bufferedInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getTaskTotalLength(String configurationKey, ParentTaskCallback ptc) {
        ptc.totalLength = 0;
        long downloadLength = 0;
        List<DownloadTask> list = ptc.getDownloadTasks();
        for (DownloadTask task : list) {
            ptc.totalLength += getLengthByUrl(task);
            downloadLength += getDownloadLength(configurationKey, task);
        }
        if (downloadLength != ptc.downloadLength)
            ptc.downloadLength = downloadLength;
    }

    private long getDownloadLength(String configurationKey, DownloadTask task) {
        ConfigurationParamsModel model = RDownloadManager.getInstance().getConfigurationParamsModel(configurationKey);
        File file = new File(DownloadUtil.getDownloadFilePath(model.context, task));
        if (file.exists())
            return file.length();
        else
            return 0;
    }

    private long getLengthByUrl(DownloadTask task) {
        String url = task.getUrl();
        Request request = new Request.Builder().url(url)
                .addHeader("RANGE", "0")
                .addHeader("Connection", "close")
                .head()
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String length = response.header("content-length", "0");
            Log.i("download", "total_length = " + length);
            return Long.parseLong(length);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
