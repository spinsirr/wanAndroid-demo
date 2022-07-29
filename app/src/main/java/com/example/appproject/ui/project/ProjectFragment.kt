package com.example.appproject.ui.project

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.constraintlayout.widget.ConstraintLayout
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
        val projectSwipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.project_refresh)
        val projectRecyclerView = view.findViewById<RecyclerView>(R.id.project_recycler_view)
        projectViewModel.shareProjectData.observe(requireActivity(), Observer {
            projectList = it.data.datas
            val adapter = ProjectAdapter(requireActivity(), projectList, this)
            projectRecyclerView.adapter = adapter
        })
        projectRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        projectSwipeRefreshLayout.setOnRefreshListener {
            projectViewModel.onRefresh()
            projectSwipeRefreshLayout.isRefreshing = false
        }



        super.onViewCreated(view, savedInstanceState)

    }
}