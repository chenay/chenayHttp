package com.chenay.platform.http.base

import androidx.lifecycle.ViewModel
import com.chenay.platform.http.entity.HintEntity
import com.chenay.platform.http.simple.HintListener


/**
 * @program: TTIPlatform2
 * @description:
 * @author: chenYan
 * @create: 2020-08-19 08:57

 **/
open class BaseViewModel : ViewModel() , HintListener {
    var callShowHint: ((HintEntity) -> Unit)? = null
}