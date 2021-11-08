package com.magicrunes.magicrunes.ui

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.data.services.resource.IResourceService
import com.magicrunes.magicrunes.databinding.ActivityMainBinding
import com.magicrunes.magicrunes.ui.widget.RuneOfTheDayWidget
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var resourceService: IResourceService

    lateinit var binding: ActivityMainBinding

    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MagicRunesApp.appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)

        waitCreatingData()

        setTheme(R.style.Theme_MagicRunes)

        setContentView(binding.root)

        initNavigation()

        binding.mainToolbar.title = getString(R.string.title_main_fragment)

        setSupportActionBar(binding.mainToolbar);

        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        checkWidgets()
    }

    private fun checkWidgets() {
        val ids = AppWidgetManager.getInstance(this).getAppWidgetIds(
            ComponentName(
                this,
                RuneOfTheDayWidget::class.java
            )
        )
        if (ids.isEmpty()) {
            displaySnackBarWithBottomMargin(getString(R.string.snackbar_widget_text))
        }
    }

    private fun displaySnackBarWithBottomMargin(msg: String) {
        val snackbar = Snackbar.make(
            this.window.decorView.rootView,
            msg,
            Snackbar.LENGTH_SHORT
        )
        val snackBarView = snackbar.view
        snackBarView.translationY = (-resourceService.convertDpToPixel(100f))
        snackbar.show()
    }

    private fun initNavigation() {
        val navView: BottomNavigationView = binding.bottomNavView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        navController?.let {
            navView.setupWithNavController(it)

            binding.bottomNavView.setOnItemSelectedListener { menuItem ->
                if (menuItem.itemId != it.currentDestination?.id) {
                    it.navigate(
                        menuItem.itemId,
                        null,
                        NavOptions.Builder().setPopUpTo(R.id.mobile_navigation, false).build()
                    )
                    binding.mainToolbar.title = menuItem.title
                }
                true
            }
        }
    }

    private fun waitCreatingData() {
        binding.navHostFragmentActivityMain.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    //как будем получать данные, убрать sleep и true
                    return if (/*viewModel.isReady*/true) {
                        // The content is ready; start drawing.
                        binding.navHostFragmentActivityMain.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content is not ready; suspend.
                        false
                    }
                }
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed();
        }
        if (item.itemId == R.id.account_image) {
            println("OLOLO ACCOUNT")
        }

        return super.onOptionsItemSelected(item);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}