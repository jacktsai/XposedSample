package com.example.xposedsample.app;

import java.lang.reflect.Method;
import java.util.HashMap;

public class MethodStatistics {
    private static HashMap<String, Integer> statistics = new HashMap<String, Integer>();

    public static synchronized void plus(Method method) {
        StringBuilder signature = new StringBuilder();
        signature.append(method.getDeclaringClass().getName());
        signature.append("#");
        signature.append(method.getName());
        signature.append("(");
        int pIndex = 0;
        for (Class<?> p : method.getParameterTypes()) {
            if (pIndex > 0)
                signature.append(", ");
            signature.append(p.getSimpleName());
            pIndex++;
        }
        signature.append(")");

        String key = signature.toString();
        Integer i = statistics.get(key);
        if (i == null) {
            statistics.put(key, 1);
        } else {
            i++;
            statistics.put(key, i);
        }
    }

    public static HashMap<String, Integer> getStatistics() {
        return statistics;
    }
}
