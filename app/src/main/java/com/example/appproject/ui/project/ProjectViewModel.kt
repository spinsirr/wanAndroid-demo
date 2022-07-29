package com.example.appproject.ui.project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wanandroidapi.NetData
import com.example.wanandroidapi.NetResult
import com.example.wanandroidapi.repository.ProjectRepository
import com.google.gson.Gson


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
        getProjectResponse()
        getProjectCategory()
        shareProjectData = projectResponse
    }


    private fun getProjectCategory(){
        ProjectRepository.getProjectsCategory(object :NetResult<Any>{
            override fun onResult(netData: NetData<Any>) {
                if (netData.errorCode == 0){
                    Log.d("category", netData.json)
                }
            }

        })
    }

    private fun getProjectResponse() {
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