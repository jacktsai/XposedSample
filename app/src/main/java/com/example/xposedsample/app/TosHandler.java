package com.example.xposedsample.app;

import com.example.xposedsample.hooks.GeneralMethodHook;
import com.example.xposedsample.net.TosConnection;
import com.example.xposedsample.utils.J;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;

public class TosHandler {
    private static TosHandler instance;

    public static TosHandler getInstance() {
        if (instance == null)
            instance = new TosHandler();

        return instance;
    }

    private TosHandler() {

    }

    public void onInit() {

    }

    public void onLoadPackage(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
//        hook_UnityPlayer(packageParam);
//        hook_a(packageParam);
//        hook_v(packageParam);
//        hook_w(packageParam);
//        hook_x(packageParam);
//        hook_y(packageParam); // 與畫面有關
//        hook_ac(packageParam); // 與 OpenGL 有關
//        hook_ad(packageParam);
//        hook_ae(packageParam);
//        hook_PlayerPrefs(packageParam);
        hook_WWW(packageParam);
        hook_URL(packageParam);
        hook_HttpsURLConnectionImpl(packageParam);
    }

    private void hook_UnityPlayer(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.UnityPlayer", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook(clazz);

        for (Method method : clazz.getDeclaredMethods()) {
            String name = method.getName();
            Class<?>[] p = method.getParameterTypes();

            if (name.equals("isFinishing") ||
                name.equals("onDrawFrame") ||
                name.equals("queueEvent") ||
                name.equals("getFilesDir") ||
                name.equals("dispatchTouchEvent") ||
                name.equals("onTouchEvent"))
                continue;

            if (name.equals("a")) {
                if (p.length == 1 && p[0] == String.class) // a(String)
                    continue;

                if (p.length == 2) {
                    if (p[0] == String.class && p[1] == File.class) // a(String, File)
                        continue;
                    if (p[0] == Integer.class && p[1] == Integer.class) // a(int, int)
                        continue;
                }
            }

            XposedBridge.hookMethod(method, hook);
        }
    }

    private void hook_a(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.a", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook(clazz);

        for (Method method : clazz.getDeclaredMethods()) {
            XposedBridge.hookMethod(method, hook);
        }
    }

    private void hook_v(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.v", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook(clazz);

        for (Method method : clazz.getDeclaredMethods()) {
            XposedBridge.hookMethod(method, hook);
        }
    }

    private void hook_w(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.w", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook(clazz);

        for (Method method : clazz.getDeclaredMethods()) {
            XposedBridge.hookMethod(method, hook);
        }
    }

    private void hook_x(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.x", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook(clazz);

        for (Method method : clazz.getDeclaredMethods()) {
            XposedBridge.hookMethod(method, hook);
        }
    }

    private void hook_y(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.y", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook(clazz);

        for (Method method : clazz.getDeclaredMethods()) {
            XposedBridge.hookMethod(method, hook);
        }
    }

    private void hook_ac(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.ac", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook(clazz);

        for (Method method : clazz.getDeclaredMethods()) {
            String name = method.getName();
            Class<?>[] p = method.getParameterTypes();

            if (name.equals("a")) {
                if (p.length == 2 /*&& p[0] == Boolean.class && p[1] == Integer.class*/) // a(boolean, int)
                    continue;
            }

            XposedBridge.hookMethod(method, hook);
        }
    }

    private void hook_ad(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.ad", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook(clazz);

        for (Method method : clazz.getDeclaredMethods()) {
            XposedBridge.hookMethod(method, hook);
        }
    }

    private void hook_ae(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.ae", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook(clazz);

        for (Method method : clazz.getDeclaredMethods()) {
            XposedBridge.hookMethod(method, hook);
        }
    }

    private void hook_PlayerPrefs(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.PlayerPrefs", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook(clazz) {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Method method = (Method) param.method;
                String methodName = method.getName();
                if (methodName.equals("GetFloat") || methodName.equals("SetFloat")) {
                    String arg0 = (String) param.args[0];
                    if (arg0.equals("UserConfig_storedChatBubbleVectorX") || arg0.equals("UserConfig_storedChatBubbleVectorY"))
                        return;
                }

                super.afterHookedMethod(param);
            }
        };

        for (Method method : clazz.getDeclaredMethods()) {
            XposedBridge.hookMethod(method, hook);
        }
    }

    private void hook_WWW(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.WWW", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook(clazz) {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                J.printStackTrace(TAG);
            }
        };

        XposedBridge.hookAllConstructors(clazz, hook);
    }

    private void hook_URL(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("java.net.URL", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook(clazz) {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                URL url = (URL) param.thisObject;
                J.d(TAG, "URL: %s", url.toExternalForm());
            }
        };
        XposedBridge.hookAllMethods(clazz, "openConnection", hook);
    }

    private void hook_HttpsURLConnectionImpl(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.android.okhttp.internal.http.HttpsURLConnectionImpl", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook(clazz);

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals("toString"))
                continue;

            XposedBridge.hookMethod(method, hook);
        }
    }
}
