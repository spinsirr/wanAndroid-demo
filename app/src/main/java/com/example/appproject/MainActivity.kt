package com.example.appproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.appproject.ui.home.HomeFragment
import com.example.appproject.ui.project.ProjectFragment
import com.example.appproject.ui.square.SquareFragment

class MainActivity : AppCompatActivity() {
    companion object {
        val squareFragment = SquareFragment()
        val homeFragment = HomeFragment()
        val projectFragment = ProjectFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onActivityNavigation()
    }

    private fun onActivityNavigation() {
        onReplace(homeFragment)
        findViewById<View>(R.id.navi_button_home).setOnClickListener {
            onReplace(homeFragment)
        }
        findViewById<View>(R.id.navi_button_project).setOnClickListener {
            onReplace(projectFragment)
        }
        findViewById<View>(R.id.navi_button_square).setOnClickListener {
            onReplace(squareFragment)
        }
    }

    private fun onReplace(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_activity_fragment_container, fragment)
            .addToBackStack("MainActivity")
            .commit()
    }
}