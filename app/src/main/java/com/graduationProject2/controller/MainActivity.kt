package com.graduationProject2.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private val sr=SimpleRunnable(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edit =findViewById<Button>(R.id.editButton)
        edit.setOnClickListener {

            val intent = Intent(this@MainActivity, EditActivity::class.java)
            startActivity(intent)
            sr.stop1()
        }


    }

    override fun onResume() {
        super.onResume()
        sr.start()
        val th=Thread(sr)
        th.start()
    }
    override fun onPause() {
        super.onPause()
        sr.stop1()
    }

}

