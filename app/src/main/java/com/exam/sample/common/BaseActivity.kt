package com.exam.sample.common

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>() : AppCompatActivity() {
    lateinit var binding: B

    abstract val viewModel: VM
    abstract val layoutResID: Int
    abstract val TAG: String

    abstract fun init()
    abstract fun initObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutResID)
        binding.lifecycleOwner = this

        init()
        initObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(::binding.isInitialized) binding.unbind()
    }
}

