package com.chenay.platform.http.simple

import com.chenay.platform.http.entity.HintEntity
import com.chenay.platform.http.R
import com.chenay.platform.http.utils.ResourcesUtils


import io.reactivex.observers.DisposableObserver
import java.net.ConnectException

/**
 * @program: TTIPlatform2
 * @description:
 * @author: chenYan
 * @create: 2020-08-19 09:59
 **/
open class SimpleDisposableObserver<T>(private val callShowHint: ((HintEntity) -> Unit)? = null) :
    DisposableObserver<T>() {


    fun onStart(
        type: Int = HintEntity.SHOW_NULL,
        msg: String = ResourcesUtils.getString(R.string.str_loading)
    ) {
        super.onStart()
        callShowHint?.invoke(HintEntity().apply {
            when (type) {
                HintEntity.SHOW_NULL -> {
                }
                HintEntity.SHOW_TOAST -> {
                    showToast(msg)
                }
                HintEntity.SHOW_DIALOG -> {
                    showDialog(msg)
                }
            }
        })

    }

    override fun onStart() {
        onStart(HintEntity.SHOW_DIALOG)
    }

    override fun onComplete() {
        callShowHint?.invoke(HintEntity().apply {
            dismissDialog()
            dismissRefresh()
        })
    }

    override fun onNext(t: T) {
        callShowHint?.invoke(HintEntity().apply {
            dismissDialog()
        })
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        e.message?.let {
            callShowHint?.invoke(HintEntity().apply {
                var s = it
                if (e is ConnectException) {
                    s = ResourcesUtils.getString(R.string.str_connect_hint) + "($it)"
                }
                showToast(s)
                dismissDialog()
            })
        }
    }
}