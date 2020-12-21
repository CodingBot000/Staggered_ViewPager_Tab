package com.exam.sample.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter



class ScreenSlideViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val mFragmentList: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = mFragmentList.size

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }


    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

}
