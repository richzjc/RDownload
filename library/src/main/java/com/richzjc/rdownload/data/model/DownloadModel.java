package com.richzjc.rdownload.data.model;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadModel {
    private String downloadUrl = "";
    private String downloadFolder = Environment.getExternalStorageDirectory().getPath() + "/wallstreetcn/";
    private String sdFile;
    private String fileName;
    private String suffixName;

    private int progress;
    private int state;
    private int totalLength;
    private long downloadLength;

    private String sdCardFile;

//    get() {
//        if (TextUtils.isEmpty(sdFile)) {
//            sdFile = downloadFolder + downloadUrl.hashCode() + getSuffix(downloadUrl)
//        }
//        return sdFile
//    }

    private void setSuffixName(String suffixName) {
        this.suffixName = suffixName;
    }

    private void setDownloadFolder(String downloadFolder) {
        this.downloadFolder = downloadFolder;
    }

    public void addDownloadLength(long length) {
        this.downloadLength += length;
        int curProgress = Math.round(downloadLength * 100f / totalLength);
        if (curProgress != progress) {
            progress = curProgress;
        }
    }

    private long getDownloadLength() {
        try {
            File file = new File(sdCardFile);
            if (!file.exists())
                downloadLength = 0;
            else
                downloadLength = file.length();
            return downloadLength;
        } catch (Exception e) {
            e.printStackTrace();
            return downloadLength;
        }
    }

    private String getSuffix(String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String suffixes = "avi|mpeg|3gp|mp3|mp4|wav|jpeg|gif|jpg|png|apk|exe|txt|html|zip|java|doc";
        Pattern pat = Pattern.compile("[\\w]+[\\.]($suffixes)");//正则判断
        Matcher mc = pat.matcher(url);//条件匹配
        String suffix = "";
        while (mc.find()) {
            suffix = mc.group();//截取文件名后缀名
        }
        if (!TextUtils.isEmpty(suffix)) {
            if (TextUtils.isEmpty(fileName))
                fileName = suffix.substring(0, suffix.indexOf("."));
            suffix = suffix.substring(suffix.indexOf("."));
        } else {
            if (TextUtils.isEmpty(fileName))
                fileName = "未知文件";
            suffix = ".temp";
        }
        if (TextUtils.isEmpty(suffixName)) {
            return suffix;
        } else {
            return suffixName;
        }
    }

    public boolean check() {
        return getDownloadLength() == totalLength && totalLength != 0L;
    }

    private void setDownloadLength(long downloadLength) {
        this.downloadLength = downloadLength;
    }

    private void deleteFile() {
        File file = new File(sdCardFile);
        file.delete();
    }
}
