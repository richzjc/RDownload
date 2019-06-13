package com.richzjc.rdownload.db.helper;


import android.content.ContentValues;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;

// 规范所有得数据库操作
public interface IBaseDao<T extends ParentTaskCallback> {

    // 插入数据
    void insert(String configurationKey, T bean);
    //更新数据
    void update(ContentValues values, String whereClause, String[] whereArgs);
}
