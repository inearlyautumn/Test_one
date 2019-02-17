package com.example.giffun01.global;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.example.giffun01.util.SharedUtil;
import org.jetbrains.annotations.NotNull;

public class GifFun {
    public static boolean isDebug = false;

    private static Context context;

    private static Handler handler;

    private static boolean isLogin;

    private static long userId;

    private static String token;

    private static int loginType = -1;

    public static String BASE_URL = isDebug ? "http://192.168.31.177:3000" : "http://api.quxianggif.com";

    public static final int GIG_MAX_SIZE = 20 * 1024 * 1024;

    /**
     * 初始化接口，这里会进行应用程序的初始化操作，一定要在代码执行的最开始调用
     */

    public static void initialize(Context c) {
        context =c;
        handler = new Handler(Looper.getMainLooper());
        refreshLoginState();

    }

    /**
     * 刷新用户的登录状态
     */
    private static void refreshLoginState() {
//        SharedUtil.read(Const.Auth.USER_ID, 0L);
    }

    /**
     * 获取全局Context， 在代码的任意位置都可以用，随时都能获取到全局Context对象
     * @return 全局Context对象
     */
    public static Context getContext() {
        return context;
    }

    /**
     * 判断用户是否已登录
     * @return 已登录返回true，未登录返回false。
     */
    public static boolean isLogin() {
        return isLogin;
    }

    @NotNull
    public static String getPackageName() {
        return context.getPackageName();
    }
}
