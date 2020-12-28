package com.exam.sample.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel>() : Fragment() {
    lateinit var binding: B

    abstract val viewModel: VM
    abstract val layoutResID: Int
    abstract val TAG: String

    abstract fun init()
    abstract fun initObserver()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        binding.lifecycleOwner = this
        init()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.lifecycleOwner = this
//        init()
        initObserver()
    }


    override fun onDestroy() {
        super.onDestroy()

        if(::binding.isInitialized) binding.unbind()
    }

}
