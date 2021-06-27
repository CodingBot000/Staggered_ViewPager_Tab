package com.exam.sample.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.exam.sample.viewmodel.base.BaseViewModel

abstract class BaseFragment<B : ViewDataBinding?, VM : BaseViewModel>() : Fragment() {
    var _binding: B? = null
    val binding get() = _binding!!

    abstract val viewModel: VM
    abstract val layoutResID: Int
    abstract val TAG: String

    abstract fun initElem()
    abstract fun initObserver()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        _binding?.lifecycleOwner = this
        initElem()
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
