package com.chenay.platform.http.simple

import io.reactivex.Observable
import io.reactivex.android.BuildConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * @program: TTIPlatform2
 * @description:
 * @author: chenYan
 * @create: 2020-08-18 15:38
 **/
open class SimpleModel : Model {

    public override fun <RespB> postJsonNoEncrypt(
        observer: DisposableObserver<RespB>,
        api: (Retrofit) -> Observable<RespB>
    ) {
        httpJsonPostNoEncrypt(observer) {
            api.invoke(it)
        }
    }
}

interface Model {
    fun <RespB> postJsonNoEncrypt(
        observer: DisposableObserver<RespB>,
        api: (Retrofit) -> Observable<RespB>
    )
}

fun <RespB, R1 : Observable<RespB>> Model.httpJsonPostNoEncrypt(
    observer: DisposableObserver<RespB>,
    api: (Retrofit) -> R1
): Retrofit {
    try {
        val retrofit = RetrofitHelper().getDefaultRetrofit()
        val observable = api.invoke(retrofit)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
        return retrofit
    } catch (e: Exception) {
        e.printStackTrace()
        throw e
    }
}

open class RetrofitHelper {

    companion object {
        //IP地址
        var DEFAULT_HOST: String? = null

        //端口
        var DEFAULT_PORT: Int? = null

        //证书
        var certificates: Array<InputStream?>? = null

        var clientBuilder: OkHttpClient.Builder? = null

        var retrofitBuilder: Retrofit.Builder? = null

        public fun defaultInit(host: String, port: Int) {
            DEFAULT_HOST = host
            DEFAULT_PORT = port
            initClientBuilder()
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                clientBuilder?.addInterceptor(loggingInterceptor)
            }
            initRetrofitBuilder()
        }

        private fun initClientBuilder() {
            clientBuilder =
                OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    //忽略证书
                    .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
                    .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
        }

        private fun initRetrofitBuilder() {
            if (retrofitBuilder == null) {
                val build = clientBuilder!!.build()
                retrofitBuilder = Retrofit.Builder()
                    .baseUrl("$DEFAULT_HOST:$DEFAULT_PORT")
                    .client(build)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            }
        }
    }


    open fun getDefaultRetrofit(): Retrofit = getRetrofit(
        DEFAULT_HOST ?: throw NullPointerException("服务器IP没有设置"),
        DEFAULT_PORT ?: throw NullPointerException("服务器端口没有设置"),
        certificates
    )


    open fun getRetrofit(host: String, port: Int, inputStream: Array<InputStream?>?): Retrofit {
        if (inputStream != null && inputStream.isNotEmpty()) {
            //校验证书
            setCertificates(
                clientBuilder
                    ?: throw NullPointerException("请先初始化clientBuilder"), *inputStream
            )
        }
        initRetrofitBuilder()
        return retrofitBuilder?.build() ?: throw NullPointerException("Retrofit 初始化失败!")
    }


    /**
     * 通过okhttpClient来设置证书
     *
     * @param clientBuilder OKhttpClient.builder
     * @param certificates  读取证书的InputStream
     */
    open fun setCertificates(
        clientBuilder: OkHttpClient.Builder,
        vararg certificates: InputStream?
    ) {
        try {
            val certificateFactory =
                CertificateFactory.getInstance("X.509")
            val keyStore =
                KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null)
            var index = 0
            for (certificate in certificates) {
                val certificateAlias = Integer.toString(index++)
                keyStore.setCertificateEntry(
                    certificateAlias, certificateFactory
                        .generateCertificate(certificate)
                )
                try {
                    certificate?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            val trustManagerFactory =
                TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm()
                )
            trustManagerFactory.init(keyStore)
            val trustManagers =
                trustManagerFactory.trustManagers
            check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                ("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers))
            }
            val trustManager =
                trustManagers[0] as X509TrustManager
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(
                null,
                trustManagerFactory.trustManagers,
                SecureRandom()
            )
            val sslSocketFactory = sslContext.socketFactory
            clientBuilder.sslSocketFactory(sslSocketFactory, trustManager)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}

