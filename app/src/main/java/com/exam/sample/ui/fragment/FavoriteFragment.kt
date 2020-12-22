package com.exam.sample.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.exam.sample.R
import com.exam.sample.adapter.StaggeredAdapter
import com.exam.sample.common.BaseFragment

import com.exam.sample.databinding.FragmentFavoriteBinding
import com.exam.sample.ui.DetailActivity
import com.exam.sample.utils.Status

import com.exam.sample.utils.extention.startActivityDetailExtras
import com.exam.sample.utils.toastMsg
import com.exam.sample.viewmodel.FavoriteViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FavoriteFragment() : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {

    override val TAG: String
        get() = this.javaClass.name
    override val layoutResID: Int
        get() = R.layout.fragment_favorite

    override val viewModel : FavoriteViewModel by sharedViewModel()
    private val adapter: StaggeredAdapter by lazy {
        StaggeredAdapter(itemListClick = { item ->
            requireActivity().startActivityDetailExtras(DetailActivity::class.java, item)
        })
    }

    private lateinit var layoutManager : StaggeredGridLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }

    fun onNewIntent(intent : Intent?) {

    }

    override fun init() {
        layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = adapter
        if (!adapter.hasObservers()) {
            adapter.setHasStableIds(true)
        }
    }

     override fun initObserver() {
        viewModel.apply {
            getFavoriteAll().observe(requireActivity(), Observer {
                if (it.isNotEmpty())
                    viewModel.getFavoriteInfoRequest(it)
                else
                    toastMsg(R.string.msgError)
            })

            isLoading.observe(requireActivity(), Observer {
                if (it) binding.progress.visibility = View.VISIBLE else binding.progress.visibility =
                    View.GONE
            })

            itemLiveData.observe(requireActivity(), Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { data -> adapter?.initItem(it.data.trendingItems) }
                    }

                    Status.ERROR -> {
                        toastMsg(it.message ?: "")
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}

