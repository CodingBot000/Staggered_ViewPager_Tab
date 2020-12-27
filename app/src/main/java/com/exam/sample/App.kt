package com.exam.sample

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.multidex.MultiDexApplication
import androidx.work.*
import com.exam.sample.common.ApplicationLifeCycleListener
import com.exam.sample.di.*
import com.exam.sample.di.viewModelModule
import com.exam.sample.service.ServiceActions
import com.exam.sample.service.ServiceDataChangeObserver
import com.exam.sample.service.WorkerDataChangeMonitor
import com.exam.sample.utils.Const
import com.exam.sample.utils.isServiceRunning
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit


class App : MultiDexApplication() {
    companion object {
        private lateinit var sApplication: Application
        private lateinit var sApplicationLifecycle: Lifecycle.Event

        fun getApplication():Application
        {
            return sApplication
        }

        fun getLifeCycleApplication() : Lifecycle.Event {
            return sApplicationLifecycle
        }

    }

    override fun onCreate()
    {
        super.onCreate();
        Stetho.initializeWithDefaults(this)
        sApplication = this;
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, repoModule, remoteDataModule, localDataModule, viewModelModule))
        }

        val serviceIntent by lazy {
            Intent(this, ServiceDataChangeObserver::class.java)
        }

        ProcessLifecycleOwner.get().getLifecycle()
            .addObserver(ApplicationLifeCycleListener(object : ApplicationLifeCycleListener.LifeCycleListenerCallback {
                override fun lifeCycleCallback(event: Lifecycle.Event) {
                    Log.v(Const.LOG_TAG, "LifeCycleListener ProcessLifecycleOwner event=> $event")
                    sApplicationLifecycle = event
                    if (event == Lifecycle.Event.ON_RESUME) {
                        if (Const.USE_FOREGROUND) {
                            if (isServiceRunning(ServiceDataChangeObserver::class.java))
                                stopService(serviceIntent)
                            
                            var notificationManager = applicationContext.getApplicationContext()
                            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
	                        notificationManager.cancel(Const.NOTI_ID)    
                        }
                        
                    }

                    if (event == Lifecycle.Event.ON_STOP) {
                        if (Const.USE_FOREGROUND) {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                serviceIntent.action = ServiceActions.START_FOREGROUND
                                startForegroundService(serviceIntent)
                            }
                        } else {
                            doWorkPeriodic()
                        }
                    }
                }
            }))
    }

    private fun doWorkPeriodic() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<WorkerDataChangeMonitor>(15, TimeUnit.MINUTES).build()
        PeriodicWorkRequest.Builder(WorkerDataChangeMonitor::class.java, PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MINUTES)
            .setConstraints(constraints).build()

        WorkManager.getInstance()?.enqueueUniquePeriodicWork(Const.WORKER_TAG, ExistingPeriodicWorkPolicy.REPLACE, workRequest);
    }
}