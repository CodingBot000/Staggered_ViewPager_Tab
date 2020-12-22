package com.exam.sample.ui


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.exam.sample.R
import com.exam.sample.adapter.StaggeredAdapter
import com.exam.sample.common.BaseActivity
import com.exam.sample.databinding.ActivitySearchBinding
import com.exam.sample.model.data.TrendingData
import com.exam.sample.utils.*
import com.exam.sample.utils.extention.hideKeyboard
import com.exam.sample.utils.extention.startActivityDetailExtras
import com.exam.sample.viewmodel.SearchViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


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
    private var totalCnt = 0
    private var offset = Const.OFFSET_DEFAULT
    private var isAddLoadReady = false
    private var isFirstLoad = true
    private var visibleThreshold = Const.LIMIT
    private var lastVisibleItem = 0
    private var totalItemCount = 0
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
            isLoading.observe(this@SearchActivity, Observer {
                if (it) binding.progress.visibility = View.VISIBLE else binding.progress.visibility =
                    View.GONE
            })

            itemLiveData.observe(this@SearchActivity, Observer {
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

            itemLiveDataAdd.observe(this@SearchActivity, Observer {
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
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                edtSearch.hideKeyboard()
            }
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

                    viewModel.getSearch(keyword, offset, true)
                    isAddLoadReady = true
                }
            }
        })
    }

    @SuppressLint("CheckResult")
    private fun initEditTextEvent() {

        edtSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    offset = 0
                    viewModel.getSearch(edtSearch.text.toString(), offset)
                    return true
                }
                return false
            }
        })

        val searchKetTW = SearchKeyTextWatcher()
        searchKetTW.getSearchKeyChangeObserver()
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
        edtSearch.addTextChangedListener(searchKetTW)
        edtSearch.requestFocus()
    }

    inner class SearchKeyTextWatcher: TextWatcher {
        private val textChangeSubject = PublishSubject.create<String>()

        init {
            textChangeSubject.onNext("")
        }

        override fun afterTextChanged(s: Editable?) {
            if (s.isNullOrEmpty())
                textChangeSubject.onNext("")
            else
                textChangeSubject.onNext(s.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {}

        fun getSearchKeyChangeObserver(): Observable<String> {
            return textChangeSubject.debounce(Const.EMIT_INTERVAL, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
