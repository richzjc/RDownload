package com.richzjc.rdownload.data.model;

import android.content.Context;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wscn on 17/1/17.
 */

public class DataModel {

//    private String FILE_NAME = "download_task";
//    private HashMap<String, Long> map = new HashMap<>();
//    private Map<String, Long> dataObject;
//    private static DataModel dataModel;
//
//    public static DataModel getInstance(Context context) {
//        if (dataModel == null) {
//            synchronized (DataModel.class) {
//                if (dataModel == null) {
//                    dataModel = new DataModel(context);
//                }
//            }
//        }
//        return dataModel;
//    }
//
//
//    private DataModel(Context context) {
//        dataObject = new HashMap<>();
//        Map<String, Long> result = (HashMap<String, Long>)readObject(context, FILE_NAME);
//        if(result != null)
//        dataObject.putAll(result);
//    }
//
//    public Map<String, Long> readData() {
//        return dataObject;
//    }
//
//    public void clearData() {
//        dataObject.clear();
//    }
//
//    public void saveData(String url, Long length) {
//        dataObject.remove(url);
//        dataObject.put(url, length);
//    }
//
//    public void removeData(String content) {
//        dataObject.remove(content);
//    }
//
//    public void save() {
//        saveObject(dataObject, FILE_NAME);
//    }
//
//    public static boolean saveObject(Serializable ser, String file) {
//        FileOutputStream fos = null;
//        ObjectOutputStream oos = null;
//        Context context = getApplication();
//        try {
//            fos = context.openFileOutput(file, context.MODE_PRIVATE);
//            oos = new ObjectOutputStream(fos);
//            oos.writeObject(ser);
//            oos.write
//            oos.flush();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        } finally {
//            try {
//                oos.close();
//            } catch (Exception e) {
//            }
//            try {
//                fos.close();
//            } catch (Exception e) {
//            }
//        }
//    }
//
//    /**
//     * 读取对象
//     *
//     * @param file
//     * @return
//     * @throws IOException
//     */
//    public static Serializable readObject(Context context , String file) {
//        FileInputStream fis = null;
//        ObjectInputStream ois = null;
//        try {
//            fis = context.openFileInput(file);
//            ois = new ObjectInputStream(fis);
//            return (Serializable) ois.readObject();
//        } catch (FileNotFoundException e) {
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                ois.close();
//            } catch (Exception e) {
//            }
//            try {
//                fis.close();
//            } catch (Exception e) {
//            }
//        }
//        return null;
//    }
}
