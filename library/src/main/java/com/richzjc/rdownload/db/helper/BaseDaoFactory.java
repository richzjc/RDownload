package com.richzjc.rdownload.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

// 提供给其他用户（coder）调用的类
public class BaseDaoFactory {

    private static volatile BaseDaoFactory instance;
    private Map<String, BaseDao> daoMap = new HashMap<>();

    // 数据库的存储位置
    private String path;

    public static BaseDaoFactory getInstance(Context context) {
        if (instance == null) {
            synchronized (Object.class) {
                if (instance == null)
                    instance = new BaseDaoFactory(context);
            }
        }
        return instance;
    }

    private BaseDaoFactory(Context context) {
        path = "/data/data/" + context.getPackageName() + "/database/download.db";
        // 判断路径是否存在
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        SQLiteDatabase.openOrCreateDatabase(path, null);
    }

    // 对外提供一个API
    public BaseDao getBaseDao(String name) {
        if (daoMap.containsKey(name)) {
            return daoMap.get(name);
        } else {
            BaseDao baseDao = null;
            try {
                baseDao = BaseDao.class.newInstance();
                baseDao.init(path, Class.forName(name));
            } catch (Exception e) {
                e.printStackTrace();
            }
            daoMap.put(name, baseDao);
            return baseDao;
        }
    }
}
