package com.richzjc.rdownload.db.helper;


import com.richzjc.rdownload.notification.callback.ParentTaskCallback;

// 规范所有得数据库操作
public interface IBaseDao<T extends ParentTaskCallback> {

    // 插入数据
    void insert(String configurationKey, T bean);
}
