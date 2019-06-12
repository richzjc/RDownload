package com.richzjc.rdownload.db.helper;


import java.sql.ResultSet;

// 规范所有得数据库操作
public interface IBaseDao<T> {

    // 插入数据
    long insert(String configurationKey, T bean);

}
