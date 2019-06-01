package com.richzjc.rdownload.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    public static boolean createFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                return file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
