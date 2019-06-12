package com.richzjc.rdownload.manager;

import android.content.Context;
import com.richzjc.rdownload.download.constant.NetworkType;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import com.richzjc.rdownload.data.model.ConfigurationParamsModel;
import com.richzjc.rdownload.data.wrapper.DataHandleWrapper;
import com.richzjc.rdownload.util.TDevice;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 该类是一个配置项，
 * 比方说，下载音频和下载视频要分成两个下载队列，
 * 那么这个configuration就需要分开
 * 每一个configuration都有对应的一个key
 */
public class Confirguration {

    private List<ParentTaskCallback> pauseAndErrorList;
    private ThreadPoolManager poolManager;
    private DataHandleWrapper wrapper;
    public ConfigurationParamsModel paramsModel;
    public String key;
    LinkedBlockingQueue<ParentTaskCallback> mDatas;

    private Confirguration(String key, ConfigurationParamsModel paramsModel) {
        this.key = key;
        this.paramsModel = paramsModel;
        mDatas = new LinkedBlockingQueue<>();
        pauseAndErrorList = new ArrayList<>();
        wrapper = new DataHandleWrapper(key, mDatas, pauseAndErrorList);
        poolManager = ThreadPoolManager.getInstance(key);
    }

    public void addParentTask(ParentTaskCallback parentTask) {
        wrapper.addParentTask(parentTask);
        poolManager.start();
    }

    public void addParentTasks(List<ParentTaskCallback> parentTasks) {
        wrapper.addParentTasks(parentTasks);
        poolManager.start();
    }

    public void pauseParentTask(ParentTaskCallback parentTask) {
        wrapper.pauseParentTask(parentTask);
    }

    public void pauseAll(){
        ParentTaskCallback callback = poolManager.pause();
        if(callback != null)
            pauseParentTask(callback);
        pauseParentTasks(mDatas);
    }

    public void startAll(){
        addParentTasks(pauseAndErrorList);
    }

    public void pauseParentTasks(Collection<ParentTaskCallback> parentTasks) {
        wrapper.pauseParentTasks(parentTasks);
    }

    private boolean contains(ParentTaskCallback ptc) {
        return wrapper.contains(ptc);
    }

    public void setNetWorkType(NetworkType netWorkType) {
        paramsModel.networkType = netWorkType;
        if(netWorkType == NetworkType.WIFI){
            if(TDevice.isConnectWIFI(paramsModel.context))
                poolManager.start();
            else
                pauseAll();
        }
    }

    public static final class ConfirgurationBuilder {

        private NetworkType networkType = NetworkType.WIFI;
        private String configurationKey;

        public ConfirgurationBuilder setNetWorkType(NetworkType workType) {
            this.networkType = workType;
            return this;
        }

        public ConfirgurationBuilder setConfigurationKey(String configurationKey) {
            this.configurationKey = configurationKey;
            return this;
        }

        public Confirguration build(Context context) {
            if (context == null)
                throw new IllegalStateException("context 不能为空");
            ConfigurationParamsModel paramsModel = new ConfigurationParamsModel();
            paramsModel.context = context.getApplicationContext();
            paramsModel.networkType = networkType;
            return new Confirguration(configurationKey, paramsModel);
        }
    }
}
