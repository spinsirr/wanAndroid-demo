package com.example.appproject.ui.project

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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
    lateinit var adapter: ProjectAdapter
    lateinit var progressbar: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_project, container, false)
    }


    fun initData() {
        if (adapter.isEmpty()) {
            progressbar.visibility = View.VISIBLE
        }
        projectViewModel.onRefresh()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val projectSwipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.project_refresh)
        val projectRecyclerView = view.findViewById<RecyclerView>(R.id.project_recycler_view)
        val projectCategoryTabLayout =
            view.findViewById<TabLayout>(R.id.project_category_tab_layout)
        projectRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        progressbar = view.findViewById(R.id.progress)

        //添加观察者
        projectViewModel.shareProjectData.observe(requireActivity()) {
            progressbar.visibility = View.GONE
            adapter.addData(it.datas)
            projectRecyclerView.adapter?.notifyDataSetChanged()
        }
        adapter = ProjectAdapter(requireActivity()) {

            onReplaceFragment(it)
        }
        projectRecyclerView.adapter = adapter


        //下拉刷新
        projectSwipeRefreshLayout.setOnRefreshListener {
            projectViewModel.onRefresh()
            projectSwipeRefreshLayout.isRefreshing = false
            projectRecyclerView.adapter?.notifyDataSetChanged()
        }

        initData()

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