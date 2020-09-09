package com.chenay.platform.http.utils;


import android.content.res.Resources;

import androidx.annotation.StringRes;

import com.chenay.platform.http.HttpApplication;


/**
 * arrays.xml
 * 静态数据映射工具
 * @author Y.Chen5
 */
public class ResourcesUtils {

    /**
     * 源文件获取字符
     * @param id
     * @return
     */
    public static String getString(@StringRes int id) {
        String str;
        try {
            str = HttpApplication.context.getResources().getString(id);
        } catch (Resources.NotFoundException e) {
            str = "";
        }
        return str;
    }

}
