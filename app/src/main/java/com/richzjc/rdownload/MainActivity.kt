package com.richzjc.rdownload

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.widget.Button
import com.richzjc.rdownload.manager.ConfirgurationBuilder
import com.richzjc.rdownload.download.constant.NetworkType
import com.richzjc.rdownload.manager.RDownloadManager
import com.richzjc.rdownload.notification.rx.EventBus

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val configration = ConfirgurationBuilder()
            .setNetWorkType(NetworkType.WIFI)
            .setConfigurationKey("test")
            .build()
        RDownloadManager.getInstance().setConfiguration(configration)

        EventBus.getInstance().register(this)
        init()

        window.navigationBarColor = Color.parseColor("#1482f0")
    }

    private fun init(){
        findViewById<Button>(R.id.btn).setOnClickListener {
           RDownloadManager.getInstance().getConfiguration("test").addParentTask(ParentTaskModel());
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getInstance().unRegister(this)
    }
}
