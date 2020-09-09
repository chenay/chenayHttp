package com.chenay.platform.http.simple

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chenay.platform.http.entity.HintEntity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chenay.platform.http.utils.DialogUtils
import com.chenay.platform.http.utils.ToastUtil

/**
 * @program: TTIPlatform2
 * @description:
 * @author: chenYan
 * @create: 2020-08-25 15:38
 **/
open class SimpleHintActivity : AppCompatActivity() {

    lateinit var hintViewModel: HintViewModel
    private var dialog: Dialog? = null
    private var refreshView: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hintViewModel = ViewModelProvider(this)[HintViewModel::class.java]
        setupObserver()
    }


//    /**
//     * 注册需要显示的提示相关信息
//     */
//    fun registryShowHint(viewModel: BaseViewModel) {
//        viewModel.callShowHint = {
//            Log.d(TAG, "registryShowHint: ")
//            hintViewModel.hintEntityLiveData.value = it
//        }
//    }

    private fun setupObserver() {
        hintViewModel.hintEntityLiveData.observe(this, Observer {
            it.toastMsg?.let { itt ->
                ToastUtil.showDefaultToast(this, itt)
            }
            showMedia(it.mediaType)
            showDialog(it.dialogMsg, it.isDialog)
            showRefresh(it.isRefreshing)
        })
    }

    /**
     * 显示提示音
     */
    private fun showMedia(type: Int) {

    }

    private fun showDialog(msg: String?, show: Boolean) {
        if (show && dialog == null && !msg.isNullOrEmpty()) {
            dialog = DialogUtils.createLoadingDialog(this, msg)
        }
        if (show) {
            dialog?.show()
        } else {
            dialog?.dismiss()
        }
    }

    fun toShowHint(it: HintEntity) {
        hintViewModel.hintEntityLiveData.value = it
    }

    fun setReFreshLayout(view: SwipeRefreshLayout) {
        refreshView?.let {
            it.isRefreshing = false
        }
        refreshView = view
    }

    private fun showRefresh(refreshing: Boolean?) {
        refreshing?.let {
            if (refreshView?.isRefreshing != refreshing) {
                refreshView?.isRefreshing = refreshing
            }
        }
    }
}