package com.espoir.shattermanager

import android.app.Application
import androidx.viewbinding.BuildConfig
import com.espoir.shatter.LogUtils
import com.espoir.shatter.ShatterManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ShatterManager.init(this)
        LogUtils.config.setLogSwitch(!com.espoir.shattermanager.BuildConfig.DEBUG).setConsoleSwitch(!com.espoir.shattermanager.BuildConfig.DEBUG)
    }
}