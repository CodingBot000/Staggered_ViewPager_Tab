package com.exam.sample.ui


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.EmptyResultSetException
import com.exam.sample.R
import com.exam.sample.adapter.StaggeredAdapter
import com.exam.sample.ui.base.BaseActivity
import com.exam.sample.databinding.ActivityDetailBinding
import com.exam.sample.livedata.EventObserver
import com.exam.sample.livedata.RxEventBus
import com.exam.sample.model.data.FavoriteInfo
import com.exam.sample.model.data.InteractionData
import com.exam.sample.utils.Const
import com.exam.sample.utils.Status
import com.exam.sample.utils.extention.startActivityDetailExtras
import com.exam.sample.utils.shareUrl
import com.exam.sample.utils.toastMsg
import com.exam.sample.viewmodel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
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

    override fun onResume() {
        super.onResume()
        // Detail은 액티비티가 쌓이도록 구현되어서 뒤로가기로 스택의 기존 뷰로 돌아갔을때 최신상태를 반영하도록 한다
        viewModel.getFavorite(interactionData.userId)
    }

    private fun getIntentData() {
        interactionData = intent.getParcelableExtra<InteractionData>(Const.EXTRA_KEY_INTERACTION)!!
        binding.interactionData = interactionData
    }

    override fun init() {
        getIntentData()
        layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerView.layoutManager = layoutManager
        adapter.setHasStableIds(true)
        binding.recyclerView.adapter = adapter

    }


    override fun initObserver() {
        viewModel.apply {
            isLoading.observe(this@DetailActivity, EventObserver {
                if (it) binding.progress.visibility =
                    View.VISIBLE else binding.progress.visibility =
                    View.GONE
            })

            dbEvent.observe(this@DetailActivity, EventObserver {
                when (it.status) {
                    Status.SUCCESS -> {
                        if (it.data?.flag == Const.DB_SELECT) {
                            checkBoxFavorite.isChecked = it.data.data != null
                        } else {
                            if (it.data?.flag == Const.DB_INSERT)
                                makeSnackBar(R.string.favorite_check, R.string.favorite_uncheck)
                            else
                                makeSnackBar(R.string.favorite_uncheck, R.string.favorite_check)
                        }
                    }

                    Status.ERROR -> {
                        if (it.data?.data is EmptyResultSetException) {
                            Log.v(Const.LOG_TAG, "Query returned Empty")
                            checkBoxFavorite.isChecked = false
                        } else {
                            toastMsg(it.message ?: "")
                        }

                    }
                }
            })

            itemLiveData.observe(this@DetailActivity, EventObserver {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let { data -> adapter.initItem(it.data.trendingItems) }
                    }

                    Status.ERROR -> {
                        toastMsg(it.message ?: "")
                    }
                }
            })

            favoriteCheckEvent.observe(this@DetailActivity, EventObserver {
                if (it) {
                    insertFavorite(
                        FavoriteInfo(
                            interactionData.userId,
                            interactionData.urlSmall,
                            interactionData.type
                        )
                    )
                }
                else {
                    removeFavorite(
                        FavoriteInfo(
                            interactionData.userId,
                            interactionData.urlSmall,
                            interactionData.type
                        )
                    )
                }
                RxEventBus.sendEvent(Const.RX_EVENT_REFRESH_FAVORITE)
            })

            btnSimpleEvent.observe(this@DetailActivity, EventObserver {
                when (it) {
                    Const.BTN_EVENT_SEND -> {
                        shareUrl(interactionData.urlEmbeded)
                    }
                    Const.BTN_EVENT_SHARE -> {
                        toastMsg(R.string.msgMore)
                    }
                    Const.BTN_EVENT_BACK -> {
                        finish()
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


    private fun makeSnackBar(msgResId: Int, btnNameResId:Int) {
        val snackbar = Snackbar.make(
            topBar,
            msgResId,
            Snackbar.LENGTH_SHORT
        )
        snackbar.setAction(getString(btnNameResId)) {
            checkBoxFavorite.performClick()
            snackbar.dismiss()
        }
        snackbar.show()
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
