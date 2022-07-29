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
        getResponse()
        shareProjectData = projectResponse
    }


    private fun getResponse() {
        ProjectRepository.getProjectList(1, 294, object : NetResult<Any> {
            override fun onResult(netData: NetData<Any>) {
                if (netData.errorCode == 0) {
                    Log.d("spencer",netData.json)
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