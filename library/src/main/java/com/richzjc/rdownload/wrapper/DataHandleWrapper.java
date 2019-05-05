package com.richzjc.rdownload.wrapper;

import com.richzjc.rdownload.callback.ParentTaskCallback;
import java.util.List;

public class DataHandleWrapper {

    private List<ParentTaskCallback> mDatas;
    private List<ParentTaskCallback> pauseAndErrorList;

    public DataHandleWrapper(List<ParentTaskCallback> mDatas, List<ParentTaskCallback> pauseAndErrorList){
        this.mDatas = mDatas;
        this.pauseAndErrorList = pauseAndErrorList;
    }

    public void addParentTask(ParentTaskCallback parentTask) {
        mDatas.remove(parentTask);
        pauseAndErrorList.remove(parentTask);
        mDatas.add(parentTask);
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
