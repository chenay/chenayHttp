package com.chenay.platform.http.simple

import androidx.lifecycle.MutableLiveData
import com.chenay.platform.http.base.BaseViewModel
import com.chenay.platform.http.entity.HintEntity


/**
 * @program: TTIPlatform2
 * @description:提示信息相关的 AlertDialogViewModel
 * @author: chenYan
 * @create: 2020-08-06 14:33
 **/
open class HintViewModel : BaseViewModel() {
    /**
     * 提示信息
     */
    val hintEntityLiveData: MutableLiveData<HintEntity> by lazy {
        MutableLiveData<HintEntity>()
    }

    /**
     *
     */
    val hintMsgLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

}