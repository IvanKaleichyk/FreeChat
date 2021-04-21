package com.koleychik.freechat.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.koleychik.freechat.App
import com.koleychik.freechat.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.component.inject(this)
    }

    override fun onStart() {
        super.onStart()
        TODO("make online")
    }

    override fun onStop() {
        super.onStop()
        TODO("make offline")
    }
}