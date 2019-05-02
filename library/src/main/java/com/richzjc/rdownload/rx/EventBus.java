package com.richzjc.rdownload.rx;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.richzjc.rdownload.anotation.ProgressSubscribe;
import com.richzjc.rdownload.anotation.SizeChangeSubscribe;
import com.richzjc.rdownload.model.RefreshDataModel;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EventBus {

    private static volatile EventBus instance;
    private Map<String, HashMap<Object, Method>> cacheMap;
    private Handler handler;

    public static EventBus getInstance() {
        if (instance == null) {
            synchronized (EventBus.class) {
                // 第二次检查
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    private EventBus() {
        cacheMap = new HashMap<>();
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                try {
                    RefreshDataModel dataModel = (RefreshDataModel) msg.obj;
                    HashMap<Object, Method> values = cacheMap.get(dataModel.subscribeName);
                    Set<Object> cacheKeys = values.keySet();
                    for (Object key : cacheKeys) {
                        Method method = values.get(key);
                        method.invoke(key, dataModel.object);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void register(Object obj) {
        registerProgressSubscribe(obj);
        registerSizeChangeSubscribe(obj);
        Log.e("cache", cacheMap.size() + "");
    }

    private void registerProgressSubscribe(Object obj) {
        if (cacheMap.containsKey(ProgressSubscribe.class.getSimpleName())) {
            HashMap<Object, Method> map = cacheMap.get(ProgressSubscribe.class.getSimpleName());
            if (!map.containsKey(obj)) {
                Method method = findProgressSubscribeList(obj);
                if (method != null)
                    map.put(obj, method);
            }
        } else {
            HashMap<Object, Method> map = new HashMap<>();
            Method method = findProgressSubscribeList(obj);
            if (method != null)
                map.put(obj, method);
            cacheMap.put(ProgressSubscribe.class.getSimpleName(), map);
        }
    }

    private void registerSizeChangeSubscribe(Object obj) {
        if (cacheMap.containsKey(SizeChangeSubscribe.class.getSimpleName())) {
            HashMap<Object, Method> map = cacheMap.get(SizeChangeSubscribe.class.getSimpleName());
            if (!map.containsKey(obj)) {
                Method method = findSizeChangeList(obj);
                if (method != null)
                    map.put(obj, method);
            }
        } else {
            HashMap<Object, Method> map = new HashMap<>();
            Method method = findSizeChangeList(obj);
            if (method != null)
                map.put(obj, method);
            cacheMap.put(SizeChangeSubscribe.class.getSimpleName(), map);
        }
    }

    private Method findProgressSubscribeList(Object obj) {
        Class<?> cls = obj.getClass();
        Method[] mths = cls.getDeclaredMethods();
        Method result = null;
        if (mths != null) {
            for (Method method : mths) {
                ProgressSubscribe ps = method.getAnnotation(ProgressSubscribe.class);
                if (ps == null)
                    continue;
                else {
                    Class<?>[] types = method.getParameterTypes();
                    if (types != null && types.length == 1 && TextUtils.equals("ProgressStatusModel", types[0].getSimpleName())) {
                        result = method;
                        break;
                    }
                }
            }
        }
        return result;
    }

    private Method findSizeChangeList(Object obj) {
        Class<?> cls = obj.getClass();
        Method[] mths = cls.getDeclaredMethods();
        Method result = null;
        if (mths != null) {
            for (Method method : mths) {
                SizeChangeSubscribe ps = method.getAnnotation(SizeChangeSubscribe.class);
                if (ps == null)
                    continue;
                else {
                    Class<?>[] types = method.getParameterTypes();
                    if (types != null && types.length == 1) {
                        result = method;
                        break;
                    }
                }
            }
        }
        return result;
    }

    public void post(RefreshDataModel refreshDataModel) {
        handler.obtainMessage(1000, refreshDataModel).sendToTarget();
    }

    public void unRegister(Object obj) {
        unregisterProgressSubscribe(obj);
        unregisterSizeChangeSubscribe(obj);
        Log.e("cache", cacheMap.size() + "; unregister");
    }

    private void unregisterProgressSubscribe(Object obj){
        if (cacheMap.containsKey(ProgressSubscribe.class.getSimpleName())) {
            HashMap<Object, Method> map = cacheMap.get(ProgressSubscribe.class.getSimpleName());
            if (map.containsKey(obj)) {
              map.remove(obj);
            }
        }
    }

    private void unregisterSizeChangeSubscribe(Object obj){
        if (cacheMap.containsKey(SizeChangeSubscribe.class.getSimpleName())) {
            HashMap<Object, Method> map = cacheMap.get(SizeChangeSubscribe.class.getSimpleName());
            if (map.containsKey(obj)) {
                map.remove(obj);
            }
        }
    }
}
