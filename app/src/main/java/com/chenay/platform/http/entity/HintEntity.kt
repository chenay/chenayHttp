package com.chenay.platform.http.entity



/**
 * @program: TTIPlatform2
 * @description: 提示信息相关的实体
 * @author: chenYan
 * @create: 2020-08-06 14:49
 **/
open  class HintEntity {

    companion object {
        val SHOW_NULL: Int = 0
        val SHOW_TOAST: Int = 1
        val SHOW_DIALOG: Int = 2
    }

    var isDialog: Boolean = false
    var mediaType: Int = 0
    var toastMsg: String? = null
    var dialogMsg: String? = null
    var action: Int? = 0
    var isRefreshing :Boolean?=null



    fun showToast(msg: String) {
        this.toastMsg = msg
    }

    fun showDialog(msg: String){
        this.isDialog = true
        this.dialogMsg = msg
    }

    fun showMedial(type: Int) {
        this.mediaType =type
    }

    fun showMedialSuccess(){
//        showMedial(AlertMediaUtil.TYPE_SUCCESS)
    }

    fun showMedialFailed(){
//        showMedial(AlertMediaUtil.TYPE_FAILE)
    }

    fun showMedialSucAndToast(msg: String) {
        showMedialSuccess()
        showToast(msg)
    }

    fun showMedialFailedAndToast(msg: String) {
        showMedialFailed()
        showToast(msg)
    }

    fun dismissDialog() {
        isDialog = false
    }

    fun dismissRefresh() {
        isRefreshing= false
    }
}