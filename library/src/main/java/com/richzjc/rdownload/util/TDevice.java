package com.richzjc.rdownload.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import java.text.DecimalFormat;

/**
 * 作者： 巴掌 on 15/7/6 13:23
 * 邮箱： wangyuwei@wallstreetcn.com
 */

public class TDevice {

    /**
     * 判断是否以WIFI方式联网
     *
     * @param
     * @return
     */
    public static boolean isConnectWIFI(Context context) {
        //获取网络连接管理者
        ConnectivityManager connectionManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络的状态信息，有下面三种方式
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context){
        try {
            ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if(info != null)
                return info.isConnected();
            else
                return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
