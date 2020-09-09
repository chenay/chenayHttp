package com.chenay.platform.http.entity

/**
 * @program: TTIPlatform2
 * @description: http请求
 * @author: chenYan
 * @create: 2020-08-24 17:50
 **/

open class HttpResponseEntity<T> {

    companion object {
        const val KEY_SUCCESS = "success"
        const val KEY_CODE = "code"
        const val KEY_RTN_CODE = "rtnCode"
        const val KEY_MSG = "msg"
        const val KEY_TIMESTAMP = "timestamp"
        const val KEY_DEFAULT_BEAN = "default"
    }

    // 消息头meta 存放状态信息 code message
    private var meta: MutableMap<String, Any> = mutableMapOf()

    // 消息内容  存储实体交互数据
    private var data: MutableMap<String, List<T>> = mutableMapOf()

    fun getMeta(): MutableMap<String, Any> {
        return meta
    }

    fun addMeta(key: String, `object`: Any): HttpResponseEntity<T> {
        meta[key] = `object`
        return this
    }

    fun getData(): MutableMap<String, List<T>> {
        return data
    }

    fun addData(key: String, `object`: T): HttpResponseEntity<T> {
        data[key] = mutableListOf<T>().apply { add(`object`) }
        return this
    }

    fun gtDefaultBeanList(): List<T>? {
        return try {
            return getData()[KEY_DEFAULT_BEAN]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun stDefaultBeanList(beanList: List<T>) {
        getData()[KEY_DEFAULT_BEAN] = beanList
    }

    fun stDefaultBean(bean: T) {
        stDefaultBeanList(mutableListOf<T>().apply {
            add(bean)
        })
    }

    fun gtDefaultBean(): T? {
        return try {
            gtDefaultBeanList()!![0]
        } catch (e: Exception) {
            null
        }
    }


    fun gtMsg(): String? {
        return meta[KEY_MSG] as String?
    }

    fun gtRtnCode(): String? {
        return meta[KEY_RTN_CODE] as String?
    }

    fun iResult(): Boolean {
        return try {
            val any = getMeta()
            val b = any[KEY_SUCCESS] as Boolean
            b
        } catch (e: Exception) {
            false
        }
    }
}