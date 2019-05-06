package com.richzjc.rdownload

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.richzjc.rdownload.anotation.ProgressSubscribe
import com.richzjc.rdownload.anotation.SizeChangeSubscribe
import com.richzjc.rdownload.builder.ConfirgurationBuilder
import com.richzjc.rdownload.constant.NetworkType
import com.richzjc.rdownload.manager.RDownloadManager
import com.richzjc.rdownload.model.ProgressStatusModel
import com.richzjc.rdownload.rx.EventBus

import kotlinx.android.synthetic.main.activity_main.*

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
