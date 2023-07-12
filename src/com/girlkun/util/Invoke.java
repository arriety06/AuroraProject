// 
// Decompiled by Procyon v0.5.36
// 

package com.girlkun.util;

import java.lang.reflect.Method;

public class Invoke
{
    public static Object invoke(final Object object, final String methodName, final Class[] paramsType, final Object[] params) throws Exception {
        final Method method = object.getClass().getMethod(methodName, (Class<?>[])paramsType);
        return method.invoke(object, params);
    }
}
