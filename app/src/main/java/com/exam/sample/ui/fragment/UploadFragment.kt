package com.exam.sample.ui.fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.exam.sample.R
import com.exam.sample.ui.base.BaseFragment

import com.exam.sample.databinding.FragmentUploadBinding
import com.exam.sample.viewmodel.FavoriteViewModel

import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class UploadFragment() : BaseFragment<FragmentUploadBinding, FavoriteViewModel>() {
    override val TAG: String
        get() = this.javaClass.name
    override val layoutResID: Int
        get() = R.layout.fragment_upload

    override val viewModel : FavoriteViewModel by sharedViewModel()


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

