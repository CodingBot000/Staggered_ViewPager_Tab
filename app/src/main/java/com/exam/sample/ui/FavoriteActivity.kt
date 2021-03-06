package com.exam.sample.ui


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import com.exam.sample.R
import com.exam.sample.adapter.ScreenSlideViewPagerAdapter
import com.exam.sample.ui.base.BaseActivity
import com.exam.sample.databinding.ActivityFavoriteBinding
import com.exam.sample.livedata.RxEventBus
import com.exam.sample.ui.fragment.FavoriteFragment
import com.exam.sample.ui.fragment.UploadFragment
import com.exam.sample.utils.Const
import com.exam.sample.utils.animation.DepthPageTransformer
import com.exam.sample.utils.extention.setImageSize
import com.exam.sample.viewmodel.FavoriteViewModel
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.viewmodel.ext.android.viewModel

import kotlin.math.abs



class FavoriteActivity :   BaseActivity<ActivityFavoriteBinding, FavoriteViewModel>() {
    override val TAG: String
        get() = this.javaClass.name
    override val layoutResID: Int
        get() = R.layout.activity_favorite

    override val viewModel : FavoriteViewModel by viewModel()
    private val viewPagerAdapter: ScreenSlideViewPagerAdapter by lazy { ScreenSlideViewPagerAdapter(this) }
    private val favoriteFragment = FavoriteFragment()
    private val uploadFragment = UploadFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun init() {
        val tabTitles = arrayListOf(R.string.tabNameFavorite, R.string.tabNameUpload)

        viewPagerAdapter.addFragment(favoriteFragment)
        viewPagerAdapter.addFragment(uploadFragment)

        binding.pager.setPageTransformer(DepthPageTransformer())
//        binding.pager.setPageTransformer(ZoomOutPageTransformer())
        binding.pager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = getString(tabTitles[position])
            binding.pager.setCurrentItem(tab.position, true)
        }.attach()

        var beforeVerticalOffset = 0
        var lastSize = 0
        binding.appbar.addOnOffsetChangedListener(
            OnOffsetChangedListener { appBarLayout, verticalOffset ->
                val imgAvatar = binding.topItemFavoriteLayout.imgAvatar
                if (binding.collapsingToolbar.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(
                        binding.collapsingToolbar
                    )
                ) {
                    // collapsed
                    if (beforeVerticalOffset != verticalOffset) {
                        imgAvatar.layoutParams = imgAvatar.setImageSize(lastSize, lastSize)
                        beforeVerticalOffset = verticalOffset
                    }
                } else {
                    // extended
                    lastSize = abs(verticalOffset ) + 150
                    if (beforeVerticalOffset != verticalOffset) {
                        imgAvatar.layoutParams = imgAvatar.setImageSize(lastSize, lastSize)
                        beforeVerticalOffset = verticalOffset
                    }

                }
            }
        )

    }


     override fun initObserver() {
        viewModel.apply {

        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }


}
