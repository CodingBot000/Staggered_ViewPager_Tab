package com.exam.sample.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.exam.sample.R
import com.exam.sample.adapter.ScreenSlideViewPagerAdapter
import com.exam.sample.databinding.ActivityMainBinding
import com.exam.sample.ui.base.BaseActivity
import com.exam.sample.ui.fragment.ArtistsFragment
import com.exam.sample.ui.fragment.ClipsFragment
import com.exam.sample.ui.fragment.TrendingFragment
import com.exam.sample.utils.Const
import com.exam.sample.utils.animation.ZoomOutPageTransformer
import com.exam.sample.utils.toastMsg
import com.exam.sample.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity :  BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val TAG: String
        get() = this.javaClass.name
    override val layoutResID: Int
        get() = R.layout.activity_main

    override val viewModel : MainViewModel by viewModel()
    private val viewPagerAdapter: ScreenSlideViewPagerAdapter by lazy { ScreenSlideViewPagerAdapter(this) }

    private val trendingFragment = TrendingFragment()
    private val artistsFragment = ArtistsFragment()
    private val clipsFragment = ClipsFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.v(Const.LOG_TAG, "$TAG onNewIntent")
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun init() {
        getScreenSize()

        viewPagerAdapter.addFragment(trendingFragment)
        viewPagerAdapter.addFragment(artistsFragment)
        viewPagerAdapter.addFragment(clipsFragment)


//        pager.setPageTransformer(DepthPageTransformer())
        binding.pager.setPageTransformer(ZoomOutPageTransformer())
        binding.pager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = getString(Const.TAB_TITLES[position])
            binding.pager.setCurrentItem(tab.position, true)
        }.attach()


      binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


    }

    override fun initObserver() {
        viewModel.apply {

        }
    }

    private fun getScreenSize() {
        val metrics = resources.displayMetrics
        val screenHeight = metrics.heightPixels
        val screenWidth = metrics.widthPixels

        Const.SCREEN_WIDTH = screenWidth.toFloat()
        Const.SCREEN_WIDTH_HALF = (screenWidth / 2).toFloat()
    }

    private var backKeyPressedTime: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + Const.BACKPRESS_TIME) {
            backKeyPressedTime = System.currentTimeMillis();
            toastMsg(R.string.onemoretime)
            return
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + Const.BACKPRESS_TIME) {
            finish();
        }
    }


    override fun onDestroy() {
        super.onDestroy()
    }



}
