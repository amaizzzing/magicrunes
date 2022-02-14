package com.magicrunes.magicrunes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.ui.states.BaseState
import com.magicrunes.magicrunes.utils.setGone
import com.magicrunes.magicrunes.utils.setVisible
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<VBinding : ViewBinding?, VViewModel : ViewModel>: Fragment(), CoroutineScope {
    protected lateinit var viewModel: VViewModel
    protected abstract fun getViewModelClass(): Class<VViewModel>
    protected abstract fun initViewModel()

    protected var binding: VBinding? = null
    protected abstract fun getViewBinding(): VBinding?

    abstract fun renderData(data: BaseState)

    open fun showProgress(progressBar: View?, view: View?, isShowing: Boolean) {
        if (progressBar != null && view != null) {
            if (isShowing) {
                view.setGone()
                progressBar.setVisible()
            } else {
                view.setVisible()
                progressBar.setGone()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBindingAndViewModel()

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()

        if (this !is FortuneDescriptionFragment) {
            collectJob = launch {
                collector.collect {
                    observeData()
                }
            }
            collectJob?.start()
        } else {
            observeData()
        }
    }

    val googleService = MagicRunesApp.appComponent.getGoogleService()
    val collector = googleService.googleStateFlow
    var collectJob: Job? = null

    override fun onStop() {
        super.onStop()
        collectJob?.cancel()
        collectJob = null
    }

    open fun setupViews() {}

    open fun observeView() {}

    open fun observeData() {}

    private fun initBindingAndViewModel() {
        binding = getViewBinding()
        initViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

    val bgDispatcher = MagicRunesApp.backgroundTaskDispatcher

    val bgSingleDispatcher = MagicRunesApp.orderedBackgroundTaskDispatcher
}