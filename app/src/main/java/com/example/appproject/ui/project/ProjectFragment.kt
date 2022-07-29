package com.example.appproject.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.appproject.R

class ProjectFragment : Fragment() {
    private val projectViewModel = ProjectViewModel()
    lateinit var projectList: ArrayList<ProjectViewModel.Project>


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


        //添加观察者
        projectViewModel.shareProjectData.observe(requireActivity()) {
            projectList = it.data.datas
            //刷新recyclerview
        }
        projectRecyclerView.adapter =
            ProjectAdapter(requireActivity(), projectList, this)
        projectRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        //下拉刷新
        projectSwipeRefreshLayout.setOnRefreshListener {
            projectViewModel.onRefresh()
            projectRecyclerView.adapter?.notifyDataSetChanged()
            projectSwipeRefreshLayout.isRefreshing = false
        }



        super.onViewCreated(view, savedInstanceState)

    }
}