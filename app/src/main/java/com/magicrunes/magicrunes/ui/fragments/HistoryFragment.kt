package com.magicrunes.magicrunes.ui.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.data.enums.HistoryType
import com.magicrunes.magicrunes.databinding.FragmentHistoryBinding
import com.magicrunes.magicrunes.di.factory.ViewModelFactory
import com.magicrunes.magicrunes.ui.adapters.HistoryFragmentAdapter
import com.magicrunes.magicrunes.ui.adapters.presenters.history.IHistoryListPresenter
import com.magicrunes.magicrunes.ui.dialogs.AddCommentDialogFragment
import com.magicrunes.magicrunes.ui.models.lists.HistoryModel
import com.magicrunes.magicrunes.ui.states.BaseState
import com.magicrunes.magicrunes.ui.viewmodels.HistoryFragmentViewModel
import javax.inject.Inject

const val HISTORY_RUNE_REQUEST_KEY = "history_rune_description"
const val HISTORY_RUNE_BUNDLE = "history_rune_bundle"

class HistoryFragment:
    BaseFragment<FragmentHistoryBinding,
    HistoryFragmentViewModel>(),
    AddCommentDialogFragment.OnCloseClick
{
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var historyListPresenter: IHistoryListPresenter

    var adapter: HistoryFragmentAdapter? = null

    private var commentClickPosition: Int = 0

    override fun getViewModelClass() = HistoryFragmentViewModel::class.java

    override fun getViewBinding() = FragmentHistoryBinding.inflate(layoutInflater)

    override fun setupViews() {
        initRecyclerView()
        initListeners()
        AddCommentDialogFragment.onCloseImpl = this@HistoryFragment
    }

    override fun observeData() {
        super.observeData()
        viewModel.data.observe(viewLifecycleOwner) {
            it?.let {
                renderData(it)
            } ?: renderData(BaseState.Error(Error()))
        }

        viewModel.getHistory(HistoryType.AllHistory)
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
                        pbHistoryFragment,
                        rvHistory,
                        false
                    )

                    (data.resultData as List<HistoryModel>).also {
                        historyListPresenter.setList(it)
                        adapter?.notifyDataSetChanged()
                    }

                }
                is BaseState.Error -> {
                    showProgress(
                        pbHistoryFragment,
                        rvHistory,
                        false
                    )

                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_LONG).show()
                }
                is BaseState.Loading -> {
                    showProgress(
                        pbHistoryFragment,
                        rvHistory,
                        true
                    )
                }
            }
        }
    }

    private fun initListeners() {
        binding?.apply {
            chipRuneDay.setOnClickListener {
                if (chipRuneDay.isChecked)
                viewModel.getHistory(HistoryType.RuneType)
            }
            chipFortune.setOnClickListener {
                viewModel.getHistory(HistoryType.FortuneType)
            }
            chipAllHistory.setOnClickListener {
                viewModel.getHistory(HistoryType.AllHistory)
            }
        }

        historyListPresenter.apply {
            itemClickListener = { historyView ->
                when(historyListPresenter.getList()[historyView.pos].historyType) {
                    HistoryType.FortuneType -> { showFortuneDescriptionFragment(historyView.pos) }
                    HistoryType.RuneType -> { showRuneDescriptionFragment(historyView.pos) }
                }
            }

            commentClickListener = { historyView ->
                commentClickPosition = historyView.pos
                historyListPresenter.getList()[historyView.pos].apply {
                    showAddCommentDialog(dateInMillis)
                }
            }
        }
    }

    private fun showAddCommentDialog(historyDate: Long) {
        AddCommentDialogFragment
            .newInstance(historyDate)
            .show(childFragmentManager, COMMENT_DIALOG_TAG)
    }

    private fun showFortuneDescriptionFragment(runePos: Int) {
        setFragmentResult(
            DESCRIPTION_REQUEST_KEY,
            bundleOf(DESCRIPTION_BUNDLE_KEY to historyListPresenter.getList()[runePos].dateInMillis)
        )

        findNavController().navigate(R.id.action_history_to_fortune_description)
    }

    private fun showRuneDescriptionFragment(runePos: Int) {
        setFragmentResult(
            HISTORY_RUNE_REQUEST_KEY,
            bundleOf(HISTORY_RUNE_BUNDLE to historyListPresenter.getList()[runePos].dateInMillis)
        )

        findNavController().navigate(R.id.action_history_to_rune_description)
    }

    private fun initRecyclerView() {
        binding?.apply {
            rvHistory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = HistoryFragmentAdapter(historyListPresenter).apply {
                MagicRunesApp.appComponent.inject(this)
            }
            rvHistory.adapter = adapter
        }
    }

    override fun onCloseAddCommentDialog(newComment: String) {
        historyListPresenter.getList()[commentClickPosition].comment = newComment
        adapter?.notifyItemChanged(commentClickPosition)
    }
}