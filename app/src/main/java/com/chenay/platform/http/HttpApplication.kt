package com.chenay.platform.http

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.chenay.platform.http.simple.RetrofitHelper
import okhttp3.logging.HttpLoggingInterceptor


/**
 * @program: chenayHttp
 * @description:
 * @author: chenYan
 * @create: 2020-09-09 17:23
 **/
class HttpApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        initHttp()
    }

    override fun createConfigurationContext(overrideConfiguration: Configuration): Context {
        val configurationContext =
            super.createConfigurationContext(overrideConfiguration)
        context = configurationContext
        return configurationContext
    }

    private fun initHttp() {

        /* debug时完整输出 http*/
        RetrofitHelper.defaultInit("http://10.64.90.79", 8687)

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

}