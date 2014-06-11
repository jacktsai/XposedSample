package com.example.xposedsample.hooks;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.xposedsample.app.TosHandler;
import com.example.xposedsample.utils.J;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class XposedHookLoadPackage implements IXposedHookLoadPackage {
    private static final String TAG = XposedHookLoadPackage.class.getSimpleName();

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        String packageName = packageParam.packageName;
        J.i(TAG, "load package [%s]", packageName);

        if (packageName.equals("com.madhead.tos.zh")) {
            TosHandler.getInstance().onLoadPackage(packageParam);
        }

        findAndHookMethod(
            "com.android.internal.policy.impl.PhoneWindow",
            null,
            "generateLayout",
            "com.android.internal.policy.impl.PhoneWindow.DecorView",
            new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Window window = (Window)param.thisObject;
                    Context context = window.getContext();

                    TextView textView = new TextView(context);
                    textView.setText(context.getPackageName());

                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
                    layoutParams.format = PixelFormat.RGBA_8888;
                    layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                    layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    layoutParams.gravity = Gravity.TOP | Gravity.LEFT;

                    WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
                    windowManager.addView(textView, layoutParams);
                }
            });
    }
}
