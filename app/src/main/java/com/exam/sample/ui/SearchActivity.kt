package com.exam.sample.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.exam.sample.R
import com.exam.sample.adapter.StaggeredAdapter
import com.exam.sample.ui.base.BaseActivity
import com.exam.sample.common.LoadMoreScrollListener
import com.exam.sample.databinding.ActivitySearchBinding
import com.exam.sample.livedata.EventObserver
import com.exam.sample.model.data.TrendingData
import com.exam.sample.utils.*
import com.exam.sample.utils.extention.hideKeyboard
import com.exam.sample.utils.extention.startActivityDetailExtras
import com.exam.sample.viewmodel.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import org.koin.android.viewmodel.ext.android.viewModel


class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>()
{
    override val TAG: String
        get() = this.javaClass.name
    override val layoutResID: Int
        get() = R.layout.activity_search

    override val viewModel : SearchViewModel by viewModel()

    private val adapter: StaggeredAdapter by lazy {
        StaggeredAdapter(itemListClick = { item ->
            startActivityDetailExtras(DetailActivity::class.java, item)
        })
    }
    private lateinit var layoutManager : StaggeredGridLayoutManager
    private lateinit var loadMoreScrollListener : LoadMoreScrollListener

    private var offset = Const.OFFSET_DEFAULT
    private var isAddLoadReady = false
    private var keyword:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        initScrollListener()
        initEditTextEvent()
    }



    override fun init() {
        layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = layoutManager
        adapter.setHasStableIds(true)
        binding.recyclerView.adapter = adapter

    }

    override fun initObserver() {
        viewModel.apply {
            isLoading.observe(this@SearchActivity, EventObserver {
                if (it) binding.progress.visibility = View.VISIBLE else binding.progress.visibility =
                    View.GONE
            })

            itemLiveData.observe(this@SearchActivity, EventObserver {
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

            itemLiveDataAdd.observe(this@SearchActivity, EventObserver {
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

    private fun initList(data : TrendingData) {
        adapter.initItem(data.trendingItems)
        loadMoreScrollListener.setParameters(data.pagination.total_count, isAddLoadReady)
        isAddLoadReady = false
    }

    private fun addList(data : TrendingData) {
        adapter.addItem(data.trendingItems)
        loadMoreScrollListener.setParameters(data.pagination.total_count, isAddLoadReady)
        isAddLoadReady = false
    }

    private fun initScrollListener() {
        loadMoreScrollListener = LoadMoreScrollListener(
            layoutManager,
            object : LoadMoreScrollListener.ScrollLoadMoreListener {
                override fun onLoadMore(page: Int, isAddReady: Boolean) {
                    offset = page
                    isAddLoadReady = isAddReady
                    viewModel.getSearch(keyword, offset, true)
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    binding.edtSearch.hideKeyboard()
                }

            })

        binding.recyclerView.addOnScrollListener(loadMoreScrollListener)

    }

    @SuppressLint("CheckResult")
    private fun initEditTextEvent() {

        binding.edtSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    offset = 0
                    viewModel.getSearch(binding.edtSearch.text.toString(), offset)
                    return true
                }
                return false
            }
        })


        viewModel.getSearchKeyChangeObserver()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it == keyword)
                    return@subscribe
                keyword = it
                if (it.isEmpty()) {
                    adapter.clearItem()
                } else {
                    offset = 0
                    viewModel.getSearch(keyword, offset)
                }
            }

        binding.edtSearch.requestFocus()

        //Databinding을 사용하지않을 경우 아래 이용
//        val searchKetTW = SearchKeyTextWatcher()
//        searchKetTW.getSearchKeyChangeObserver()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                if (it == keyword)
//                    return@subscribe
//                keyword = it
//                if (it.isEmpty()) {
//                    adapter.clearItem()
//                } else {
//                    offset = 0
//                    viewModel.getSearch(keyword, offset)
//                }
//            }
//        edtSearch.addTextChangedListener(searchKetTW)
//        edtSearch.requestFocus()

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
