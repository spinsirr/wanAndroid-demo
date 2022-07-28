package com.example.appproject

import android.app.Application
import android.content.Context

class MainApplication:Application() {
    companion object{
        const val PRJURL = "https://www.wanandroid.com/project/list/1/json?cid=294"
        lateinit var context: Context
    }

    override fun onCreate() {
        context = baseContext
        super.onCreate()
    }
}