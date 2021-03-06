package com.example.xposedsample.hooks;

import com.example.xposedsample.utils.J;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;

public class XposedHookInitPackageResources implements IXposedHookInitPackageResources {
    private static final String TAG = XposedHookInitPackageResources.class.getSimpleName();

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam initPackageResourcesParam) throws Throwable {
        J.i(TAG, "init package resources [%s]", initPackageResourcesParam.packageName);
    }
}
