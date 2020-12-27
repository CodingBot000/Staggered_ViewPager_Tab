package com.exam.sample.common


import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exam.sample.App
import com.exam.sample.R
import com.exam.sample.utils.toastMsg
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(): ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    fun onClickImgPlusButton() {
        toastMsg(R.string.plusBtnMsg)
    }
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    protected fun showProgress() {
        if (App.getLifeCycleApplication() == Lifecycle.Event.ON_RESUME)
            _isLoading.value = true
    }

    protected fun hideProgress() {
        if (App.getLifeCycleApplication() == Lifecycle.Event.ON_RESUME)
            _isLoading.value = false
    }

}