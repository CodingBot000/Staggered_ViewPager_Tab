package com.exam.sample.service

import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import com.exam.sample.App
import com.exam.sample.R
import com.exam.sample.model.data.TrendingData
import com.exam.sample.utils.Const
import com.exam.sample.viewmodel.MainViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class WorkerDataChangeMonitor : Worker(), KoinComponent, MainViewModel.ServiceListener {
    private val viewModel : MainViewModel by inject()
    private var dataOld:TrendingData? = null

    private var debugCnt = 0


    override fun doWork(): Result {
        Log.d(Const.LOG_TAG, "WorkerDataChangeObserver doWork")
        viewModel.serviceListener = this
        viewModel.getDataFromBackground(0)

        return Result.SUCCESS
    }

    override fun listenerFromService(data: TrendingData) {

        Log.v(Const.LOG_TAG, "listenerFromService")
        if (dataOld != null) {
            if ((dataOld as TrendingData).trendingItems[0].embed_url  == data.trendingItems[0].embed_url) {
                Log.v(Const.LOG_TAG, "noti msg equals $debugCnt")
            } else {
                Log.v(Const.LOG_TAG, "noti msg NOT equals $debugCnt")

                if (Const.USE_FOREGROUND) {
                    DataChangeNotification.updateMessage(App.getApplication().getString(R.string.newDataNotiMessage))
                } else {
                    val notiBuilder = DataChangeNotification.createNotification(App.getApplication().applicationContext
                        , App.getApplication().getString(R.string.newDataNotiMessage))
                    NotificationManagerCompat.from(App.getApplication().applicationContext).
                    notify(Const.NOTI_ID, notiBuilder.build())
                }
            }
            debugCnt++
        }
        dataOld = data
    }

}