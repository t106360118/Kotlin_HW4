package com.example.kotlin_hw4_lab7_2

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_calculate.setOnClickListener {
            if (ed_height.length() < 1)
                Toast.makeText(this,"請輸入身高", Toast.LENGTH_SHORT).show()
            else if (ed_weight.length() < 1)
                Toast.makeText(this,"請輸入體重", Toast.LENGTH_SHORT).show()
            else
                runAsyncTask()
        }
    }
    @SuppressLint("StaticFieldLeak")
    private fun runAsyncTask() {
        object : AsyncTask<Void, Int, Boolean>() {
            override fun onPreExecute() {
                super.onPreExecute()
                tv_weight.text = "標準體重\n無"
                tv_bmi.text = "體脂肪\n無"
                progressBar2.progress = 0
                tv_progress.text = "0%"
                ll_progress.visibility = View.VISIBLE
            }
            override fun doInBackground(vararg voids: Void?): Boolean? {
                var progress = 0
                while (progress <= 100) {
                    try {
                        Thread.sleep(50)
                        publishProgress(progress)
                        progress++
                    } catch (e: InterruptedException) { e.printStackTrace() }
                }
                return true
            }
            override protected fun onProgressUpdate(vararg values: Int?) {
                super.onProgressUpdate(*values)
                progressBar2.progress = values[0]!!
                tv_progress.text = values[0].toString() + "%"
            }
            override fun onPostExecute(aBoolean: Boolean?) {
                super.onPostExecute(aBoolean)
                ll_progress.visibility = View.GONE
                val h = Integer.valueOf(ed_height.text.toString())
                val w = Integer.valueOf(ed_weight.text.toString())
                val standWeight: Double
                val bodyFat: Double
                if (btn_boy.isChecked) {
                    standWeight = (h - 80) * 0.7
                    bodyFat = (w - 0.88 * standWeight) / w * 100
                } else {
                    standWeight = (h - 70) * 0.6
                    bodyFat = (w - 0.82 * standWeight) / w * 100
                }
                tv_weight.text = String.format("標準體重 \n%.2f", standWeight)
                tv_bmi.text = String.format("體脂肪 \n%.2f", bodyFat)
            }
        }.execute();
    }
}
