package com.chenay.platform.http.test

import com.chenay.platform.http.entity.HttpResponseEntity
import io.reactivex.observers.DisposableObserver

/**
 * @program: chenayHttp
 * @description:
 * @author: chenYan
 * @create: 2020-09-09 16:46
 **/
interface TestModel{

    fun httpRequestGiRcvLog(request: FgRcvRequestFind, observer: DisposableObserver<HttpResponseEntity<FgRcvResponseSave>>)
}