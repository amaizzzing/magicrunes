package com.magicrunes.magicrunes.ui.fragments

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.forEach
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.data.services.image.IImageLoader
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.databinding.FragmentCurrentFortuneBinding
import com.magicrunes.magicrunes.di.factory.ViewModelFactory
import com.magicrunes.magicrunes.ui.MainActivity
import com.magicrunes.magicrunes.ui.customView.RuneImageView
import com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies.ICurrentFragmentStrategy
import com.magicrunes.magicrunes.ui.states.BaseState
import com.magicrunes.magicrunes.ui.states.CurrentFortuneState
import com.magicrunes.magicrunes.ui.viewmodels.CurrentFortuneViewModel
import com.magicrunes.magicrunes.utils.setInvisible
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val DESCRIPTION_REQUEST_KEY = "description"
const val DESCRIPTION_BUNDLE_KEY = "description_key"

class CurrentFortuneFragment: BaseFragment<FragmentCurrentFortuneBinding, CurrentFortuneViewModel>() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    @Inject
    lateinit var imageService: ImageService

    private var currentStrategy: ICurrentFragmentStrategy? = null

    private var currentFortuneId: Long = -1

    private var currentFortuneState: CurrentFortuneState = CurrentFortuneState.BeforeShowing

    private var runesList = mutableListOf<RuneImageView?>()

    override fun setupViews() {
        showAnimatedButtonText(getString(R.string.show_start_text))

        binding?.cvButtonFortuneShow?.setOnClickListener {
            when(currentFortuneState) {
                CurrentFortuneState.BeforeShowing -> {
                    beforeShowingClick()
                }
                CurrentFortuneState.Showing -> {
                    Toast.makeText(requireContext(), getString(R.string.creating_fortune), Toast.LENGTH_SHORT).show()
                }
                CurrentFortuneState.ShowFortuneDescription -> {
                    showFortuneDescription()
                }
            }
        }
    }

    private fun beforeShowingClick() = launch(coroutineContext) {
        currentFortuneState = CurrentFortuneState.Showing
        setEnabledButtonsOnFortuneShow(false)

        showAnimatedButtonText(getString(R.string.create_fortune))
        withContext(bgDispatcher) { showFortuneRunes() }
        showAnimatedButtonText(getString(R.string.show_description))

        setEnabledButtonsOnFortuneShow(true)
        currentFortuneState = CurrentFortuneState.ShowFortuneDescription
    }

    private fun showFortuneDescription() = launch(bgDispatcher) {
        val idInHistory = viewModel.updateHistoryFortune(currentFortuneId)
        withContext(this@CurrentFortuneFragment.coroutineContext) {
            idInHistory?.let {
                setFragmentResult(
                    DESCRIPTION_REQUEST_KEY,
                    bundleOf(DESCRIPTION_BUNDLE_KEY to it)
                )
            }

            findNavController().navigate(R.id.action_currentfortune_to_fortunedescription)
        }
    }

    private fun setEnabledButtonsOnFortuneShow(isEnabled: Boolean) {
        (requireActivity() as MainActivity).apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(isEnabled)
            findViewById<BottomNavigationView>(R.id.bottom_nav_view)
                .menu
                .forEach { it.isEnabled = isEnabled }
        }
    }

    private fun showAnimatedButtonText(text: String) {
        binding?.textShowingFortune?.apply {
            alpha = 0f
            this.text = text
            animate()?.alpha(1f)?.setDuration(400)?.start()
        }
    }

    private suspend fun showFortuneRunes() {
        runesList
            .zip(viewModel.getRandomRunes(runesList.size))
            .forEach {
                withContext(coroutineContext) {
                    imageLoader.loadInto(
                        imageService.getRuneBitmap(
                            it.second.image,
                            it.second.isReverse
                        ),
                        it.first as ImageView
                    )
                    (it.first as ImageView).apply {
                        alpha = 0f
                        animate().alpha(1.0f).setDuration(1000).start()
                    }
                }
                delay(750)
            }
    }

    override fun getViewModelClass(): Class<CurrentFortuneViewModel> =
        CurrentFortuneViewModel::class.java

    override fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[getViewModelClass()]
    }

    override fun getViewBinding(): FragmentCurrentFortuneBinding =
        FragmentCurrentFortuneBinding.inflate(layoutInflater)

    override fun observeData() {
        super.observeData()
        viewModel.data.observe(viewLifecycleOwner) {
            it?.let {
                renderData(it)
            } ?: renderData(BaseState.Error(Error()))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MagicRunesApp.appComponent.inject(this)

        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            currentFortuneId = bundle.getLong(BUNDLE_KEY)
            viewModel.getCurrentFortuneStrategy(currentFortuneId)
        }

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun createVisibleRunes(strategy: ICurrentFragmentStrategy) {
        fillRunesImageList()

        val list = mutableListOf<RuneImageView?>()
        runesList.forEachIndexed { index, runeImageView ->
            val data = index + 1
            if (strategy.invisibleRuneList.contains(data)) {
                binding?.apply {
                    when(data) {
                        1 -> fortuneRune1.setInvisible()
                        2 -> fortuneRune2.setInvisible()
                        3 -> fortuneRune3.setInvisible()
                        4 -> fortuneRune4.setInvisible()
                        5 -> fortuneRune5.setInvisible()
                        6 -> fortuneRune6.setInvisible()
                        7 -> fortuneRune7.setInvisible()
                        8 -> fortuneRune8.setInvisible()
                        9 -> fortuneRune9.setInvisible()
                    }
                }
            } else {
                list.add(runeImageView)
            }
        }
        if (list.isNotEmpty()) {
            runesList.clear()
            runesList.addAll(list)
        }
    }

    private fun fillRunesImageList() {
        binding?.apply {
            runesList.clear()
            runesList.addAll(
                listOf(
                    fortuneRune1,
                    fortuneRune2,
                    fortuneRune3,
                    fortuneRune4,
                    fortuneRune5,
                    fortuneRune6,
                    fortuneRune7,
                    fortuneRune8,
                    fortuneRune9
                )
            )
        }
    }

    override fun renderData(data: BaseState) {
        binding?.apply {
            when(data) {
                is BaseState.Success<*> -> {
                    showProgress(
                        pbCurrentFortune,
                        gridCurrentFortune,
                        false
                    )

                    (data.resultData as ICurrentFragmentStrategy).also {
                        currentStrategy = it
                        createVisibleRunes(it)
                    }
                }
                is BaseState.Error -> {
                    showProgress(
                        pbCurrentFortune,
                        gridCurrentFortune,
                        false
                    )

                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_LONG).show()
                }
                is BaseState.Loading -> {
                    showProgress(
                        pbCurrentFortune,
                        gridCurrentFortune,
                        true
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onDestroyView()
    }
}