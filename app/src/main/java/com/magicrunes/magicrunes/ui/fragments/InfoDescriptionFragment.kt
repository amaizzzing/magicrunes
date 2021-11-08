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
import com.magicrunes.magicrunes.ui.adapters.InfoDescriptionViewPagerAdapter
import com.magicrunes.magicrunes.ui.adapters.presenters.infodescription.IInfoDescriptionPresenter
import com.magicrunes.magicrunes.ui.models.lists.InfoRuneModel
import com.magicrunes.magicrunes.ui.states.BaseState
import com.magicrunes.magicrunes.ui.viewmodels.InfoDescriptionViewModel
import com.magicrunes.magicrunes.utils.getRecyclerView
import javax.inject.Inject

class InfoDescriptionFragment: BaseFragment<FragmentFortuneDescriptionBinding, InfoDescriptionViewModel>() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    @Inject
    lateinit var imageService: ImageService

    @Inject
    lateinit var resourceService: IResourceService

    @Inject
    lateinit var infoDescriptionPresenter: IInfoDescriptionPresenter

    override fun getViewModelClass(): Class<InfoDescriptionViewModel> =
        InfoDescriptionViewModel::class.java

    override fun getViewBinding(): FragmentFortuneDescriptionBinding? =
        FragmentFortuneDescriptionBinding.inflate(layoutInflater)

    override fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[getViewModelClass()]
    }

    override fun setupViews() {
        configureViewPager()
        configureBottomSheetLayout()

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun observeData() {
        super.observeData()
        viewModel.data.observe(viewLifecycleOwner) {
            it?.let {
                renderData(it)
            } ?: renderData(BaseState.Error(Error()))
        }
        setFragmentResultListener(INFO_FRAGMENT_REQUEST_KEY) { _, bundle ->
            val id = bundle.getLong(INFO_FRAGMENT_BUNDLE_KEY)
            viewModel.getRuneDescription(id)
        }
    }

    override fun renderData(data: BaseState) {
        binding?.apply {
            when(data) {
                is BaseState.Success<*> -> {
                    showProgress(
                        pbFortuneDescription,
                        contentFortuneDescription,
                        false
                    )

                    (data.resultData as InfoRuneModel).also {
                        val tabsDescription = mutableListOf("Общее описание", "Прямое положение", "Перевернутое положение")
                        infoDescriptionPresenter.setList(
                            if (it.revDescription.isEmpty()) {
                                tabsDescription.removeAt(2)

                                listOf(
                                    it.mainDescription,
                                    it.avDescription
                                )
                            } else {
                                listOf(
                                    it.mainDescription,
                                    it.avDescription,
                                    it.revDescription
                                )
                            }
                        )

                        viewPager.adapter = InfoDescriptionViewPagerAdapter(infoDescriptionPresenter)
                        TabLayoutMediator(
                            tabs, viewPager
                        ) { tab, position ->
                            tab.text = tabsDescription[position]
                        }.attach()

                        imageLoader.loadInto(
                            imageService.getImageResource(it.imageName),
                            binding!!.imageRuneFotuneDescription
                        )

                        (requireActivity() as MainActivity).binding.mainToolbar.title = it.runeName
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MagicRunesApp.appComponent.inject(this)
    }

    private fun configureViewPager() {
        binding?.apply {
            viewPager.getRecyclerView()?.apply {
                isNestedScrollingEnabled = false
                overScrollMode = View.OVER_SCROLL_NEVER
            }
            viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewPager.adapter?.notifyItemChanged(position)
                }
            })
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

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}