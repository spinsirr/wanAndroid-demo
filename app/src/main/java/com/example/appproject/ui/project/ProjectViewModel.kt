package com.example.appproject.ui.project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


class ProjectViewModel : ViewModel() {
    private val projectResponse = MutableLiveData<ProjectResponse>()
    var shareProjectData: LiveData<ProjectResponse> = projectResponse
    init {
        onRefresh()
    }

    data class ProjectResponse(
        val data: Data,
        val errorCode: Int,
        val errorMsg: String
    )

    data class Data(
        val curPage: Int,
        val datas: List<Project>,
        val size: Int

    )

    data class Project(
        val author: String,
        val title: String,
        val desc: String,
        val envelopePic: String,
        val link: String,
        val niceDate: String
    )

    fun onRefresh() {
        val PRJURL = "https://www.wanandroid.com/project/list/${(1..19).random()}/json?cid=294"
        Log.d("spencer",PRJURL)
        getResponse(PRJURL)
        shareProjectData = projectResponse
    }


    private fun getResponse(URL: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(URL)
            .build()
        Log.d("spencer", "Start request")
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.d("spencer","Request fail")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    projectResponse.postValue(
                        Gson().fromJson(
                            response.body?.string(),
                            ProjectResponse::class.java
                        )
                    )
                    Log.d("spencer", "Request success")
                }
            }

        })
    }
}