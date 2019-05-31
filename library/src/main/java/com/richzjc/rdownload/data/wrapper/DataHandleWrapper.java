package com.richzjc.rdownload.data.wrapper;

import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import java.util.List;

public class DataHandleWrapper {

    private List<ParentTaskCallback> mDatas;
    private List<ParentTaskCallback> pauseAndErrorList;
    private String key;

    public DataHandleWrapper(String key, List<ParentTaskCallback> mDatas, List<ParentTaskCallback> pauseAndErrorList){
        this.key = key;
        this.mDatas = mDatas;
        this.pauseAndErrorList = pauseAndErrorList;
    }

    public void addParentTask(ParentTaskCallback parentTask) {
        mDatas.remove(parentTask);
        pauseAndErrorList.remove(parentTask);
        mDatas.add(parentTask);
        //TODO 入库，判断数据库里面是否存在
        //TODO 标记为等待缓存， 通过注解回调回去，
        // TODO 并且更新实体类的状态，状态也根据注解，反射去更新
    }

    public void addParentTasks(List<ParentTaskCallback> parentTasks) {
        if(parentTasks != null){
            for(ParentTaskCallback taskCallback : parentTasks){
                addParentTask(taskCallback);
            }
        }
    }

    public void pauseParentTask(ParentTaskCallback parentTask) {
        if(parentTask != null){
            mDatas.remove(parentTask);
            pauseAndErrorList.remove(parentTask);
            pauseAndErrorList.add(parentTask);
            //TODO 标记为暂停状态， 并且通过注解回调回去更新界面的展示，
            // TODO 通过注解更新实体类的属性，
            //  TODO 从线程池里面暂停当前使用的这个类
        }
    }

    public void pauseParentTasks(List<ParentTaskCallback> parentTasks) {
        if(parentTasks != null){
            for(ParentTaskCallback taskCallback : parentTasks){
                pauseParentTask(taskCallback);
            }
        }
    }

    public boolean contains(ParentTaskCallback ptc) {
        return mDatas.contains(ptc) || pauseAndErrorList.contains(ptc);
    }
}
