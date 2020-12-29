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
import com.exam.sample.livedata.EventObserver
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
            isLoading.observe(this@DetailActivity, EventObserver {
                if (it) binding.progress.visibility =
                    View.VISIBLE else binding.progress.visibility =
                    View.GONE
            })

            getFavorite(interactionData.userId).observe(this@DetailActivity, Observer {
                checkBoxFavorite.isChecked = it != null
            })

            dbDataSuccessEvent.observe(this@DetailActivity, EventObserver {
                when (it.status) {
                    Status.SUCCESS -> {

                        Log.v(Const.LOG_TAG, "${it.data?.flag} $it")
                    }

                    Status.ERROR -> {
                        toastMsg(it.message ?: "")
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
            // 최초 getFavorite을 통해서 결과를 받아와서 좋아요 체크를 해주는데 이때 아래 changeListener가 불림.
            // 어차피 insert가     @Insert(onConflict = REPLACE) 이기 때문에
            // 그냥 두었음. 만약 필요하다면 boolean변수로 제어하던가, 최초에 setOnCheckedChangeListener를 null로 해제했다 재등록하면됨
            checkBoxFavorite.setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    viewModel.insertFavorite(
                        FavoriteInfo(
                            interactionData.userId,
                            interactionData.urlSmall,
                            interactionData.type
                        )
                    )

                    makeSnackBar(R.string.favorite_check, R.string.favorite_uncheck)
                }
                else {
                    viewModel.removeFavorite(
                        FavoriteInfo(
                            interactionData.userId,
                            interactionData.urlSmall,
                            interactionData.type
                        )
                    )
                    makeSnackBar(R.string.favorite_uncheck, R.string.favorite_check)
                }
            }

            btnSend.setOnClickListener {

               shareUrl(interactionData.urlEmbeded)
            }

            btnMore.setOnClickListener {
                toastMsg(R.string.msgMore)
            }
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
