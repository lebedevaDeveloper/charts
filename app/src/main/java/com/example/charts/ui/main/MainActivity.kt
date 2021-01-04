package com.example.charts.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.charts.R
import com.example.charts.databinding.ActivityMainBinding
import com.example.charts.ui.activityBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val binding by activityBinding(ActivityMainBinding::inflate)

    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    companion object {
        fun newIntent(
            context: Context
        ): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navController.let { controller ->
            binding.navigationView.setupWithNavController(controller)
            NavigationUI.setupActionBarWithNavController(this, controller, binding.drawerLayout)
        }
        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        binding.drawerLayout.closeDrawers()

       when(item.itemId){
           R.id.lineChart -> navController.navigate(R.id.lineChartFragment)
           R.id.candleStickChart -> navController.navigate(R.id.candleStickChartFragment)
       }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.let{
            NavigationUI.navigateUp(it, binding.drawerLayout)
        }
        return  false
    }


    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}