package com.example.appproject.ui.project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appproject.ui.util.showToast
import com.example.wanandroidapi.NetCallback
import com.example.wanandroidapi.NetData
import com.example.wanandroidapi.NetResult
import com.example.wanandroidapi.repository.ProjectRepository
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


class ProjectViewModel : ViewModel() {
    private val projectResponse = MutableLiveData<ProjectResponse>()
    private var page: Int
    var shareProjectData: LiveData<ProjectResponse> = projectResponse


    init {
        onRefresh()
        page = 0
    }

    data class ProjectResponse(
        val data: Data,
        val errorCode: Int,
        val errorMsg: String
    )

    data class Data(
        val curPage: Int,
        val datas: ArrayList<Project>,
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
        val projectUrlRefresh = "https://www.wanandroid.com/project/list/1/json?cid=294"
        Log.d("spencer", projectUrlRefresh)
        getResponse(projectUrlRefresh)
        shareProjectData = projectResponse
    }

    fun onLoad(){
        val projectUrlLoad = "https://www.wanandroid.com/project/list/$page/json?cid=294"
        Log.d("spencer",projectUrlLoad)
        getResponse(projectUrlLoad)
    }


    private fun getResponse(URL: String) {
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url(URL)
//            .build()
//        Log.d("spencer", "Start request")
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//                Log.d("spencer", "Request fail")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    projectResponse.postValue(
//                        Gson().fromJson(
//                            response.body?.string(),
//                            ProjectResponse::class.java
//                        )
//                    )
//                    Log.d("spencer", "Request success")
//                }
//            }
//
//        })
        ProjectRepository.getProjectList(1, 294, object : NetResult<Any> {
            override fun onResult(netData: NetData<Any>) {
               if (netData.errorCode == 0) {
                   Log.d("liyu", "json = ${netData.json}")
                   projectResponse.postValue(
                       Gson().fromJson(
                           netData.json,
                           ProjectResponse::class.java
                       )
                   )
               }
            }

        })
    }
}