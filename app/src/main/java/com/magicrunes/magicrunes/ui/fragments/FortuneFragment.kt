package com.magicrunes.magicrunes.ui.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.databinding.FragmentFortuneBinding
import com.magicrunes.magicrunes.di.factory.ViewModelFactory
import com.magicrunes.magicrunes.ui.adapters.FortuneDiffUtilCallback
import com.magicrunes.magicrunes.ui.adapters.FortuneFragmentAdapter
import com.magicrunes.magicrunes.ui.adapters.presenters.fortune.IFortuneListPresenter
import com.magicrunes.magicrunes.ui.dialogs.FORTUNE_DESCRIPTION_DIALOG_TAG
import com.magicrunes.magicrunes.ui.dialogs.FortuneDescriptionDialog
import com.magicrunes.magicrunes.ui.models.lists.FortuneModel
import com.magicrunes.magicrunes.ui.states.BaseState
import com.magicrunes.magicrunes.ui.viewmodels.FortuneFragmentViewModel
import javax.inject.Inject

const val REQUEST_KEY = "fortuneStrategy"
const val BUNDLE_KEY = "id_model"

class FortuneFragment: BaseFragment<FragmentFortuneBinding, FortuneFragmentViewModel>() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var fortuneListPresenter: IFortuneListPresenter

    var adapter: FortuneFragmentAdapter? = null

    override fun getViewModelClass() = FortuneFragmentViewModel::class.java

    override fun getViewBinding() = FragmentFortuneBinding.inflate(layoutInflater)

    override fun setupViews() {
        initRecyclerView()

        fortuneListPresenter.modelClickListener = { position ->
            setFragmentResult(
                REQUEST_KEY,
                bundleOf(BUNDLE_KEY to fortuneListPresenter.getList()[position].id)
            )

            findNavController().navigate(R.id.action_fortune_to_currentfortune)
        }

        fortuneListPresenter.favouriteClickListener = { position ->
            changeFavouritePosition(position)
        }
        fortuneListPresenter.descriptionClickListener = { position ->
            FortuneDescriptionDialog
                .newInstance(fortuneListPresenter.getList()[position].id)
                .show(childFragmentManager, FORTUNE_DESCRIPTION_DIALOG_TAG)
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.data.observe(viewLifecycleOwner) {
            it?.let {
                renderData(it)
            } ?: renderData(BaseState.Error(Error()))
        }

        viewModel.getFortuneList()
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

    override fun renderData(data: BaseState) {
        binding?.apply {
            when(data) {
                is BaseState.Success<*> -> {
                    showProgress(
                        pbFortuneFragment,
                        rvFortune,
                        false
                    )

                    (data.resultData as List<FortuneModel>).also {
                        fortuneListPresenter.setList(it)
                        adapter?.notifyDataSetChanged()
                    }

                }
                is BaseState.Error -> {
                    showProgress(
                        pbFortuneFragment,
                        rvFortune,
                        false
                    )

                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_LONG).show()
                }
                is BaseState.Loading -> {
                    showProgress(
                        pbFortuneFragment,
                        rvFortune,
                        true
                    )
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding?.apply {
            rvFortune.layoutManager = GridLayoutManager(context, 2)
            adapter = FortuneFragmentAdapter(fortuneListPresenter).apply {
                MagicRunesApp.appComponent.inject(this)
            }
            rvFortune.adapter = adapter
        }
    }

    private fun changeFavouritePosition(position: Int) {
        val oldFortuneList = fortuneListPresenter.getList()
        val changedId = oldFortuneList[position].id

        var newList = oldFortuneList.map { it.copy() }

        newList.find { it.id == changedId }?.let {
            it.isFavourite = !it.isFavourite
            viewModel.updateFavouriteFortune(oldFortuneList[position].id, it.isFavourite)
        }
        newList = newList
            .sortedWith(compareBy<FortuneModel> { it.isFavourite }.thenBy { it.dateInMillis }.thenByDescending { it.currentStrategy?.visibleRuneList?.size })
            .reversed()

        diffUtilUpdates(newList)
    }

    private fun diffUtilUpdates(list: List<FortuneModel>) {
        adapter?.let {
            val fortuneDiffUtilCallback = FortuneDiffUtilCallback(fortuneListPresenter.getList(), list)
            val diffResult = DiffUtil.calculateDiff(fortuneDiffUtilCallback, true)
            fortuneListPresenter.setList(list)
            diffResult.dispatchUpdatesTo(it)
        }
    }
}