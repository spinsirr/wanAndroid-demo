package com.example.appproject

import android.app.Application
import android.content.Context
import java.lang.Math.random

class MainApplication:Application() {
    companion object{
//        const val PRJURL = "https://www.wanandroid.com/project/list/2/json?cid=294"
        lateinit var context: Context
    }

    override fun onCreate() {
        context = baseContext
        super.onCreate()
    }
}