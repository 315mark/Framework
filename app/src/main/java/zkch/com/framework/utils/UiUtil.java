package zkch.com.framework.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import zkch.com.framework.MyApp;

public class UiUtil {
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static Context getContext()
    {
        return MyApp.getContext();
    }

    public static Handler getHandler()
    {
        return sHandler;
    }

    // /////////////////加载资源文件 ///////////////////////////

    // 获取字符串
    public static String getString(int id)
    {
        return getContext().getResources().getString(id);
    }

    // 获取字符串数组
    public static String[] getStringArray(int id)
    {
        return getContext().getResources().getStringArray(id);
    }

    // 获取图片
    public static Drawable getDrawable(int id)
    {
        return getContext().getResources().getDrawable(id);
    }

    // 获取颜色
    public static int getColor(int id)
    {
        return getContext().getResources().getColor(id);
    }

    //根据id获取颜色的状态选择器
    public static ColorStateList getColorStateList(int id)
    {
        return getContext().getResources().getColorStateList(id);
    }

    // 获取尺寸
    public static int getDimen(int id)
    {
        return getContext().getResources().getDimensionPixelSize(id);// 返回具体像素值
    }

    // /////////////////加载布局文件//////////////////////////
    public static View inflate(int id)
    {
        return View.inflate(getContext(), id, null);
    }

    // /////////////////判断是否运行在主线程//////////////////////////
    public static boolean isRunOnUiThread()
    {
        if (Looper.myLooper() == Looper.getMainLooper())
        {
            return true;
        }
        return false;
    }

    // 运行在主线程
    public static void runOnUiThread(Runnable r)
    {
        if (isRunOnUiThread())
        {
            // 已经是主线程, 直接运行
            r.run();
        }
        else
        {
            // 如果是子线程, 借助handler让其运行在主线程
            getHandler().post(r);
        }
    }
}
