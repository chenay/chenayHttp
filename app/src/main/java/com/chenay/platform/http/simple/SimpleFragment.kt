package com.chenay.platform.http.simple

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chenay.platform.http.base.BaseViewModel

import java.util.*

/**
 * @program: TTIPlatform2
 * @description:
 * @author: chenYan
 * @create: 2020-08-24 15:25
 **/

open class SimpleFragment : Fragment() {

    protected var requestDefFocus: (() -> Unit)? = null


    var refreshView: SwipeRefreshLayout? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    fun registryShowHint(viewModel: BaseViewModel) {
        viewModel.callShowHint = {
            if (requireActivity() is SimpleHintActivity) {
                (requireActivity() as SimpleHintActivity).toShowHint(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Timer().schedule(object : TimerTask() {
            override fun run() {
                requireActivity().runOnUiThread {
                    requestDefFocus?.invoke()
                }
            }
        }, 150)

        refreshView?.let { view ->
            requireActivity().run {
                if (this is SimpleHintActivity) {
                    this
                } else {
                    null
                }
            }?.setReFreshLayout(view)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}
