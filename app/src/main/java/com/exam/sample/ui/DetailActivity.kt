package com.exam.sample.ui


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.exam.sample.R
import com.exam.sample.adapter.StaggeredAdapter
import com.exam.sample.common.BaseActivity

import com.exam.sample.databinding.ActivityDetailBinding
import com.exam.sample.model.data.DBResultData
import com.exam.sample.model.data.FavoriteInfo
import com.exam.sample.model.data.InteractionData
import com.exam.sample.model.data.TrendingDetail
import com.exam.sample.utils.*
import com.exam.sample.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.detail_middle_view.*
import org.koin.android.viewmodel.ext.android.viewModel


class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>()
{

    override val TAG: String
        get() = this.javaClass.name
    override val layoutResID: Int
        get() = R.layout.activity_detail

    override val viewModel : DetailViewModel by viewModel()

    private val adapter: StaggeredAdapter by lazy {
        StaggeredAdapter(itemListClick = { item ->
            startActivityDetailExtras(DetailActivity::class.java, item)
        })
    }
    private lateinit var layoutManager : StaggeredGridLayoutManager

    private lateinit var interactionData: InteractionData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        initData()
    }

    private fun getIntentData() {
        interactionData = intent.getParcelableExtra<InteractionData>(Const.EXTRA_KEY_INTERACTION)
        binding.interactionData = interactionData
    }

    override fun init() {
        getIntentData()
        layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = layoutManager
        adapter.setHasStableIds(true)
        binding.recyclerView.adapter = adapter
        initClickEvent()
    }


    override fun initObserver() {
        viewModel.apply {
            isLoading.observe(this@DetailActivity, Observer {
                if (it) binding.progress.visibility = View.VISIBLE else binding.progress.visibility =
                    View.GONE
            })

            getFavorite(interactionData.userId).observe(this@DetailActivity, Observer {
                checkBoxFavorite.isChecked = it != null
            })

            dbDataSuccessEvent.observe(this@DetailActivity, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        Log.v(Const.LOG_TAG, "${(it.data as DBResultData).flag} $it")
                    }

                    Status.ERROR -> {
                        ToastMsg(it.message ?: "")
                    }
                }
            })

            itemLiveData.observe(this@DetailActivity, Observer {

                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { data -> adapter.initItem(it.data.trendingItems) }
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
            getDetailData(interactionData.userId)
        }
    }

    private fun initClickEvent() {
        btnBack.setOnClickListener{
            finish()
        }

        ll_middleView.apply {
            checkBoxFavorite.setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    viewModel.insertFavorite(
                        FavoriteInfo(
                            interactionData.userId,
                            interactionData.urlSmall,
                            interactionData.type
                        )
                    )
                    ToastMsg(R.string.favorite_check)
                }
                else {
                    viewModel.removeFavorite(
                        FavoriteInfo(
                            interactionData.userId,
                            interactionData.urlSmall,
                            interactionData.type
                        )
                    )
                    ToastMsg(R.string.favorite_uncheck)
                }
            }

            btnSend.setOnClickListener {

               shareUrl(interactionData.urlEmbeded)
            }

            btnMore.setOnClickListener {
                ToastMsg(R.string.msgMore)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
