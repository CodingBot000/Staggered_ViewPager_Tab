package com.exam.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2

import com.exam.sample.R
import com.exam.sample.adapter.ScreenSlideViewPagerAdapter
import com.exam.sample.adapter.ViewPagerAdapter
import com.exam.sample.common.BaseActivity
import com.exam.sample.databinding.ActivityMainBinding
import com.exam.sample.ui.fragment.*
import com.exam.sample.utils.Const
import com.exam.sample.utils.ZoomOutPageTransformer

import com.exam.sample.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_trending.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity :  BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val TAG: String
        get() = this.javaClass.name
    override val layoutResID: Int
        get() = R.layout.activity_main

    override val viewModel : MainViewModel by viewModel()
    val viewPagerAdapter: ScreenSlideViewPagerAdapter by lazy { ScreenSlideViewPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getScreenSize()

    }

    override fun init() {
        getScreenSize()

        val tabTitles = arrayListOf(R.string.tabNameTrending, R.string.tabNameArtists,
            R.string.tabNameClips, R.string.tabNameStories, R.string.tabNameStickers, R.string.tabNameEmoji)

        viewPagerAdapter.addFragment(TrendingFragment())
        viewPagerAdapter.addFragment(TrendingFragment())
        viewPagerAdapter.addFragment(TrendingFragment())
        viewPagerAdapter.addFragment(TrendingFragment())
        viewPagerAdapter.addFragment(TrendingFragment())
        viewPagerAdapter.addFragment(TrendingFragment())

        pager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, pager) { tab, position ->
            tab.text = getString(tabTitles[position])
            pager.setCurrentItem(tab.position, true)
        }.attach()


      binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position

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



    override fun onDestroy() {
        super.onDestroy()
    }



}
