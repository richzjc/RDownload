package com.richzjc.rdownload.db.helper;


// 规范所有得数据库操作
public interface IBaseDao<T> {

    // 插入数据
    void insert(String configurationKey, T bean);

}
