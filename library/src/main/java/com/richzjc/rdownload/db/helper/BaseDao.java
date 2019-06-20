package com.richzjc.rdownload.db.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.richzjc.rdownload.db.anotations.DbField;
import com.richzjc.rdownload.db.anotations.DbTable;
import com.richzjc.rdownload.notification.callback.ParentTaskCallback;
import java.lang.reflect.Field;
import java.util.*;

public class BaseDao<T extends ParentTaskCallback> implements IBaseDao<T> {
    //数据库的路径
    String sqldbPath;
    // 表名
    private String tableName;
    private Class<T> beanClazz;
    // 缓存map key（数据库对应的字段名），value（成员变量）
    private HashMap<String, Field> cacheMap;
    // 是否已经初始化
    private boolean isInit = false;

    protected boolean init(String sqlDbPath, Class<T> beanClazz) {
        this.sqldbPath = sqlDbPath;
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqldbPath,null);
        this.beanClazz = beanClazz;
        if (!isInit) {
            // 先要得到表名
            DbTable dbTable = this.beanClazz.getAnnotation(DbTable.class);
            if (dbTable == null) {
                throw new IllegalStateException("实体类的model 必须要添加 数据库表明的注解");
            }
            tableName = dbTable.value();
            if (sqLiteDatabase == null || !sqLiteDatabase.isOpen() || tableName == null) {
                return false;
            }
            cacheMap = new HashMap<>();
            initCacheMap();
            // 创建数据表
            sqLiteDatabase.execSQL(getCreateTableSql());
            isInit = true;
            sqLiteDatabase.close();
        }
        return false;
    }

    private void initCacheMap() {
        // 获取类对象的所有成员变量
        Field[] declaredFields = beanClazz.getFields();
        for (Field declaredField : declaredFields) {
            DbField dbField = declaredField.getAnnotation(DbField.class);
            if (dbField != null) {
                cacheMap.put(dbField.value(), declaredField);
            }
        }
    }

    private String getCreateTableSql() {
        // create table if not exists tableName(id integer,name varchar(20))
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("create table if not exists ");
        stringBuffer.append(tableName + "(");
        stringBuffer.append("configurationKey" + " TEXT ,");
        stringBuffer.append("parentTaskId" + " TEXT ,");
        stringBuffer.append("progress" + " INTEGER ,");
        stringBuffer.append("status" + " INTEGER ,");
        stringBuffer.append("totalLength" + " LONG ,");
        stringBuffer.append("downloadLength" + " LONG ,");
        //获取beanClazz对象的所有成员变量
        Field[] declaredFields = beanClazz.getDeclaredFields();
        DbField dbField;
        for (Field declaredField : declaredFields) {
            Class type = declaredField.getType();
            dbField = declaredField.getAnnotation(DbField.class);
            if (dbField == null)
                continue;
            String filedName = dbField.value();
            if (type == String.class) {
                stringBuffer.append(filedName + " TEXT ,");
            } else if (TextUtils.equals(type.getName(), Integer.class.getName()) || TextUtils.equals(type.getName(), "int")) {
                stringBuffer.append(filedName + " INTEGER ,");
            } else if (TextUtils.equals(type.getName(), Long.class.getName()) || TextUtils.equals(type.getName(), "long")) {
                stringBuffer.append(filedName + " LONG ,");
            } else {
                // 不支持类型
                continue;
            }
        }
        // 去掉最后的逗号
        if (stringBuffer.charAt(stringBuffer.length() - 1) == ',') {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        stringBuffer.append(")");
        Log.e("sql语句", stringBuffer.toString());
        return stringBuffer.toString();
    }

    @Override
    public void insert(String configurationKey, T bean) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqldbPath,null);
        List<T> list = query(new String[]{"parentTaskId", "progress", "status"}, "configurationKey = ? and parentTaskId = ?", new String[]{configurationKey, bean.getParentTaskId()});
        if (list.size() <= 0) {
            Map<String, String> map = getValue(configurationKey, bean);
            // 将map转换成contentvalues
            ContentValues contentValues = getContentValues(map);
            sqLiteDatabase.insert(tableName, null, contentValues);
        } else {
            bean.progress = list.get(0).progress;
            bean.status = list.get(0).status;
            bean.totalLength = list.get(0).totalLength;
            bean.downloadLength = list.get(0).downloadLength;
        }
        sqLiteDatabase.close();
    }

    @Override
    public void update(ContentValues values, String whereClause, String[] whereArgs){
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqldbPath,null);
        sqLiteDatabase.update(tableName, values, whereClause, whereArgs);
        sqLiteDatabase.close();
    }

    public List<T> query(String[] columns, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqldbPath,null);
        List<T> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(tableName, columns, selection, selectionArgs, null, null, null);
        Log.i("download", cursor.getCount() + "");
        if (cursor.moveToFirst()) {
            T entity;
            String value;
            Field[] fields = beanClazz.getFields();
            Map<String, Field> map = new HashMap<>();
            for(Field field : fields){
                for(String column : columns){
                    if(TextUtils.equals(field.getName(), column)){
                        map.put(column, field);
                    }
                }
            }
            do {
                try {
                    entity = beanClazz.newInstance();
                    for(String column : columns){
                        value = cursor.getString(cursor.getColumnIndex(column));
                        try {
                            Field field = map.get(column);
                            if(field != null){
                                field.setAccessible(true);
                                if (field.getType() == String.class) {
                                    field.set(entity, value);
                                } else if (TextUtils.equals(field.getType().getName(), Integer.class.getName()) || TextUtils.equals(field.getType().getName(), "int")) {
                                    field.set(entity, Integer.parseInt(value));
                                } else if (TextUtils.equals(field.getType().getName(), Long.class.getName()) || TextUtils.equals(field.getType().getName(), "long")) {
                                    field.set(entity, Integer.parseInt(value));
                                } else {
                                    field.set(entity, value);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    list.add(entity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
            while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return list;
    }

    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        // 拿到map的keyset
        Set<String> keys = map.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);
            if (value != null) {
                contentValues.put(key, value);
            }
        }
        return contentValues;
    }

    // 获取对象的属性值，并且按照contentValue的方式存储起来
    private Map<String, String> getValue(String configurationKey, T bean) {
        Map<String, String> map = new HashMap<>();
        map.put("configurationKey", configurationKey);
        map.put("parentTaskId", bean.getParentTaskId());
        map.put("progress", String.valueOf(bean.progress));
        map.put("status", String.valueOf(bean.status));
        map.put("totalLength", String.valueOf(bean.totalLength));
        map.put("downloadLength", String.valueOf(bean.downloadLength));
        // 从缓存map中获取成员变量
        Iterator<Field> iterator = cacheMap.values().iterator();
        while (iterator.hasNext()) {
            // 拿到成员变量对象
            Field field = iterator.next();
            // 打开权限
            field.setAccessible(true);
            // 从成员变量中获取field值
            try {
                Object o = field.get(bean);
                if (o == null) {
                    continue;
                }
                String value = o.toString();
                String key = field.getAnnotation(DbField.class).value();
                if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(key)) {
                    map.put(key, value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
