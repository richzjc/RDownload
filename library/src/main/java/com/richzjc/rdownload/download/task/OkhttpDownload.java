package com.richzjc.rdownload.download.task;

import android.os.Environment;
import android.util.Log;
import com.richzjc.rdownload.util.FileUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.*;

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

    public void download(String url) {
        Request request = new Request.Builder().url(url)
                .addHeader("RANGE", "0")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody body = response.body();
            Log.i("body", body.contentType().toString());
            writeFiles(body, url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeFiles(ResponseBody body, String url) {
        File file = new File(Environment.getExternalStorageDirectory(), String.valueOf(url.hashCode()));
        if (!FileUtil.createFile(file.getAbsolutePath())) ;
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
}
