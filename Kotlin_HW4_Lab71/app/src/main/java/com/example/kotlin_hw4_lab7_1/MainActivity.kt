package com.example.kotlin_hw4_lab7_1

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var rabprogress=0
    private var turprogress=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_start.setOnClickListener{
            btn_start.isEnabled = false
            rabprogress=0
            turprogress=0
            seekBar.progress=0
            seekBar2.progress = 0
            runThread()
            runAsyncTask()
        }
    }
    private fun runThread(){
        Thread(Runnable {
            while (rabprogress<=100 && turprogress<=100){
                try {
                    Thread.sleep(100)
                    rabprogress += (Math.random() * 3).toInt()
                    val msg = Message()
                    msg.what=1
                    mHandler.sendMessage(msg)
                } catch (e:InterruptedException) {
                    e.printStackTrace();
                }
            }
        }).start()
    }
    private val mHandler = @SuppressLint("HandlerLeak")
    object :Handler(){
        override public fun handleMessage(msg:Message?){
            when (msg!!.what) {
                1 -> seekBar.setProgress(rabprogress)
            }
            if (rabprogress >= 100 && turprogress >= 100) {
                Toast.makeText(this@MainActivity,"兔子勝利",Toast.LENGTH_LONG)
                btn_start.isEnabled = true
            }
        }
    }
    @SuppressLint("StaticFieldLeak")
    private fun runAsyncTask() {
        object : AsyncTask<Void, Int, Boolean>() {
            override fun doInBackground(vararg params: Void?): Boolean {
                while (turprogress <= 100 && rabprogress < 100) {
                    try {
                        Thread.sleep(100)
                        turprogress += (Math.random() * 3).toInt()
                        publishProgress(turprogress)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                return true
            }
            override fun onProgressUpdate(vararg values: Int?) {
                super.onProgressUpdate(*values)
                seekBar2.setProgress(values[0]!!)
            }
            override fun onPostExecute(aBoolean: Boolean?) {
                super.onPostExecute(aBoolean)
                if (turprogress >= 100 && rabprogress < 100) {
                    Toast.makeText(this@MainActivity, "烏龜勝利", Toast.LENGTH_SHORT).show()
                    btn_start.setEnabled(true)
                }
            }
        }.execute()
    }
}
