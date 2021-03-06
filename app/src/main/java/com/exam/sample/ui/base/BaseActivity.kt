package com.exam.sample.ui.base


import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.exam.sample.livedata.RxEventBus
import com.exam.sample.viewmodel.base.BaseViewModel

abstract class BaseActivity<B : ViewDataBinding?, VM : BaseViewModel>() : AppCompatActivity() {
    var _binding: B? = null
    val binding: B get() = _binding!!

    abstract val viewModel: VM
    abstract val layoutResID: Int
    abstract val TAG: String

    abstract fun init()
    abstract fun initObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = DataBindingUtil.setContentView(this, layoutResID)
        _binding?.lifecycleOwner = this

        init()
        initObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

