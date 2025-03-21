package com.alekseykostyunin.courses.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alekseykostyunin.courses.R
import com.alekseykostyunin.courses.databinding.ActivityMainBinding
import com.alekseykostyunin.courses.presentation.account.AccountFragment
import com.alekseykostyunin.courses.presentation.bookmark.BookmarkFragment
import com.alekseykostyunin.courses.presentation.listcourses.ListCoursesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment
        navController = navHostFragment.navController

        // Скрываем BottomNavigationView на первых двух экранах
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.onboarding, R.id.entrance -> binding.bottomNavigation.visibility = View.GONE
                else -> binding.bottomNavigation.visibility = View.VISIBLE
            }
        }

        // Проверка, первый ли это запуск приложения
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("is_first_launch", true)

        if (isFirstLaunch) {
            navController.navigate(R.id.onboarding)
            sharedPreferences.edit { putBoolean("is_first_launch", false) }
        } else {
            navController.navigate(R.id.list_courses)
        }

        // Настройка BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_list_courses -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragmentContainer, ListCoursesFragment())
                        .commit()
                    true
                }
                R.id.navigation_bookmark -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragmentContainer, BookmarkFragment())
                        .commit()
                    true
                }
                R.id.navigation_account -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.mainFragmentContainer, AccountFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

    }
}