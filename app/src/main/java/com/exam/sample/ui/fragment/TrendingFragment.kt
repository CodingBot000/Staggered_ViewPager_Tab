package com.exam.sample.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.exam.sample.R
import com.exam.sample.adapter.StaggeredAdapter
import com.exam.sample.common.BaseFragment
import com.exam.sample.databinding.FragmentTrendingBinding
import com.exam.sample.model.data.TrendingData
import com.exam.sample.ui.DetailActivity
import com.exam.sample.utils.*
import com.exam.sample.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class TrendingFragment() : BaseFragment<FragmentTrendingBinding, MainViewModel>(), SwipeRefreshLayout.OnRefreshListener {
    override val TAG: String
        get() = this.javaClass.name
    override val layoutResID: Int
        get() = R.layout.fragment_trending

    override val viewModel : MainViewModel by sharedViewModel()

    private val adapter: StaggeredAdapter by lazy {
        StaggeredAdapter(itemListClick = { item ->
            requireActivity().startActivityDetailExtras(DetailActivity::class.java, item)
        })
    }

    private lateinit var layoutManager : StaggeredGridLayoutManager

    private var totalCnt = 0
    private var offset = Const.OFFSET_DEFAULT
    private var isAddLoadReady = false
    private var isFirstLoad = true
    private var visibleThreshold = Const.LIMIT
    private var lastVisibleItem = 0
    private var totalItemCount = 0

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

            isLoading.observe(requireActivity(), Observer {
                if (it) binding.progress.visibility = View.VISIBLE else binding.progress.visibility = View.GONE


            })

            itemLiveData.observe(requireActivity(), Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        isAddLoadReady = false
                        it.data?.let { data -> initList(data) }
                    }

                    Status.ERROR -> {
                        ToastMsg(it.message ?: "")
                    }
                }


            })

            itemLiveDataAdd.observe(requireActivity(), Observer {

                when (it.status) {
                    Status.SUCCESS -> {
                        isAddLoadReady = false
                        it.data?.let { data -> addList(data) }
                    }

                    Status.ERROR -> {
                        ToastMsg(it.message ?: "")
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
        totalCnt = data.pagination.total_count
        isAddLoadReady = false
    }

    private fun addList(data : TrendingData) {
        adapter.addItem(data.trendingItems)
        totalCnt = data.pagination.total_count
        isAddLoadReady = false
    }

    private fun initScrollListener() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                totalItemCount = layoutManager.itemCount
                lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPositions(null)[1]

                if (!isAddLoadReady && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (isFirstLoad) {
                        isFirstLoad = false
                        return
                    }

                    if (offset >= totalCnt)
                        return

                    offset += Const.LIMIT

                    if (offset > totalCnt)
                        offset = totalCnt

                    viewModel.getTrendingData(offset, true)
                    isAddLoadReady = true

                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()

    }

}
