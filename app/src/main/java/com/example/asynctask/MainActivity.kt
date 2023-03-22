package com.example.asynctask

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var button2: Button
    private val textDone = "done"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button2 = findViewById(R.id.button2)
    }

    fun doSomethingLong() {
        try {
            println("long operation start")
            Thread.sleep(1000)
            button2.text = textDone
            println("long operation stop")
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun buttonClick(view: View) {
        val task = MyTask()
        task.execute(3, 4)
    }

    @SuppressLint("StaticFieldLeak")
    class MyTask : AsyncTask<Int, Void, String>() {
        private val mainActivity = MainActivity()

        override fun onPreExecute() {
            println("onPreExecute ${Thread.currentThread().name} ${Thread.currentThread().id}")
        }

        override fun doInBackground(vararg params: Int?): String? {
            println("doInBackground ${Thread.currentThread().name} ${Thread.currentThread().id}")

            val a = params[0]
            val b = params[1]
            val c = a!!.plus(b!!)

            Thread.sleep(1000)

            return c.toString()
        }

        override fun onPostExecute(result: String?) {
            println("onPostExecute ${Thread.currentThread().name} ${Thread.currentThread().id}")

            println("Sum $result")
//            mainActivity.button2.text = result      // cant and the text to the button2 cuz button2 is located in MainActivity. idk why
        }
    }
}