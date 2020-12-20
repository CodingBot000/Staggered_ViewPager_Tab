package com.exam.sample.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.exam.sample.BR
import com.exam.sample.R
import com.exam.sample.common.BaseFragment

import com.exam.sample.databinding.FragmentTrendingBinding
import com.exam.sample.databinding.FragmentUploadBinding
import com.exam.sample.viewmodel.MainViewModel
import com.exam.sample.viewmodel.UploadViewModel
import org.koin.android.viewmodel.ext.android.viewModel



class UploadFragment() : BaseFragment<FragmentUploadBinding, UploadViewModel>() {
    override val TAG: String
        get() = this.javaClass.name
    override val layoutResID: Int
        get() = R.layout.fragment_upload

    override val viewModel : UploadViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }

    fun onNewIntent(intent : Intent?) {

    }

    override fun init() {

    }

    override fun initObserver() {
        viewModel.apply {

        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }



}

