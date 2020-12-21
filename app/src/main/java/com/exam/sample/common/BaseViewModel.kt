package com.exam.sample.common


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exam.sample.R
import com.exam.sample.utils.ToastMsg
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(): ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    fun onClickImgPlusButton() {
        ToastMsg(R.string.plusBtnMsg)
    }
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    protected fun showProgress() {
        _isLoading.value = true
    }

    protected fun hideProgress() {
        _isLoading.value = false
    }

}