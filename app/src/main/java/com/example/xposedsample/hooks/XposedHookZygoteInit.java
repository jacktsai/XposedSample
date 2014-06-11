package com.example.xposedsample.hooks;

import com.example.xposedsample.app.TosHandler;
import com.example.xposedsample.utils.J;

import de.robv.android.xposed.IXposedHookZygoteInit;

public class XposedHookZygoteInit implements IXposedHookZygoteInit {
    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
//        J.log("initZygote [%s]", startupParam.modulePath);

        TosHandler.getInstance().onInit();
    }
}
