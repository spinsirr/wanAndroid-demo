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
    private val projectResponse = MutableLiveData<Data>()
    private val projectCategory = MutableLiveData<List<String>>()
    var shareProjectData: LiveData<Data> = projectResponse
    var shareProjectCategory: LiveData<List<String>> = projectCategory



    private fun getProjectCategory() {
        ProjectRepository.getProjectsCategory(object : NetResult<Any> {
            override fun onResult(netData: NetData<Any>) {
                if (netData.errorCode == 0) {
                    Log.d("category", netData.json)
                }
            }

        })
    }

    private fun getProjectResponse() {
        ProjectRepository.getProjectList(1, 294, object : NetResult<Data> {
            override fun onResult(netData: NetData<Data>) {
                if (netData.errorCode == 0) {
                    Log.d("spencer", netData.json)
                    netData.data?.let {
                        projectResponse.postValue(it)
                    }

                }
            }

        })
    }

    data class Data(
        val curPage: Int,
        val datas: MutableList<Project>,
        val pageCount: Int,
        val size: Int
    )

    data class Project(
        val author: String,
        val chapterId: Int,         //cid
        val title: String,
        val desc: String,
        val envelopePic: String,
        val link: String,
        val niceDate: String
    )

    fun onRefresh() {
        getProjectResponse()
        getProjectCategory()
    }
}