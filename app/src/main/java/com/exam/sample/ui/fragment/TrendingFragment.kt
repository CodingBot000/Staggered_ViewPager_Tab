package com.exam.sample.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.exam.sample.R
import com.exam.sample.adapter.StaggeredAdapter
import com.exam.sample.ui.base.BaseFragment
import com.exam.sample.common.LoadMoreScrollListener
import com.exam.sample.databinding.FragmentTrendingBinding
import com.exam.sample.livedata.EventObserver
import com.exam.sample.model.data.TrendingData
import com.exam.sample.ui.DetailActivity
import com.exam.sample.utils.*
import com.exam.sample.utils.extention.startActivityDetailExtras
import org.koin.android.viewmodel.ext.android.viewModel

import com.exam.sample.viewmodel.TrendingViewModel


class TrendingFragment  : BaseFragment<FragmentTrendingBinding, TrendingViewModel>(), SwipeRefreshLayout.OnRefreshListener {
    override val TAG: String
        get() = this.javaClass.name
    override val layoutResID: Int
        get() = R.layout.fragment_trending

    override val viewModel : TrendingViewModel by viewModel()

    private val adapter: StaggeredAdapter by lazy {
        StaggeredAdapter(itemListClick = { item ->
            requireActivity().startActivityDetailExtras(DetailActivity::class.java, item)
        })
    }

    private lateinit var layoutManager : StaggeredGridLayoutManager
    private lateinit var loadMoreScrollListener : LoadMoreScrollListener

    private var offset = Const.OFFSET_DEFAULT
    private var isAddLoadReady = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        initData()
        initScrollListener()

        return binding.root
    }

    override fun init() {
        binding.swipeLayout.setOnRefreshListener(this)
        // by lazy로 변수정의와 함께 할당할 경우 열어 탭을 오고가면 already attached오류 발생합니다.
        layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = layoutManager

        if (!adapter.hasObservers()) {
            adapter.setHasStableIds(true)
        }

        binding.recyclerView.itemAnimator = null
        binding.recyclerView.adapter = adapter
    }

    override fun onRefresh() {
        offset = Const.OFFSET_DEFAULT
        adapter.clearItem()
        viewModel.getTrendingData(offset)
        binding.swipeLayout.isRefreshing = false
    }

    override fun initObserver() {
        viewModel.apply {

            isLoading.observe(requireActivity(), EventObserver {
                if (it) binding.progress.visibility = View.VISIBLE else binding.progress.visibility = View.GONE


            })

            itemLiveData.observe(requireActivity(), EventObserver {
                when (it.status) {
                    Status.SUCCESS -> {
                        isAddLoadReady = false
                        it.data?.let { data -> initList(data) }
                    }

                    Status.ERROR -> {
                        toastMsg(it.message ?: "")
                    }
                }

            })

            itemLiveDataAdd.observe(requireActivity(), EventObserver {

                when (it.status) {
                    Status.SUCCESS -> {
                        isAddLoadReady = false
                        it.data?.let { data -> addList(data) }
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
            getTrendingData(offset)
        }
    }

    private fun initList(data : TrendingData) {
        adapter.initItem(data.trendingItems)
        isAddLoadReady = false
        loadMoreScrollListener.setParameters(data.pagination.total_count, isAddLoadReady)
    }

    private fun addList(data : TrendingData) {
        adapter.addItem(data.trendingItems)
        isAddLoadReady = false
        loadMoreScrollListener.setParameters(data.pagination.total_count, isAddLoadReady)
    }

    private fun initScrollListener() {
        loadMoreScrollListener = LoadMoreScrollListener(
            layoutManager,
            object : LoadMoreScrollListener.ScrollLoadMoreListener {
                override fun onLoadMore(page: Int, isAddReady: Boolean) {
                    offset = page
                    isAddLoadReady = isAddReady
                    viewModel.getTrendingData(offset, "", true)
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) { }

            })

        binding.recyclerView.addOnScrollListener(loadMoreScrollListener)

    }

    override fun onDestroy() {
        super.onDestroy()

    }



}
