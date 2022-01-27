package com.magicrunes.magicrunes.ui.fragments

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.data.services.image.IImageLoader
import com.magicrunes.magicrunes.data.services.image.ImageService
import com.magicrunes.magicrunes.data.services.resource.IResourceService
import com.magicrunes.magicrunes.databinding.FragmentMainBinding
import com.magicrunes.magicrunes.di.factory.ViewModelFactory
import com.magicrunes.magicrunes.ui.MainActivity
import com.magicrunes.magicrunes.ui.dialogs.AddCommentDialogFragment
import com.magicrunes.magicrunes.ui.models.RuneOfTheDayModel
import com.magicrunes.magicrunes.ui.states.BaseState
import com.magicrunes.magicrunes.ui.viewmodels.MainFragmentViewModel
import com.magicrunes.magicrunes.ui.widget.RuneOfTheDayWidget
import com.magicrunes.magicrunes.utils.animationUtils.FabAnimatorHelper
import com.magicrunes.magicrunes.utils.isGone
import com.magicrunes.magicrunes.utils.setGone
import com.magicrunes.magicrunes.utils.setVisible
import javax.inject.Inject

const val COMMENT_DIALOG_TAG = "AddCommentDialogFragment"

class MainFragment:
    BaseFragment<FragmentMainBinding,
    MainFragmentViewModel>(),
    AddCommentDialogFragment.OnCloseClick
{
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var fabAnimatorHelper: FabAnimatorHelper

    @Inject
    lateinit var imageLoader: IImageLoader<ImageView>

    @Inject
    lateinit var imageService: ImageService

    @Inject
    lateinit var resourceService: IResourceService

    private var startXFab = 0.0f
    private var startYFab = 0.0f

    private var currentRuneOfTheDayId: Long = -1L

    private var historyDate: Long = -1L

    override fun getViewModelClass() = MainFragmentViewModel::class.java

    override fun getViewBinding() = FragmentMainBinding.inflate(layoutInflater)

    override fun setupViews() {
        configureBottomSheetLayout()

         binding?.apply {
             fabAnimatorHelper.setFabButton(fabMainFragment)

             fabMainFragment.setOnClickListener {
                 if (startXFab == 0.0f)
                     startXFab = it.x
                 if (startYFab == 0.0f)
                     startYFab = it.y
                 fabAnimatorHelper.apply {
                     this.fab = it as FloatingActionButton
                     this.startX = it.x
                     this.startY = it.y
                     this.endX = resourceService.getDisplayMetrics().widthPixels / 2f
                     this.endY = it.y
                     this.isArcPath = true
                     this.duration = 600
                     this.startAlpha = 1.0f
                     this.endAlpha = 0.0f
                     this.startScale = 1f
                     this.endScale = 3f
                 }
                 fabAnimatorHelper.getAnimator(true).start()

                 AddCommentDialogFragment.onCloseImpl = this@MainFragment

                 showAddCommentDialog()
             }
         }

        binding?.apply {
            arrowMainDescription.setOnClickListener { arrowImage ->
                mainDescriptionRuneMainFragment.let { mainDescription ->
                    if (mainDescription.isGone()) {
                        imageLoader.loadInto(
                            R.drawable.arrow_up_round,
                            arrowImage as ImageView
                        )

                        mainDescription.setVisible()
                    } else {
                        imageLoader.loadInto(
                            R.drawable.arrow_down_round,
                            arrowImage as ImageView
                        )

                        mainDescription.setGone()
                    }

                    mainDescription.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.nav_default_enter_anim))
                }
            }
        }
    }

    private fun showAddCommentDialog() {
        AddCommentDialogFragment
            .newInstance()
            .show(childFragmentManager, COMMENT_DIALOG_TAG)
    }

    private fun updateWidgets() {
        val intent = Intent(requireContext(), RuneOfTheDayWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

        val ids = AppWidgetManager.getInstance(requireContext()).getAppWidgetIds(
            ComponentName(
                requireContext(),
                RuneOfTheDayWidget::class.java
            )
        )
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        requireContext().sendBroadcast(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MagicRunesApp.appComponent.inject(this)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[getViewModelClass()]
    }

    private fun configureBottomSheetLayout() {
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<View>((binding?.bottomSheet) as View)

        behavior.apply {
            peekHeight = resourceService.getDisplayMetrics().heightPixels / 3
            isFitToContents = false
            halfExpandedRatio = 0.5f
            state = BottomSheetBehavior.STATE_HALF_EXPANDED
            isHideable = false

            addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        this@apply.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
    }

    override fun renderData(data: BaseState) {
        binding?.apply {
            when(data) {
                is BaseState.Success<*> -> {
                    showProgress(
                        pbMainFragment,
                        runeContentMainFragment,
                        false
                    )

                    (data.resultData as RuneOfTheDayModel).also {
                        currentRuneOfTheDayId = it.idRune

                        nameRuneMainFragment.text = it.name
                        mainDescriptionRuneMainFragment.text = it.description
                        avrevDescriptionRuneMainFragment.text = it.avrevDescription

                        imageLoader.loadInto(
                            imageService.getRuneBitmap(
                                it.image,
                                it.isReverse
                            ),
                            runeImageMainFragment as ImageView
                        )

                        avrevNameMainFragment.text =
                            if (it.isReverse) {
                                resources.getText(R.string.rev_state)
                            } else {
                                resources.getText(R.string.av_state)
                            }

                        updateWidgets()
                    }
                }
                is BaseState.Error -> {
                    showProgress(
                        pbMainFragment,
                        runeContentMainFragment,
                        false
                    )

                    Toast.makeText(requireContext(), "ERROR", LENGTH_LONG).show()
                }
                is BaseState.Loading -> {
                    showProgress(
                        pbMainFragment,
                        runeContentMainFragment,
                        true
                    )
                }
            }
        }
    }

    override fun observeData() {
        super.observeData()

        viewModel.data.observe(viewLifecycleOwner) {
            it?.let {
                renderData(it)
            } ?: renderData(BaseState.Error(Error()))
        }

        setFragmentResultListener(HISTORY_RUNE_REQUEST_KEY) { _, bundle ->
            historyDate = bundle.getLong(HISTORY_RUNE_BUNDLE)
            viewModel.getRuneDescription(historyDate)
            (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        viewModel.getRuneOfTheDay()
    }

    override fun onCloseAddCommentDialog(newComment: String) {
        binding?.apply {
            fabMainFragment.also {
                fabAnimatorHelper.apply {
                    fab = it
                    startX = resourceService.getDisplayMetrics().widthPixels / 2f
                    startY = it.y
                    endX = startXFab
                    endY = startYFab
                    isArcPath = true
                    duration = 600
                    startScale = 4.0f
                    endScale = 1f
                    startAlpha = 0.0f
                    endAlpha = 1.0f
                }
                fabAnimatorHelper.getAnimator(true).start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }
}