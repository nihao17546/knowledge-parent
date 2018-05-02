package com.test.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by nihao on 18/4/9.
 */
public class DyProxy implements InvocationHandler {

    private Object targetObj;

    public DyProxy(Object targetObj) {
        this.targetObj = targetObj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("---------before---------");
        Object result = method.invoke(targetObj, args);
        System.out.println("---------after---------");
        return result;
    }

    public Object getProxy(){
        return Proxy.newProxyInstance(targetObj.getClass().getClassLoader(),
                targetObj.getClass().getInterfaces(), this);
    }
}
