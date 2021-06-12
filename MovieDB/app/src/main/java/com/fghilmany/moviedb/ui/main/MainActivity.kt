package com.fghilmany.moviedb.ui.main


import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.fghilmany.moviedb.R
import com.fghilmany.moviedb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)

//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.nav_home, R.id.nav_popular, R.id.nav_favorite
//            )
//        )

//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}