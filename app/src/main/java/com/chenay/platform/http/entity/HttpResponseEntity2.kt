package com.chenay.platform.http.entity

import com.chenay.platform.http.utils.GsonUtil
import com.google.gson.reflect.TypeToken

/**
 * @program: TTIPlatform2
 * @description: http请求
 * @author: chenYan
 * @create: 2020-08-24 17:50
 **/

class HttpResponseEntity2<T> {


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
    private var data: MutableMap<String, Any> = mutableMapOf()

    fun getMeta(): MutableMap<String, Any> {
        return meta
    }

    fun addMeta(key: String, `object`: Any): HttpResponseEntity2<T> {
        meta[key] = `object`
        return this
    }

    fun getData(): MutableMap<String, Any> {
        return data
    }

    fun addData(key: String, `object`: Any): HttpResponseEntity2<T> {
        data[key] = `object`
        return this
    }

    fun <E> gtBeanList(key: String, clazz: Class<E>): List<E>? {
        return try {
            val any = getData()[key]
            val toJson = GsonUtil.getGson().toJson(any)
            GsonUtil.getObjectList(toJson,clazz)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun <E> gtBean(key: String, clazz: Class<E>): E? {
        return try {
            val any = getData()[key]
            val toJson = GsonUtil.getGson().toJson(any)
            GsonUtil.getGson().fromJson(toJson,clazz)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun gtDefaultData(clazz: Class<T>): List<T>? {
        return try {
            val type = object : TypeToken<List<T>>() {}.type
            val any = getData()[KEY_DEFAULT_BEAN]
            val toJson = GsonUtil.getGson().toJson(any)
            GsonUtil.getObjectList(toJson,clazz)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun stDefaultBeanList(beanList: Any) {
        getData()[KEY_DEFAULT_BEAN] = beanList
    }

    fun stDefaultBean(bean: Any) {
        getData()[KEY_DEFAULT_BEAN] = bean
    }

    fun gtDefaultBean(clazz: Class<T>): T? {
        return try {
            gtDefaultData(clazz)!![0]
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