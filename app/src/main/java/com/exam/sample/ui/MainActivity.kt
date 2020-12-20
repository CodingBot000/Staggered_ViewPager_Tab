package com.exam.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import com.exam.sample.R
import com.exam.sample.adapter.ViewPagerAdapter
import com.exam.sample.common.BaseActivity
import com.exam.sample.databinding.ActivityMainBinding
import com.exam.sample.ui.fragment.*
import com.exam.sample.utils.Const

import com.exam.sample.viewmodel.MainViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity :  BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val TAG: String
        get() = this.javaClass.name
    override val layoutResID: Int
        get() = R.layout.activity_main

    override val viewModel : MainViewModel by viewModel()
    val viewPagerAdapter: ViewPagerAdapter by lazy { ViewPagerAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getScreenSize()

    }

    override fun init() {
        getScreenSize()
        viewPagerAdapter.addFragment(TrendingFragment(), getString(R.string.tabNameTrending))
        viewPagerAdapter.addFragment(TrendingFragment(), getString(R.string.tabNameArtists))
        viewPagerAdapter.addFragment(TrendingFragment(), getString(R.string.tabNameClips))
        viewPagerAdapter.addFragment(TrendingFragment(), getString(R.string.tabNameStories))
        viewPagerAdapter.addFragment(TrendingFragment(), getString(R.string.tabNameStickers))
        viewPagerAdapter.addFragment(TrendingFragment(), getString(R.string.tabNameEmoji))

        binding.tabLayout.setupWithViewPager(pager);
        pager.adapter = viewPagerAdapter

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
