package com.clothing.shoppingcarts

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.ui.setupWithNavController
import com.clothing.shoppingcarts.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment
import com.clothing.shoppingcarts.base.extension.navigateSafe
import com.clothing.shoppingcarts.base.navigation.NavManager
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
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

        //val badge =  binding.bottomNavigation.getOrCreateBadge(R.id.bag)
        //badge.isVisible = true
        //badge.number = 1
        addBadge(3)

        navManager.setOnNavEvent {
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.shop ->     showBottomNav()
                    R.id.search -> showBottomNav()
                    R.id.closet -> showBottomNav()
                    R.id.bag -> showBottomNav()
                    //else -> hideBottomNav()
                    else -> showBottomNav()
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

    /**
     * add badge(notification dot) to bottom bar
     * @param position to get badge container
     */
    @SuppressLint("PrivateResource")
    private fun addBadge(position: Int) {
        // get badge container (parent)
        val bottomMenu = binding.bottomNavigation.getChildAt(0) as? BottomNavigationMenuView
        val v = bottomMenu?.getChildAt(position) as? BottomNavigationItemView

        // inflate badge from layout
        val badge = LayoutInflater.from(this)
            .inflate(R.layout.badge_layout, bottomMenu, false)

        val bagTotalItems = badge.findViewById<TextView>(R.id.bagTotalItems)
        bagTotalItems.text = "1"

        // create badge layout parameter
        val badgeLayout: FrameLayout.LayoutParams = FrameLayout.LayoutParams(badge?.layoutParams!!).apply {
            gravity = Gravity.CENTER_HORIZONTAL
            topMargin = resources.getDimension(R.dimen.design_bottom_navigation_margin).toInt()

            // <dimen name="bagde_left_margin">8dp</dimen>
            leftMargin = resources.getDimension(R.dimen.bagde_left_margin).toInt()
        }

        // add view to bottom bar with layout parameter
        v?.addView(badge, badgeLayout)
    }
}