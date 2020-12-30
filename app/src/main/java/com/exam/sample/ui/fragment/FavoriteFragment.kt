package com.exam.sample.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.EmptyResultSetException
import com.exam.sample.R
import com.exam.sample.adapter.StaggeredAdapter
import com.exam.sample.common.BaseFragment

import com.exam.sample.databinding.FragmentFavoriteBinding
import com.exam.sample.livedata.EventObserver
import com.exam.sample.model.data.FavoriteInfo
import com.exam.sample.ui.DetailActivity
import com.exam.sample.ui.MainActivity
import com.exam.sample.utils.Const
import com.exam.sample.utils.Status

import com.exam.sample.utils.extention.startActivityDetailExtras
import com.exam.sample.utils.snackBarSimpleAlert
import com.exam.sample.utils.toastMsg
import com.exam.sample.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.detail_middle_view.*
import kotlinx.android.synthetic.main.fragment_favorite.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    fun onNewIntent(intent : Intent?) {
        adapter.clearItem()
        initData()
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

            isLoading.observe(requireActivity(), EventObserver {
                if (it) binding.progress.visibility = View.VISIBLE else binding.progress.visibility =
                    View.GONE
            })

            dbDataSuccessEvent.observe(requireActivity(), EventObserver {
                when (it.status) {
                    Status.SUCCESS -> {
                        val list = it.data?.data as List<FavoriteInfo>
                        if (list.isNotEmpty())
                            viewModel.getFavoriteInfoRequest(list)
                        else
                            snackBarSimpleAlert(R.string.noFavorites, R.string.ok, topBar )
                    }

                    Status.ERROR -> {
                        if (it.data?.data is EmptyResultSetException) {
                            Log.v(Const.LOG_TAG, "Query returned Empty")
                        } else {
                            toastMsg(it.message ?: "")
                        }

                    }
                }
            })

            itemLiveData.observe(requireActivity(), EventObserver {
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

    private fun initData() {
        viewModel.apply {
            getFavoriteAll()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}

