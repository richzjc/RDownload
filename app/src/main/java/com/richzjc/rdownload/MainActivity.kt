package com.richzjc.rdownload

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.richzjc.rdownload.download.constant.NetworkType
import com.richzjc.rdownload.manager.Configuration
import com.richzjc.rdownload.manager.RDownloadManager
import com.richzjc.rdownload.notification.rx.EventBus

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("file", getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.absolutePath ?: "")

        setContentView(R.layout.activity_main)
        val configration = RDownloadManager.getInstance()
            .initBuilder()
            .setNetWorkType(NetworkType.WIFI)
            .setConfigurationKey("test")
            .build(applicationContext)
        EventBus.getInstance().register(this)
        init()

        window.navigationBarColor = Color.parseColor("#1482f0")
    }

    private fun init() {
        findViewById<Button>(R.id.btn).setOnClickListener {
            val model = ParentTaskModel()
            RDownloadManager.getInstance().addParentTask("test", model)
            val intent = Intent(this@MainActivity, DonwloadDetailActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getInstance().unRegister(this)
    }
}
