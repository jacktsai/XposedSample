package com.example.xposedsample.hooks;

import android.view.MotionEvent;

import com.example.xposedsample.app.TosHandler;
import com.example.xposedsample.utils.J;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;

public class XposedHookLoadPackage implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam packageParam) throws Throwable {
        String packageName = packageParam.packageName;
        J.log("load package [%s]", packageName);

        if (packageName.equals("com.madhead.tos.zh")) {
            TosHandler.getInstance().onLoadPackage(packageParam);
        }
    }
}
