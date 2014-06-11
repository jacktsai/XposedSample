package com.example.xposedsample.hooks;

import com.example.xposedsample.app.MethodStatistics;
import com.example.xposedsample.utils.J;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;

import static de.robv.android.xposed.XposedBridge.log;

public class GeneralMethodHook extends XC_MethodHook {
    public static final String TAG = GeneralMethodHook.class.getSimpleName();

    private final Class<?> clazz;

    public GeneralMethodHook(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
        String className = clazz.getSimpleName();

        Method method = (Method) param.method;
        String methodName = method.getName();

        Class<?>[] paramTypes = method.getParameterTypes();
        StringBuilder argsString = new StringBuilder();
        for (int i = 0; i < paramTypes.length; i++) {
            if (i > 0)
                argsString.append(", ");

            argsString.append("<" + paramTypes[i].getSimpleName() + ">");

            Object arg = param.args[i];
            if (arg == null)
                argsString.append("null");
            else
                argsString.append(arg.toString());
        }

        Class<?> returnType = method.getReturnType();
        String returnString = "";
        if (returnType != void.class) {
            returnString = String.format("<%s>", returnType.getSimpleName());

            Object returnValue = param.getResult();
            if (returnValue == null)
                returnString = returnString + "null";
            else
                returnString = returnString + returnValue.toString();
        }

        J.d(TAG, "%s.%s(%s)%s", className, methodName, argsString.toString(), returnString);
    }
}
