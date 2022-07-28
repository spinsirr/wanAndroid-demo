package com.example.appproject.ui.project

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.appproject.R

class ProjectFragment : Fragment() {
    private val projectViewModel = ProjectViewModel()
    lateinit var projectList: List<ProjectViewModel.Project>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        projectViewModel.shareProjectData.observe(requireActivity(), Observer {
            projectList = it.data.datas
        })
        val projectSwipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.project_refresh)
        val projectRecyclerView = view.findViewById<RecyclerView>(R.id.project_recycler_view)
        projectSwipeRefreshLayout.setOnRefreshListener {
            projectViewModel.onRefresh()
            projectSwipeRefreshLayout.isRefreshing = false
            projectRecyclerView.adapter = ProjectAdapter(requireActivity(), projectList)
        }
        projectRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        projectRecyclerView.adapter = ProjectAdapter(requireActivity(), projectList)


        super.onViewCreated(view, savedInstanceState)

    }
}