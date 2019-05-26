package com.richzjc.rdownload.download.task;


public class AudioVideoTask implements Runnable {

    private String url;

    public AudioVideoTask(String url){
        this.url = url;
    }

    @Override
    public void run() {
//        CacheModelCallback downloadModel = getModel(url) ?: return
//        if (downloadModel.check()) {
//            downloadModel.state = DownloadConstants.DOWNLOAD_FINISH
//            return
//        }
//        if (downloadModel.downloadLength > downloadModel.totalLength) {
//            val file = File(downloadModel.sdCardFile)
//            file.delete()
//            downloadModel.downloadLength = 0
//            downloadModel.progress = 0
//            downloadModel.totalLength = 0
//        }
//        val range = "bytes=" + downloadModel.downloadLength + "-"
//        val request = Request.Builder().url(downloadModel.downloadUrl)
//                .addHeader("RANGE", range).build()
//        val response = okHttpClient!!.newCall(request).execute()
//        val responseBody = response.body()
//        if (downloadModel.state == 0) {
//            downloadModel.state = DownloadConstants.DOWNLOADING
//        }
//        if (downloadModel.totalLength == 0L) {
//            downloadModel.totalLength = responseBody!!.contentLength()
//        }
//        writeFile(downloadModel, responseBody!!.byteStream())
//        if (downloadModel.check()) {
//            downloadModel.state = DownloadConstants.DOWNLOAD_FINISH
//        }
    }
}
