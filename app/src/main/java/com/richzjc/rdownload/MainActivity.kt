package com.richzjc.rdownload

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.widget.Button
import com.richzjc.rdownload.notification.anotation.ProgressSubscribe
import com.richzjc.rdownload.notification.anotation.SizeChangeSubscribe
import com.richzjc.rdownload.manager.ConfirgurationBuilder
import com.richzjc.rdownload.download.constant.NetworkType
import com.richzjc.rdownload.manager.RDownloadManager
import com.richzjc.rdownload.data.model.ProgressStatusModel
import com.richzjc.rdownload.notification.rx.EventBus

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val configration = ConfirgurationBuilder()
            .setNetWorkType(NetworkType.WIFI)
            .setConfigurationKey("test")
            .setDownloadCompleteCallback(null)
            .setGetSaveModelCallback {
                return@setGetSaveModelCallback null
            }
            .build()
        RDownloadManager.getInstance().setConfiguration(configration)

        EventBus.getInstance().register(this)
        init()
    }

    private fun init(){
        findViewById<Button>(R.id.btn).setOnClickListener {
           RDownloadManager.getInstance().getConfiguration("test").addParentTask(ParentTaskModel());
        }
    }

    @ProgressSubscribe(configurationKey="test")
    fun getType(obj : ProgressStatusModel?){
        Log.i("test", "isSuccess")
    }

    @SizeChangeSubscribe(configurationKey = "test")
    fun testlength(size : Int){
        Log.i("test", "length = $size");
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getInstance().unRegister(this)
    }
}
