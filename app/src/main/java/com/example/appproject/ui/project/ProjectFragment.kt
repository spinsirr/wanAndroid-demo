package com.example.appproject.ui.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.appproject.R
import com.google.android.material.tabs.TabLayout

@SuppressLint("NotifyDataSetChanged")

class ProjectFragment : Fragment() {
    private val projectViewModel = ProjectViewModel()
    val projectCategoryList = listOf("","","","","","","")
    lateinit var projectList: MutableList<ProjectViewModel.Project>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_project, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val projectSwipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.project_refresh)
        val projectRecyclerView = view.findViewById<RecyclerView>(R.id.project_recycler_view)
        val projectCategoryTabLayout = view.findViewById<TabLayout>(R.id.project_category_tab_layout)
        projectRecyclerView.layoutManager = LinearLayoutManager(requireActivity())


        //添加观察者
        projectViewModel.shareProjectData.observe(requireActivity()) {
            projectList = it.data.datas
            projectRecyclerView.adapter?.notifyDataSetChanged()
        }

        projectRecyclerView.adapter =
            ProjectAdapter(requireActivity(), projectList) {
                onReplaceFragment(it)
            }

        //下拉刷新
        projectSwipeRefreshLayout.setOnRefreshListener {
            projectViewModel.onRefresh()
            projectSwipeRefreshLayout.isRefreshing = false
            projectRecyclerView.adapter?.notifyDataSetChanged()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    private fun onReplaceFragment(url: String) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.main_activity_fragment_container, ProjectDetailFragment(url))
            .addToBackStack(url)
            .commit()
    }
}