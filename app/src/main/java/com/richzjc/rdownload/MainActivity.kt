package com.richzjc.rdownload

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.richzjc.rdownload.anotation.ProgressSubscribe
import com.richzjc.rdownload.anotation.SizeChangeSubscribe
import com.richzjc.rdownload.model.ProgressStatusModel
import com.richzjc.rdownload.rx.EventBus

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        EventBus.getInstance().register(this)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    @ProgressSubscribe(confirgurationKey="test")
    fun getType(obj : ProgressStatusModel?){
        Log.i("test", "isSuccess")
    }

    @SizeChangeSubscribe(confirgurationKey = "test")
    fun testlength(size : Int){
        Log.i("test", "length = $size");
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getInstance().unRegister(this)
    }
}
