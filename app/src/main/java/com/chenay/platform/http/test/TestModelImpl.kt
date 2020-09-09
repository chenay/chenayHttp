package com.chenay.platform.http.test

import com.chenay.platform.http.entity.HttpResponseEntity
import com.chenay.platform.http.simple.SimpleModel
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * @program: chenayHttp
 * @description:
 * @author: chenYan
 * @create: 2020-09-09 16:46
 **/
class TestModelImpl : TestModel, SimpleModel() {

    override fun httpRequestGiRcvLog(
        request: FgRcvRequestFind,
        observer: DisposableObserver<HttpResponseEntity<FgRcvResponseSave>>
    ) {
        postJsonNoEncrypt(observer) {
            it.create(ServiceApi::class.java).ReceivingGiFindV2(request)
        }
    }

}

interface ServiceApi {
    @Headers("Content-Type: application/json", "Accept: application/json")
    @POST("/wms-api/api/v2/fg/rcv/find")
    fun ReceivingGiFindV2(@Body request: FgRcvRequestFind): Observable<HttpResponseEntity<FgRcvResponseSave>>
}