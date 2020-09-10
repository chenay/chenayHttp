package com.chenay.platform.http.utils;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.view.View;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.chenay.platform.http.HttpApplication;

import io.reactivex.annotations.NonNull;


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




    /**
     * View获取Activity的工具
     *
     * @param view view
     * @return Activity
     */
    public static
    @NonNull
    Activity getActivity(View view) {
        Context context = view.getContext();

        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }

        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

    /**
     * 判断App是否是Debug版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug(Context context) {
        if (context.getPackageName().isEmpty()) {
            return false;
        }
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(context.getPackageName(), 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }



}
