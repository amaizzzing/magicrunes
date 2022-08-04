package com.magicrunes.magicrunes.ui

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.data.services.image.IImageLoader
import com.magicrunes.magicrunes.data.services.network.IGoogleService
import com.magicrunes.magicrunes.data.services.resource.IResourceService
import com.magicrunes.magicrunes.databinding.ActivityMainBinding
import com.magicrunes.magicrunes.ui.dialogs.SignInDialog
import com.magicrunes.magicrunes.ui.widget.RuneOfTheDayWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

    @Inject
    lateinit var resourceService: IResourceService

    @Inject
    lateinit var imageViewLoader: IImageLoader<ImageView>

    @Inject
    lateinit var googleService: IGoogleService

    lateinit var binding: ActivityMainBinding

    var navController: NavController? = null

    private var toolbarMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MagicRunesApp.appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)

        waitCreatingData()

        setTheme(R.style.Theme_MagicRunes)

        setContentView(binding.root)

        initNavigation()

        binding.mainToolbar.title = getString(R.string.title_main_fragment)

        setSupportActionBar(binding.mainToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        checkWidgets()

        launch {
            googleService.googleSignIn()

            googleService.googleStateFlow.collect { isSignIn ->
                loadAvatarImageToToolbar(isSignIn)
            }
        }
    }

    private fun signIn() {
        SignInDialog.newInstance().show(supportFragmentManager, "SignInDialog")
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
            signIn()
        }

        return super.onOptionsItemSelected(item);
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        toolbarMenu = menu

        loadAvatarImageToToolbar(googleService.getGooglePhoto() != null)

        return true
    }

    private fun loadAvatarImageToToolbar(isSignIn: Boolean) = launch {
        toolbarMenu?.findItem(R.id.account_image)?.let { accountItem ->
            if (isSignIn) {
                googleService.getGooglePhoto()?.let {
                    imageViewLoader.loadInto(it, accountItem, this@MainActivity, resources)
                }
            } else {
                imageViewLoader.loadInto(R.drawable.account_image, accountItem, this@MainActivity, resources)
            }
        }
    }
}