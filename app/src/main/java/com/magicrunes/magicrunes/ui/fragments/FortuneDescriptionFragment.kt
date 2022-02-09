package com.magicrunes.magicrunes.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayoutMediator
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.data.services.image.IImageLoader
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.data.services.resource.IResourceService
import com.magicrunes.magicrunes.databinding.FragmentFortuneDescriptionBinding
import com.magicrunes.magicrunes.di.factory.ViewModelFactory
import com.magicrunes.magicrunes.ui.MainActivity
import com.magicrunes.magicrunes.ui.adapters.ViewPagerAdapter
import com.magicrunes.magicrunes.ui.adapters.presenters.viewpager.IViewPagerPresenter
import com.magicrunes.magicrunes.ui.fragments.currentfortunestrategies.FortuneFactory
import com.magicrunes.magicrunes.ui.models.CurrentFortuneDescriptionModel
import com.magicrunes.magicrunes.ui.states.BaseState
import com.magicrunes.magicrunes.ui.viewmodels.CurrentFortuneDescriptionViewModel
import com.magicrunes.magicrunes.utils.getRecyclerView
import javax.inject.Inject

class FortuneDescriptionFragment: BaseFragment<FragmentFortuneDescriptionBinding, CurrentFortuneDescriptionViewModel>() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var fortuneFactory: FortuneFactory

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    @Inject
    lateinit var imageService: ImageService

    @Inject
    lateinit var viewPagerPresenter: IViewPagerPresenter

    @Inject
    lateinit var resourceService: IResourceService

    private var historyId: Long = -1L

    private var runeList: List<CurrentFortuneDescriptionModel> = listOf()

    override fun setupViews() {
        configureViewPager()
        configureBottomSheetLayout()

        binding?.viewPager?.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                createRuneImage(position)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MagicRunesApp.appComponent.inject(this)

        setFragmentResultListener(DESCRIPTION_REQUEST_KEY) { _, bundle ->
            historyId = bundle.getLong(DESCRIPTION_BUNDLE_KEY)
            viewModel.getCurrentDescription(historyId)
            (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.data.observe(viewLifecycleOwner) {
            it?.let {
                renderData(it)
            } ?: renderData(BaseState.Error(Error()))
        }
    }

    private fun createRuneImage(position: Int) {
        if (runeList.isNotEmpty()) {
            binding?.apply {
                imageRuneFotuneDescription.alpha = 0f

                imageLoader.loadInto(
                    imageService.getImageResource(runeList[position].image),
                    imageRuneFotuneDescription as ImageView
                )

                imageRuneFotuneDescription.animate()?.alpha(1f)?.setDuration(400)?.start()
                if (runeList[position].isReverse) {
                    imageRuneFotuneDescription.rotation = 180f
                }
                viewPager.adapter?.notifyItemChanged(position)
            }
        }
    }

    private fun configureViewPager() {
        binding?.viewPager?.getRecyclerView()?.apply {
            isNestedScrollingEnabled = false
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }

    private fun configureBottomSheetLayout() {
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<View>((binding?.bottomSheetFotuneDescription) as View)

        behavior.apply {
            peekHeight = resourceService.getDisplayMetrics().heightPixels / 3
            isFitToContents = false
            halfExpandedRatio = 0.5f
            state = BottomSheetBehavior.STATE_HALF_EXPANDED
            isHideable = false
        }

        behavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }

    override fun getViewModelClass(): Class<CurrentFortuneDescriptionViewModel> =
        CurrentFortuneDescriptionViewModel::class.java

    override fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[getViewModelClass()]
    }

    override fun getViewBinding(): FragmentFortuneDescriptionBinding =
        FragmentFortuneDescriptionBinding.inflate(layoutInflater)

    override fun renderData(data: BaseState) {
        binding?.apply {
            when(data) {
                is BaseState.Success<*> -> {
                    showProgress(
                        pbFortuneDescription,
                        contentFortuneDescription,
                        false
                    )

                    (data.resultData as List<CurrentFortuneDescriptionModel>).also {
                        runeList = it

                        viewPagerPresenter.setList(it)
                        viewPager.adapter = ViewPagerAdapter(viewPagerPresenter)

                        TabLayoutMediator(
                            tabs, viewPager
                        ) { tab, position ->
                            tab.text = fortuneFactory.createFortune(runeList[0].idFortune).description[position]
                        }.attach()
                    }
                }
                is BaseState.Error -> {
                    showProgress(
                        pbFortuneDescription,
                        contentFortuneDescription,
                        false
                    )

                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_LONG).show()
                }
                is BaseState.Loading -> {
                    showProgress(
                        pbFortuneDescription,
                        contentFortuneDescription,
                        true
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}