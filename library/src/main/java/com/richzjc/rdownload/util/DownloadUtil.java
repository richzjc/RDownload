package com.richzjc.rdownload.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import com.richzjc.rdownload.download.task.DownloadTask;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadUtil {
    public static String getDownloadFilePath(Context context, DownloadTask task){
        File file =  context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if(file == null)
            file = getExternalCacheDir(context);
        String path = TextUtils.concat(file.getAbsolutePath(), "/", String.valueOf(task.getUrl().hashCode()), getSuffix(task.getUrl())).toString();
        return path;
    }

    public static String getDownloadFileName(Context context, DownloadTask task){
        return "";
    }


    private static String getSuffix(String downloadUrl){
        String url = downloadUrl;
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String suffixes = "[\\w]+[\\.](avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|txt|html|zip|java|doc)";
        Pattern pat = Pattern.compile(suffixes);//正则判断
        Matcher mc = pat.matcher(url);//条件匹配
        String suffix = "";
        while (mc.find()) {
            suffix = mc.group();//截取文件名后缀名
        }
        if(!TextUtils.isEmpty(suffix)){
            return suffix.substring(suffix.lastIndexOf("."));
        }else{
            return ".temp";
        }
    }

    public static File getExternalCacheDir(Context context) {
        return new File(Environment.getExternalStorageDirectory().getPath() + ("/Android/data/" + context.getPackageName() + "/download"));
    }


    public static void updateDownloadState(ParentTaskCallback obj, int progress, int downloadStatus){
        if(obj != null){
            obj.progress = progress;
            obj.status = downloadStatus;
        }
        Log.i("status", obj.toString());
    }

}
