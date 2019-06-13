package com.richzjc.rdownload.manager;

import android.content.ContentValues;
import android.content.Context;
import com.richzjc.rdownload.data.model.ConfigurationParamsModel;
import com.richzjc.rdownload.db.helper.BaseDaoFactory;
import com.richzjc.rdownload.download.constant.NetworkType;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 下载的单例类
 */
public class RDownloadManager {
    private static volatile RDownloadManager instance;
    private HashMap<String, Configuration> map;

    public static RDownloadManager getInstance() {
        if (instance == null) {
            synchronized (RDownloadManager.class) {
                // 第二次检查
                if (instance == null) {
                    instance = new RDownloadManager();
                }
            }
        }
        return instance;
    }

    private RDownloadManager() {
        map = new HashMap<>();
    }

    public Builder initBuilder() {
        return new Builder();
    }


    public void addParentTask(String configurationKey, ParentTaskCallback taskCallback) {
        Configuration configuration = map.get(configurationKey);
        if (configuration != null)
            configuration.addParentTask(taskCallback);
    }

    public List<ParentTaskCallback> getAllData(String configurationKey) {
        Configuration configuration = map.get(configurationKey);
        if (configuration != null)
            return configuration.getAllData();
        else
            return new ArrayList<>();
    }

    public void pauseParentTask(String configurationKey, ParentTaskCallback parentTaskCallback) {
        Configuration configuration = map.get(configurationKey);
        if (configuration != null)
            configuration.pauseParentTask(parentTaskCallback);
    }

    public void insert(String configurationKey, ParentTaskCallback taskCallback){
        Configuration configuration = map.get(configurationKey);
        if(configuration != null){
            BaseDaoFactory.getInstance(configuration.paramsModel.context).getBaseDao(taskCallback.getClass().getName()).insert(configurationKey, taskCallback);
        }
    }

    public void update(String configurationKey, ParentTaskCallback parentTaskCallback) {
        Configuration configuration = map.get(configurationKey);
        if(configuration != null){
            Context context = configuration.paramsModel.context;
            ContentValues values = new ContentValues();
            values.put("progress", parentTaskCallback.progress);
            values.put("status", parentTaskCallback.status);
            values.put("totalLength", parentTaskCallback.totalLength);
            values.put("downloadLength", parentTaskCallback.downloadLength);
            BaseDaoFactory.getInstance(context).getBaseDao(parentTaskCallback.getClass().getName()).update(values, "configurationKey = ? and parentTaskId = ?", new String[]{configurationKey, parentTaskCallback.getParentTaskId()});
        }
    }


    public ParentTaskCallback getCurrentDownloadPTask(String configurationKey){
        Configuration configuration = map.get(configurationKey);
        if(configuration != null){
            return configuration.poolManager.getDownloadParentTask();
        }else{
            return null;
        }
    }

    public ConfigurationParamsModel getConfigurationParamsModel(String configurationKey){
        Configuration configuration = map.get(configurationKey);
        if(configuration != null){
            return configuration.paramsModel;
        }else{
            return null;
        }
    }

    public int getDownloadSize(String configurationKey) {
        Configuration configuration = map.get(configurationKey);
        if(configuration != null){
            return configuration.getDownloadSize();
        }
        return 0;
    }

    private void setConfiguration(Configuration configuration) {
        if (map.containsKey(configuration.key)) {
            map.remove(configuration.key);
            map.put(configuration.key, configuration);
        } else {
            map.put(configuration.key, configuration);
        }
    }

    public static final class Builder {
        private NetworkType networkType = NetworkType.WIFI;
        private String configurationKey;

        public Builder setNetWorkType(NetworkType workType) {
            this.networkType = workType;
            return this;
        }

        public Builder setConfigurationKey(String configurationKey) {
            this.configurationKey = configurationKey;
            return this;
        }

        public void build(Context context) {
            if (context == null)
                throw new IllegalStateException("context 不能为空");
            ConfigurationParamsModel paramsModel = new ConfigurationParamsModel();
            paramsModel.context = context.getApplicationContext();
            paramsModel.networkType = networkType;
            RDownloadManager.getInstance().setConfiguration(new Configuration(configurationKey, paramsModel));
        }
    }
}
