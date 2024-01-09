package com.example.finalproject.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.finalproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)?.findNavController()!!
        bottomNavigationBar.setupWithNavController(navController)
    }

    fun setToolbarVisibility(isVisible: Boolean) {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        if (isVisible) {
            toolbar.visibility = View.VISIBLE
        } else {
            toolbar.visibility = View.GONE
        }
    }

    fun setToolbarTitle(title: String) {
        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = title
    }

    // In MainActivity.kt
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.navigation_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    // In MainActivity.kt
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.aboutMe -> {
                // Handle the click on Item 1
                finish()
                return true
            }
            R.id.task2 -> {
                // Handle the click on Item 2
                return true
            }
            // Add more cases as needed
            else -> return super.onOptionsItemSelected(item)
        }
    }


}