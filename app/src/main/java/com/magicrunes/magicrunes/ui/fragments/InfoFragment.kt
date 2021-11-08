package com.magicrunes.magicrunes.ui.fragments

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.magicrunes.magicrunes.MagicRunesApp
import com.magicrunes.magicrunes.R
import com.magicrunes.magicrunes.databinding.FragmentInfoBinding
import com.magicrunes.magicrunes.di.factory.ViewModelFactory
import com.magicrunes.magicrunes.ui.adapters.InfoFragmentAdapter
import com.magicrunes.magicrunes.ui.adapters.presenters.info.IInfoListPresenter
import com.magicrunes.magicrunes.ui.models.lists.InfoRuneModel
import com.magicrunes.magicrunes.ui.states.BaseState
import com.magicrunes.magicrunes.ui.viewmodels.InfoFragmentViewModel
import javax.inject.Inject

const val INFO_FRAGMENT_REQUEST_KEY = "info_fragment_request_key"
const val INFO_FRAGMENT_BUNDLE_KEY = "info_fragment_bundle_key"

class InfoFragment: BaseFragment<FragmentInfoBinding, InfoFragmentViewModel>() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var infoListPresenter: IInfoListPresenter

    var adapter: InfoFragmentAdapter? = null

    override fun getViewModelClass() = InfoFragmentViewModel::class.java

    override fun getViewBinding() = FragmentInfoBinding.inflate(layoutInflater)

    override fun setupViews() {
        initRecyclerView()

        infoListPresenter.itemClickListener = {itemView ->
            setFragmentResult(
                INFO_FRAGMENT_REQUEST_KEY,
                bundleOf(
                    INFO_FRAGMENT_BUNDLE_KEY to infoListPresenter.getList()[itemView.pos].idRune
                )
            )

            findNavController().navigate(R.id.action_info_to_info_description)
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.data.observe(viewLifecycleOwner) {
            it?.let {
                renderData(it)
            } ?: renderData(BaseState.Error(Error()))
        }

        viewModel.getRunesList()
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

    private fun initRecyclerView() {
        binding?.apply {
            rvInfo.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = InfoFragmentAdapter(infoListPresenter).apply {
                MagicRunesApp.appComponent.inject(this)
            }
            rvInfo.adapter = adapter
        }
    }

    override fun renderData(data: BaseState) {
        binding?.apply {
            when(data) {
                is BaseState.Success<*> -> {
                    showProgress(
                        pbInfoFragment,
                        rvInfo,
                        false
                    )

                    (data.resultData as List<InfoRuneModel>).also {
                        infoListPresenter.setList(it)
                        adapter?.notifyDataSetChanged()
                    }

                }
                is BaseState.Error -> {
                    showProgress(
                        pbInfoFragment,
                        rvInfo,
                        false
                    )

                    Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_LONG).show()
                }
                is BaseState.Loading -> {
                    showProgress(
                        pbInfoFragment,
                        rvInfo,
                        true
                    )
                }
            }
        }
    }
}