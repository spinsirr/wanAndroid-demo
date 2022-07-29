package com.example.appproject

import android.app.Application
import android.content.Context
import java.lang.Math.random

class MainApplication:Application() {
    companion object{
        lateinit var context: Context
    }

    override fun onCreate() {
        context = baseContext
        super.onCreate()
    }
}