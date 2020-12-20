package com.exam.sample

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.exam.sample.di.*
import com.exam.sample.di.viewModelModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class App : MultiDexApplication() {
    companion object {
        private lateinit var sApplication: Application
        fun getApplication():Application
        {
            return sApplication
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
    }
}