package com.richzjc.rdownload.manager;

import android.content.Context;
import android.util.Log;
import com.richzjc.rdownload.download.constant.NetworkType;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import com.richzjc.rdownload.data.model.ConfigurationParamsModel;
import com.richzjc.rdownload.data.wrapper.DataHandleWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 该类是一个配置项，
 * 比方说，下载音频和下载视频要分成两个下载队列，
 * 那么这个configuration就需要分开
 * 每一个configuration都有对应的一个key
 */
public class Confirguration {

    private ThreadPoolManager poolManager;
    private DataHandleWrapper wrapper;
    public ConfigurationParamsModel paramsModel;
    public String key;
    LinkedBlockingQueue<ParentTaskCallback> mDatas;

    private Confirguration(String key, ConfigurationParamsModel paramsModel) {
        this.key = key;
        this.paramsModel = paramsModel;
        mDatas = new LinkedBlockingQueue<>();
        Log.i("download", "configuration constructor");
        List<ParentTaskCallback> pauseAndErrorList = new ArrayList<>();
        wrapper = new DataHandleWrapper(key, mDatas, pauseAndErrorList);
    }

    public void addParentTask(ParentTaskCallback parentTask) {
        wrapper.addParentTask(parentTask);
    }

    public void addParentTasks(List<ParentTaskCallback> parentTasks) {
        wrapper.addParentTasks(parentTasks);
    }

    public void pauseParentTask(ParentTaskCallback parentTask) {
        wrapper.pauseParentTask(parentTask);
    }

    public void pauseParentTasks(List<ParentTaskCallback> parentTasks) {
        wrapper.pauseParentTasks(parentTasks);
    }

    private boolean contains(ParentTaskCallback ptc) {
        return wrapper.contains(ptc);
    }

    public void setNetWorkType(NetworkType netWorkType) {
        //TODO 根据这个networkType 如果不符合条件 则要暂停去下载
        paramsModel.networkType = netWorkType;
    }

    public void start() {
        if (poolManager == null)
            poolManager = ThreadPoolManager.getInstance(key);
    }

    public static final class ConfirgurationBuilder {

        private NetworkType networkType = NetworkType.WIFI;
        private String configurationKey;
        private ErrorMsgCallback msgCallback;

        public ConfirgurationBuilder setNetWorkType(NetworkType workType) {
            this.networkType = workType;
            return this;
        }

        public ConfirgurationBuilder setConfigurationKey(String configurationKey) {
            this.configurationKey = configurationKey;
            return this;
        }

        public ConfirgurationBuilder setErrorMsgCallback(ErrorMsgCallback callback) {
            this.msgCallback = callback;
            return this;
        }


        public Confirguration build(Context context) {
            if (context == null)
                throw new IllegalStateException("context 不能为空");
            ConfigurationParamsModel paramsModel = new ConfigurationParamsModel();
            paramsModel.context = context.getApplicationContext();
            paramsModel.networkType = networkType;
            paramsModel.msgCallback = msgCallback;
            return new Confirguration(configurationKey, paramsModel);
        }
    }

    // 当有错误 信息的时候 回调到上层页面进行处理进行上报操作
    public interface ErrorMsgCallback {
        void downLoadError(String msgs);
    }
}
