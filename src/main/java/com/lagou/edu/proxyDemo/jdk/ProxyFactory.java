package com.lagou.edu.proxyDemo.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {
    private ProxyFactory(){}

    private static ProxyFactory proxyFactory = new ProxyFactory();
    public static ProxyFactory getInstance(){
        return proxyFactory;
    }

    /**
     * @param client the client
     * @return returns the proxy
     */
    public Object getJdkProxy(Object client) {
        // get the proxy object DYNAMICALLY
        Object proxy =
                Proxy.newProxyInstance(client.getClass().getClassLoader(),
                        client.getClass().getInterfaces(),
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                Object res = null;
                                System.out.println("===>");
                                res = method.invoke(client, args);
                                System.out.println("<===");
                                return res;
                            }
                        });
        return proxy;
    }
}
