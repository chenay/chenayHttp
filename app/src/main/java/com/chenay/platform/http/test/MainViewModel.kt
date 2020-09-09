package com.chenay.platform.http.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chenay.platform.http.base.BaseViewModel
import com.chenay.platform.http.entity.HintEntity
import com.chenay.platform.http.entity.HttpResponseEntity
import com.chenay.platform.http.simple.SimpleDisposableObserver

/**
 * @program: chenayHttp
 * @description:
 * @author: chenYan
 * @create: 2020-09-09 16:42
 **/
class MainViewModel : BaseViewModel() {

    /***
     * 查询出的GI自身的属性
     */
    val mldLog: MutableLiveData<FgRcvResponseSave> by lazy {
        MutableLiveData<FgRcvResponseSave>()
    }

    /**
     * 查询收货记录
     */
    fun requestFind(request: FgRcvRequestFind) {

        TestModelImpl().httpRequestGiRcvLog(request, object : SimpleDisposableObserver<HttpResponseEntity<FgRcvResponseSave>>(this.callShowHint) {
            override fun onNext(t: HttpResponseEntity<FgRcvResponseSave>) {
                if (t.iResult()) {
                    mldLog.value = t.gtDefaultBean()
                } else {
                    callShowHint?.invoke(HintEntity().apply {
                        t.gtMsg()?.let { showToast(it) }
                    })
                }
            }
        })
    }
}

class FgRcvRequestFind{
    //@ApiModelProperty(value = "请求的用户ID", required = true)
    var staffId: String = "458081"

    //@ApiModelProperty(value = "前端调用的功能菜单ID", required = true)
    var menuId: String? = null
}

class FgRcvResponseSave {

}
