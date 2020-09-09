package com.chenay.platform.http.test

import androidx.lifecycle.MutableLiveData
import com.chenay.platform.http.base.BaseViewModel
import com.chenay.platform.http.entity.HintEntity
import com.chenay.platform.http.entity.HttpResponseEntity
import com.chenay.platform.http.simple.SimpleDisposableObserver
import java.sql.Timestamp

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
    fun newrequestFind(request: FgRcvRequestFind) {

        TestModelImpl().httpRequestGiRcvLog(
            request,
            object :
                SimpleDisposableObserver<HttpResponseEntity<FgRcvResponseSave>>(this.callShowHint) {
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

class FgRcvRequestFind {
    //@ApiModelProperty(value = "请求的用户ID", required = true)
    var staffId: String = "458081"

    //@ApiModelProperty(value = "前端调用的功能菜单ID", required = true)
    var menuId: String? = null
}

class FgRcvResponseSave {
    var transId: Long? = null
    var wmsTransId: Long? = null
    var erpTransId: String? = null
    var staffId: Long? = null
    var menuId: Long? = null
    var giNo1: String? = null
    var giNo2: String? = null
    var giNo3: String? = null
    var pickListId: String? = null
    var qty: Long? = null
    var logTime: Timestamp? = null
    var status: Int? = null
    var locator: String? = null
    var member: String? = null
    var returnTxType: String? = null
    var docNo: String? = null
    var docId: Long? = null
    var itemNo: String? = null


    override fun toString(): String {
        return "ScanLog{" +
                "transId=" + transId +
                ", wmsTransId=" + wmsTransId +
                ", erpTransId=" + erpTransId +
                ", staffId=" + staffId +
                ", menuId=" + menuId +
                ", giNo1=" + giNo1 +
                ", giNo2=" + giNo2 +
                ", giNo3=" + giNo3 +
                ", pickListId=" + pickListId +
                ", qty=" + qty +
                ", logTime=" + logTime +
                ", status=" + status +
                ", locator=" + locator +
                ", member=" + member +
                ", returnTxType=" + returnTxType +
                ", docNo=" + docNo +
                ", docId=" + docId +
                ", itemNo=" + itemNo +
                "}"
    }

}
