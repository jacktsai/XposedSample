package com.example.xposedsample.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.example.xposedsample.hooks.GeneralMethodHook;
import com.example.xposedsample.utils.J;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class TosHandler {
    private static final String TAG = TosHandler.class.getSimpleName();
    private static TosHandler instance;
    private static final String LOG_DIR = "/sdcard/ToS_traces";
    private static HashMap<String, FileOutputStream> fileMap = new HashMap<String, FileOutputStream>();

    private static FileOutputStream getOutputFile(URL url) {
        String filePath = String.format("%s/%s", LOG_DIR, url.getAuthority());
        FileOutputStream file = fileMap.get(filePath);
        if (file == null) {
            try {
                file = new FileOutputStream(filePath, true);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            fileMap.put(filePath, file);
        }

        return file;
    }

    private class MyOutputStream extends OutputStream {
        private final OutputStream inner;
        private final FileOutputStream log;

        public MyOutputStream(OutputStream inner, URL url) {
            this.inner = inner;

            String message = String.format("\n[request]\n");
            log = getOutputFile(url);
            try {
                log.write(message.getBytes("UTF-8"));
                log.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void close() throws IOException {
            inner.close();
        }

        @Override
        public void flush() throws IOException {
            inner.flush();
        }

        @Override
        public void write(int oneByte) throws IOException {
            inner.write(oneByte);
            log.write(oneByte);
            log.flush();
        }
    }

    private class MyInputStream extends InputStream {
        private final InputStream inner;
        private final FileOutputStream log;

        public MyInputStream(InputStream inner, URL url) {
            this.inner = inner;

            String message = String.format("\n[response]\n");
            log = getOutputFile(url);
            try {
                log.write(message.getBytes("UTF-8"));
                log.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int available() throws IOException {
            return inner.available();
        }

        @Override
        public void close() throws IOException {
            inner.close();
        }

        @Override
        public void mark(int readlimit) {
            inner.mark(readlimit);
        }

        @Override
        public boolean markSupported() {
            return inner.markSupported();
        }

        @Override
        public int read() throws IOException {
            int oneByte = inner.read();

            if (oneByte == -1) {
                log.write("\n".getBytes("UTF-8"));
            } else {
                log.write(oneByte);
            }
            log.flush();

            return oneByte;
        }

        @Override
        public synchronized void reset() throws IOException {
            inner.reset();
        }

        @Override
        public long skip(long byteCount) throws IOException {
            return inner.skip(byteCount);
        }
    }

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
//        hook_PlayerPrefs(packageParam);
//        hook_WWW(packageParam);
//        hook_URL(packageParam);
//        hook_URLConnection(packageParam);
//        hook_OutputStream(packageParam);
//        hook_InputStream(packageParam);
    }

    private void hook_UnityPlayer(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.UnityPlayer", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook();

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

    private void hook_PlayerPrefs(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.PlayerPrefs", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook() {
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
//                J.printStackTrace(TAG);
            }
        };

        for (Method method : clazz.getDeclaredMethods()) {
            String methodName = method.getName();
            if (methodName.equals("Sync"))
                continue;

            XposedBridge.hookMethod(method, hook);
        }
    }

    private void hook_WWW(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        Class<?> clazz = Class.forName("com.unity3d.player.WWW", false, packageParam.classLoader);
        XC_MethodHook hook = new GeneralMethodHook() {
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
        XC_MethodHook hook = new GeneralMethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

                URL url = (URL) param.thisObject;
                J.d(TosHandler.TAG, "URL: %s", url.toExternalForm());

                if (url.getAuthority().contains("towerofsaviors")) {
                    FileOutputStream log = getOutputFile(url);
                    log.write(url.toExternalForm().getBytes("UTF-8"));
                    log.flush();
                }
            }
        };
        XposedBridge.hookAllMethods(clazz, "openConnection", hook);
    }

    private void hook_URLConnection(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        XC_MethodHook hook = new GeneralMethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                URLConnection connection = (URLConnection) param.thisObject;
                URL url = connection.getURL();
                if (url.getAuthority().contains("towerofsaviors")) {
                    String methodName = param.method.getName();
                    if (methodName.equals("getOutputStream")) {
                        OutputStream origin = (OutputStream) param.getResult();
                        if (origin instanceof MyOutputStream) {
                        } else {
                            OutputStream substitute = new MyOutputStream(origin, url);
                            param.setResult(substitute);
                        }
                    } else if (param.method.getName().equals("getInputStream")) {
                        InputStream origin = (InputStream) param.getResult();
                        if (origin instanceof MyInputStream) {
                        } else {
                            InputStream substitute = new MyInputStream(origin, url);
                            param.setResult(substitute);
                        }
                    }

                    super.afterHookedMethod(param);
                }
            }
        };

        for (Method method : Class.forName("com.android.okhttp.internal.http.HttpURLConnectionImpl", false, packageParam.classLoader).getDeclaredMethods()) {
            String methodName = method.getName();
            if (methodName.equals("toString") || methodName.equals("getURL"))
                continue;

            if (Modifier.isPublic(method.getModifiers()))
                XposedBridge.hookMethod(method, hook);
        }

        for (Method method : Class.forName("com.android.okhttp.internal.http.HttpsURLConnectionImpl", false, packageParam.classLoader).getDeclaredMethods()) {
            String methodName = method.getName();
            if (methodName.equals("toString") || methodName.equals("getURL"))
                continue;

            if (Modifier.isPublic(method.getModifiers()))
                XposedBridge.hookMethod(method, hook);
        }
    }

    private void hook_OutputStream(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
//        Class<?> clazz = Class.forName("com.android.okhttp.internal.http.RetryableOutputStream", false, packageParam.classLoader);
        Class<?> clazz = MyOutputStream.class;
        XC_MethodHook hook = new GeneralMethodHook();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals("toString"))
                continue;

            XposedBridge.hookMethod(method, hook);
        }
    }

    private void hook_InputStream(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
//        Class<?> clazz = Class.forName("java.util.zip.GZIPInputStream", false, packageParam.classLoader);
        Class<?> clazz = MyInputStream.class;
        XC_MethodHook hook = new GeneralMethodHook();

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals("toString"))
                continue;

            XposedBridge.hookMethod(method, hook);
        }
    }
}
