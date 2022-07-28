package com.example.appproject.ui.project

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appproject.MainApplication
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException


class ProjectViewModel : ViewModel() {
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
        val link: String
    )

    private val projectResponse : MutableLiveData<ProjectResponse> by lazy{
        MutableLiveData<ProjectResponse>().also {
            projectResponse.value = getResponse(MainApplication.PRJURL)
        }
    }

    private fun getResponse(URL: String): ProjectViewModel.ProjectResponse {
        lateinit var projectResponse: ProjectViewModel.ProjectResponse
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(URL)
            .build()
        Log.d("Request", "I build Request")


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(URL, "I fail!")
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.d("Request", "I success!")
                    projectResponse =
                        Gson().fromJson(response.body?.string(), ProjectResponse::class.java)
                }
            }

        })
        return projectResponse
    }
}