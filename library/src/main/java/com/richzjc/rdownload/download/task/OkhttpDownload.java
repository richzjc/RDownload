package com.richzjc.rdownload.download.task;

import android.util.Log;
import com.richzjc.rdownload.manager.Configuration;
import com.richzjc.rdownload.manager.RDownloadManager;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
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
        String url = task.getUrl();
        Request request = new Request.Builder().url(url)
                .addHeader("RANGE", "0")
                .addHeader("Connection", "close")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody body = response.body();
            Log.i("body", body.contentType().toString());
            writeFiles(configurationKey, body, task);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFiles(String configurationKey, ResponseBody body, DownloadTask task) {
        //TODO 这里还包括一些暂停的代码没有写完
        Configuration configuration = RDownloadManager.getInstance().getConfiguration(configurationKey);
        File file = new File(DownloadUtil.getDownloadFilePath(configuration.paramsModel.context, task));
        if (!FileUtil.createFile(file.getAbsolutePath()))
            return;
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
                    accessFile.write(buffer, 0, readCount);
                } else {
                    break;
                }
            } while (true);
            accessFile.close();
            bufferedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getTaskTotalLength(ParentTaskCallback ptc){
        ptc.totalLength = 0;
       List<DownloadTask> list = ptc.getDownloadTasks();
       for(DownloadTask task : list){
           ptc.totalLength +=getLengthByUrl(task);
       }
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

    private boolean check(ParentTaskCallback taskCallback){
        return taskCallback.totalLength == taskCallback.downloadLength && taskCallback.totalLength != 0;
    }
}
