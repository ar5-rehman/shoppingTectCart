package com.clothing.shoppingcarts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.ui.setupWithNavController
import com.clothing.shoppingcarts.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment
import com.clothing.shoppingcarts.base.extension.navigateSafe
import com.clothing.shoppingcarts.base.navigation.NavManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navManager: NavManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWindowLightStatus()

        setUpViews()
    }

    private fun setUpViews() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragNavHost) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            navController.popBackStack()
            when (item.itemId) {
                R.id.shop -> {
                    navController.navigate(R.id.shop)
                }
                R.id.search -> {
                    navController.navigate(R.id.search)
                }
                R.id.closet -> {
                    navController.navigate(R.id.closet)
                }
                R.id.bag -> {
                    navController.navigate(R.id.bag)
                }
            }
            true
        }

        val badge =  binding.bottomNavigation.getOrCreateBadge(R.id.bag)
        badge.isVisible = true
        badge.number = 1

        navManager.setOnNavEvent {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.shop ->     showBottomNav()
                    R.id.search -> showBottomNav()
                    R.id.closet -> showBottomNav()
                    R.id.bag -> showBottomNav()
                    else -> hideBottomNav()
                }
            }
            val currentFragment = navHostFragment.childFragmentManager.fragments.first()
            currentFragment?.navigateSafe(it)
        }
    }

    private fun showBottomNav() {
        binding.bottomNavigation.isVisible = true
    }

    private fun hideBottomNav() {
        binding.bottomNavigation.isGone = true
    }

    private fun setWindowLightStatus() {
        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).isAppearanceLightStatusBars = true
    }
}